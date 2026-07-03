package org.ukrida.root.ui.admin.screens.newtrip.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.AddSongToGroupRequest
import org.ukrida.root.data.model.SongBrowseItem
import org.ukrida.root.data.repository.ItineraryRepository
import org.ukrida.root.data.repository.SongRepository

data class NewSongItemUiState(
    val id: Int,
    val day: Int,
    val selectedSongId: Int? = null,
    val selectedSongTitle: String = "",
    val selectedSongAuthor: String? = null
)

class NewSongsViewModel(
    val tripId: Int,
    private val songRepository: SongRepository,
    private val itineraryRepository: ItineraryRepository
) : ViewModel() {

    private val _songItems = MutableStateFlow<List<NewSongItemUiState>>(emptyList())
    val songItems: StateFlow<List<NewSongItemUiState>> = _songItems.asStateFlow()

    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    private val _availableDays = MutableStateFlow<List<Int>>(emptyList())
    val availableDays: StateFlow<List<Int>> = _availableDays.asStateFlow()

    private val _availableSongs = MutableStateFlow<List<SongBrowseItem>>(emptyList())
    val availableSongs: StateFlow<List<SongBrowseItem>> = _availableSongs.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _submitSuccess = MutableStateFlow(false)
    val submitSuccess: StateFlow<Boolean> = _submitSuccess.asStateFlow()

    private val dayToItineraryId = mutableMapOf<Int, Int>()
    private var tempId = -1

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            itineraryRepository.getItineraryDates(tripId)
                .onSuccess { dates ->
                    val days = dates.indices.map { index -> index + 1 }

                    _availableDays.value = days
                    _selectedDay.value = days.firstOrNull() ?: 1

                    dayToItineraryId.clear()

                    dates.forEachIndexed { index, itineraryDate ->
                        val dayNumber = index + 1

                        itineraryRepository.getItinerary(
                            groupId = tripId,
                            date = itineraryDate.date
                        ).onSuccess { itinerary ->
                            dayToItineraryId[dayNumber] = itinerary.id
                        }
                    }

                    if (_songItems.value.isEmpty() && days.isNotEmpty()) {
                        addItem()
                    }
                }
                .onFailure { error ->
                    _errorMessage.value = error.message ?: "Gagal memuat tanggal itinerary"
                }

            songRepository.browseSongs()
                .onSuccess { songs ->
                    _availableSongs.value = songs
                }
                .onFailure { error ->
                    _errorMessage.value = error.message ?: "Gagal memuat daftar lagu"
                }

            _isLoading.value = false
        }
    }

    fun selectDay(day: Int) {
        _selectedDay.value = day

        val alreadyHasItem = _songItems.value.any {
            it.day == day
        }

        if (!alreadyHasItem) {
            addItem()
        }
    }

    fun addItem() {
        val day = _selectedDay.value

        _songItems.value = _songItems.value + NewSongItemUiState(
            id = tempId--,
            day = day
        )
    }

    fun deleteItem(itemId: Int) {
        _songItems.value = _songItems.value.filter {
            it.id != itemId
        }
    }

    fun selectSong(
        itemId: Int,
        song: SongBrowseItem
    ) {
        _songItems.value = _songItems.value.map { item ->
            if (item.id == itemId) {
                item.copy(
                    selectedSongId = song.id,
                    selectedSongTitle = song.title,
                    selectedSongAuthor = song.author
                )
            } else {
                item
            }
        }
    }

    fun submitSongs() {
        val currentItems = _songItems.value

        if (currentItems.isEmpty()) {
            _errorMessage.value = "Minimal tambahkan 1 lagu"
            return
        }

        val invalidItem = currentItems.firstOrNull {
            it.selectedSongId == null
        }

        if (invalidItem != null) {
            _errorMessage.value = "Semua row lagu harus dipilih"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                currentItems
                    .groupBy { it.day }
                    .forEach { (day, songsInDay) ->

                        val itineraryId = dayToItineraryId[day]

                        songsInDay.forEachIndexed { index, item ->
                            val selectedSongId = item.selectedSongId
                                ?: throw Exception("Lagu belum dipilih")

                            songRepository.addSongToGroup(
                                AddSongToGroupRequest(
                                    group_id = tripId,
                                    song_id = selectedSongId,
                                    itenary_id = itineraryId,
                                    sort_order = index + 1
                                )
                            ).onFailure { error ->
                                throw error
                            }
                        }
                    }

                _submitSuccess.value = true
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Gagal menambahkan lagu"
            }

            _isLoading.value = false
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun resetSubmitSuccess() {
        _submitSuccess.value = false
    }

    companion object {
        fun factory(
            tripId: Int,
            songRepository: SongRepository,
            itineraryRepository: ItineraryRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NewSongsViewModel(
                        tripId = tripId,
                        songRepository = songRepository,
                        itineraryRepository = itineraryRepository
                    ) as T
                }
            }
        }
    }
}