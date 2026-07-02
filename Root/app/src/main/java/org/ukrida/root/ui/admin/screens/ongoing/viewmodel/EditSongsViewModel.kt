package org.ukrida.root.ui.admin.screens.ongoing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.SongBrowseItem
import org.ukrida.root.data.remote.RetrofitClient
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.data.repository.ItineraryRepository
import org.ukrida.root.data.repository.SongRepository
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import org.ukrida.root.data.model.AddSongToGroupRequest
import org.ukrida.root.data.model.RemoveSongFromGroupRequest


data class SongItemUiState(
    val id: Int,
    val day: Int,
    val selectedSongId: Int? = null,
    val selectedSongTitle: String = "",
    val originalSongId: Int? = null
)


class EditSongsViewModel(
    val tripId: Int
) : ViewModel() {

    private val repository = SongRepository(RetrofitClient.instance)
    private val groupRepository = GroupRepository(RetrofitClient.instance)
    private val itineraryRepository = ItineraryRepository(RetrofitClient.instance)
    private val dayToDate = mutableMapOf<Int, String>()
    private val dayToItineraryId = mutableMapOf<Int, Int>()

    private data class DeletedSongDraft(
        val day: Int,
        val songId: Int
    )

    private val deletedSongDrafts = mutableListOf<DeletedSongDraft>()

    private var tempId = -1
    private var uiId = 1

    private val _songList = MutableStateFlow<List<SongItemUiState>>(emptyList())

    private fun calculateDays(startDate: String, endDate: String): List<Int> {
        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)
        val totalDays = ChronoUnit.DAYS.between(start, end).toInt() + 1
        return (1..totalDays).toList()
    }

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

    private fun generateDayDateMap(startDate: String, endDate: String) {
        dayToDate.clear()

        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)
        val totalDays = ChronoUnit.DAYS.between(start, end).toInt() + 1

        for (i in 0 until totalDays) {
            val day = i + 1
            val date = start.plusDays(i.toLong()).toString()
            dayToDate[day] = date
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true

            groupRepository.getTourById(tripId)
                .onSuccess { group ->
                    val startDate = group.startDate
                    val endDate = group.endDate

                    if (startDate != null && endDate != null) {
                        _availableDays.value = calculateDays(startDate, endDate)
                        generateDayDateMap(startDate, endDate)
                        _selectedDay.value = _availableDays.value.firstOrNull() ?: 1

                        // Ambil itinerary id untuk setiap day
                        dayToItineraryId.clear()

                        dayToDate.forEach { (day, date) ->
                            itineraryRepository.getItinerary(tripId, date)
                                .onSuccess { itinerary ->
                                    dayToItineraryId[day] = itinerary.id
                                }
                                .onFailure {
                                    it.printStackTrace()
                                }
                        }
                    }
                }
                .onFailure {
                    it.printStackTrace()
                }

            repository.browseSongs()
                .onSuccess { songs ->
                    _availableSongs.value = songs
                }
                .onFailure {
                    it.printStackTrace()
                }

            val allSongItems = mutableListOf<SongItemUiState>()
            uiId = 1

            dayToDate.forEach { (day, date) ->
                repository.getSongsByDate(tripId, date)
                    .onSuccess { summaries ->
                        summaries.forEach { summary ->
                            allSongItems.add(
                                SongItemUiState(
                                    id = uiId++,
                                    day = day,
                                    selectedSongId = summary.id,
                                    selectedSongTitle = summary.title,
                                    originalSongId = summary.id
                                )
                            )
                        }
                    }
                    .onFailure {
                        it.printStackTrace()
                    }
            }

            _songList.value = allSongItems

            _isLoading.value = false
        }
    }

    fun selectDay(day: Int) {
        _selectedDay.value = day
    }

    fun selectSong(itemId: Int, song: SongBrowseItem) {
        _songList.value = _songList.value.map { item ->
            if (item.id == itemId) {
                item.copy(
                    selectedSongId = song.id,
                    selectedSongTitle = song.title
                )
            } else {
                item
            }
        }
    }

    fun addItem() {
        val newItem = SongItemUiState(
            id = tempId--,
            day = _selectedDay.value,
            selectedSongId = null,
            selectedSongTitle = "",
            originalSongId = null
        )

        _songList.value = _songList.value + newItem
    }

    fun deleteItem(itemId: Int) {
        val deletedItem = _songList.value.find { it.id == itemId }

        if (deletedItem?.originalSongId != null) {
            deletedSongDrafts.add(
                DeletedSongDraft(
                    day = deletedItem.day,
                    songId = deletedItem.originalSongId
                )
            )
        }

        _songList.value = _songList.value.filter { it.id != itemId }
    }

    fun submitSongs() {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                // 1. Hapus lagu lama yang user delete
                deletedSongDrafts.forEach { deleted ->
                    val itineraryId = dayToItineraryId[deleted.day]

                    repository.removeSongFromGroup(
                        RemoveSongFromGroupRequest(
                            group_id = tripId,
                            song_id = deleted.songId,
                            itenary_id = itineraryId
                        )
                    ).onFailure {
                        throw it
                    }
                }

                // 2. Proses semua item yang masih ada di UI
                _songList.value.forEachIndexed { index, item ->
                    val selectedSongId = item.selectedSongId ?: return@forEachIndexed
                    val itineraryId = dayToItineraryId[item.day]

                    val isNewItem = item.originalSongId == null
                    val isChangedItem = item.originalSongId != null &&
                            item.originalSongId != selectedSongId

                    // Item baru dari ADD MORE
                    if (isNewItem) {
                        repository.addSongToGroup(
                            AddSongToGroupRequest(
                                group_id = tripId,
                                song_id = selectedSongId,
                                itenary_id = itineraryId,
                                sort_order = index + 1
                            )
                        ).onFailure {
                            throw it
                        }
                    }

                    // Item lama yang lagunya diganti
                    if (isChangedItem) {
                        repository.removeSongFromGroup(
                            RemoveSongFromGroupRequest(
                                group_id = tripId,
                                song_id = item.originalSongId,
                                itenary_id = itineraryId
                            )
                        ).onFailure {
                            throw it
                        }

                        repository.addSongToGroup(
                            AddSongToGroupRequest(
                                group_id = tripId,
                                song_id = selectedSongId,
                                itenary_id = itineraryId,
                                sort_order = index + 1
                            )
                        ).onFailure {
                            throw it
                        }
                    }
                }

                deletedSongDrafts.clear()
                loadData()

            } catch (e: Exception) {
                e.printStackTrace()
            }

            _isLoading.value = false
        }
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