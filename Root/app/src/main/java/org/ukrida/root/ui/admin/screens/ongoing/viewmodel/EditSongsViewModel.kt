package org.ukrida.root.ui.admin.screens.ongoing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.fake.FakeSongRepository
import org.ukrida.root.data.model.SongBrowseItem
import org.ukrida.root.data.model.SongSummary

data class SongItemUiState(
    val id: Int,
    val day: Int,
    val selectedSongId: Int? = null,
    val selectedSongTitle: String = ""
)

class EditSongsViewModel(
    val tripId: Int
) : ViewModel() {

    private val repository = FakeSongRepository()

    private val _songList = MutableStateFlow<List<SongItemUiState>>(emptyList())
    val songList: StateFlow<List<SongItemUiState>> = _songList.asStateFlow()

    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    private val _availableDays = MutableStateFlow<List<Int>>(emptyList())
    val availableDays: StateFlow<List<Int>> = _availableDays.asStateFlow()

    // Bank lagu untuk dropdown
    private val _availableSongs = MutableStateFlow<List<SongBrowseItem>>(emptyList())
    val availableSongs: StateFlow<List<SongBrowseItem>> = _availableSongs.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true

            // Load bank lagu global
            repository.browseSongs().onSuccess { songs ->
                _availableSongs.value = songs
            }

            // Load lagu yang sudah ada di grup (per hari)
            // Untuk sekarang dummy: semua lagu di day 1
            repository.getSongs(tripId).onSuccess { summaries ->
                val uiList = summaries.mapIndexed { index, summary ->
                    SongItemUiState(
                        id = summary.id,
                        day = 1, // default day 1, nanti bisa dikembangkan per date
                        selectedSongId = summary.id,
                        selectedSongTitle = summary.title
                    )
                }
                _songList.value = uiList
                _availableDays.value = listOf(1) // bisa dikembangkan dari itinerary dates
            }

            _isLoading.value = false
        }
    }

    fun selectDay(day: Int) {
        _selectedDay.value = day
    }

    fun selectSong(itemId: Int, song: SongBrowseItem) {
        _songList.value = _songList.value.map { item ->
            if (item.id == itemId) item.copy(
                selectedSongId = song.id,
                selectedSongTitle = song.title
            ) else item
        }
    }

    fun addItem() {
        val newId = (_songList.value.maxOfOrNull { it.id } ?: 0) + 1
        val newItem = SongItemUiState(
            id = newId,
            day = _selectedDay.value
        )
        _songList.value = _songList.value + newItem
    }

    fun deleteItem(itemId: Int) {
        _songList.value = _songList.value.filter { it.id != itemId }
    }

    fun submitSongs() {
        // TODO: kirim ke API
    }

    companion object {
        fun factory(tripId: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return EditSongsViewModel(tripId) as T
                }
            }
    }
}