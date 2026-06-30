package org.ukrida.root.ui.admin.screens.ongoing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ukrida.root.data.fake.FakeDevotionRepository

data class DailyBreadItem(
    val id: Int,
    val day: Int,
    val date: String,
    val title: String,
    val content: String
)

class EditDailyBreadViewModel(
    val tripId: Int
) : ViewModel() {

    private val repository = FakeDevotionRepository()

    private val _availableDays = MutableStateFlow<List<Int>>(emptyList())
    val availableDays: StateFlow<List<Int>> = _availableDays.asStateFlow()

    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    private val _dailyBreadList = MutableStateFlow<List<DailyBreadItem>>(emptyList())
    val dailyBreadList: StateFlow<List<DailyBreadItem>> = _dailyBreadList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Map date -> day number untuk lookup
    private val dateToDay = mutableMapOf<String, Int>()
    private val dayToDate = mutableMapOf<Int, String>()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true

            repository.getDevotionDates(tripId).onSuccess { dates ->
                // Map tiap tanggal ke nomor hari
                dates.forEachIndexed { index, devotionDate ->
                    val dayNumber = index + 1
                    dateToDay[devotionDate.date] = dayNumber
                    dayToDate[dayNumber] = devotionDate.date
                }

                _availableDays.value = dates.indices.map { it + 1 }

                // Load devotion untuk tiap tanggal
                val allItems = mutableListOf<DailyBreadItem>()
                dates.forEachIndexed { index, devotionDate ->
                    val dayNumber = index + 1
                    repository.getDevotion(tripId, devotionDate.date)
                        .onSuccess { devotion ->
                            allItems.add(
                                DailyBreadItem(
                                    id = devotion.id,
                                    day = dayNumber,
                                    date = devotion.date,
                                    title = devotion.title,
                                    content = devotion.content
                                )
                            )
                        }
                        .onFailure {
                            // Devotion belum ada untuk hari ini, buat kosong
                            allItems.add(
                                DailyBreadItem(
                                    id = dayNumber,
                                    day = dayNumber,
                                    date = devotionDate.date,
                                    title = devotionDate.title,
                                    content = ""
                                )
                            )
                        }
                }

                _dailyBreadList.value = allItems.sortedBy { it.day }
                _selectedDay.value = 1
            }

            _isLoading.value = false
        }
    }

    fun selectDay(day: Int) {
        _selectedDay.update { day }
    }

    fun updateTitle(day: Int, newTitle: String) {
        _dailyBreadList.update { list ->
            list.map { if (it.day == day) it.copy(title = newTitle) else it }
        }
    }

    fun updateContent(day: Int, newContent: String) {
        _dailyBreadList.update { list ->
            list.map { if (it.day == day) it.copy(content = newContent) else it }
        }
    }

    fun submit() {
        // TODO: kirim ke API
    }

    companion object {
        fun factory(tripId: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return EditDailyBreadViewModel(tripId) as T
                }
            }
    }
}