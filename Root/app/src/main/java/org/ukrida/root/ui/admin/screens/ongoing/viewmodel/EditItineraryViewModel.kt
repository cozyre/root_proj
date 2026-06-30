package org.ukrida.root.ui.admin.screens.ongoing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.fake.FakeGroupRepository
import org.ukrida.root.data.fake.FakeItineraryRepository
import org.ukrida.root.data.model.ItineraryItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class ItineraryItemUiState(
    val id: Int,
    val day: Int,
    val startTime: String,
    val endTime: String,
    val activity: String,   // dari description
    val type: String
)

class EditItineraryViewModel(
    val tripId: Int
) : ViewModel() {

    private val itineraryRepository = FakeItineraryRepository()
    private val groupRepository = FakeGroupRepository()

    private val _itineraryList = MutableStateFlow<List<ItineraryItemUiState>>(emptyList())
    val itineraryList: StateFlow<List<ItineraryItemUiState>> = _itineraryList.asStateFlow()

    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    private val _availableDays =
        MutableStateFlow<List<Int>>(emptyList())

    val availableDays: StateFlow<List<Int>>
            = _availableDays.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    init {
        loadItineraryDates()
    }

    private fun calculateDays(
        startDate: String,
        endDate: String
    ): List<Int> {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val start = LocalDate.parse(startDate, formatter)
        val end = LocalDate.parse(endDate, formatter)

        val totalDays =
            ChronoUnit.DAYS.between(start, end).toInt() + 1

        return (1..totalDays).toList()
    }

    private fun loadItineraryDates() {
        viewModelScope.launch {

            _isLoading.value = true

            val groupResult =
                groupRepository.getTourById(tripId)

            groupResult.onSuccess { group ->

                val days = calculateDays(
                    group.startDate ?: return@onSuccess,
                    group.endDate ?: return@onSuccess
                )

                _availableDays.value = days

                val datesResult =
                    itineraryRepository.getItineraryDates(tripId)

                datesResult.onSuccess { dates ->

                    val allItems =
                        mutableListOf<ItineraryItemUiState>()

                    dates.forEachIndexed { index, itineraryDate ->

                        val dayNumber = index + 1

                        val itineraryResult =
                            itineraryRepository.getItinerary(
                                tripId,
                                itineraryDate.date
                            )

                        itineraryResult.onSuccess { itinerary ->

                            allItems.addAll(
                                itinerary.items.map {
                                    it.toUiState(dayNumber)
                                }
                            )
                        }
                    }

                    _itineraryList.value = allItems
                }

                _selectedDay.value = 1
            }

            _isLoading.value = false
        }
    }

    fun selectDay(day: Int) {
        _selectedDay.value = day
    }

    fun updateStartTime(itemId: Int, newTime: String) {
        _itineraryList.value = _itineraryList.value.map {
            if (it.id == itemId) it.copy(startTime = newTime) else it
        }
    }

    fun updateEndTime(itemId: Int, newTime: String) {
        _itineraryList.value = _itineraryList.value.map {
            if (it.id == itemId) it.copy(endTime = newTime) else it
        }
    }

    fun updateActivity(itemId: Int, newActivity: String) {
        _itineraryList.value = _itineraryList.value.map {
            if (it.id == itemId) it.copy(activity = newActivity) else it
        }
    }

    fun deleteItem(itemId: Int) {
        _itineraryList.value = _itineraryList.value.filter { it.id != itemId }
    }

    fun addItem() {
        val newId = (_itineraryList.value.maxOfOrNull { it.id } ?: 0) + 1
        val newItem = ItineraryItemUiState(
            id = newId,
            day = _selectedDay.value,
            startTime = "00:00",
            endTime = "00:00",
            activity = "",
            type = "activity"
        )
        _itineraryList.value = _itineraryList.value + newItem
    }

    private fun ItineraryItem.toUiState(day: Int) = ItineraryItemUiState(
        id = id,
        day = day,
        startTime = startTime,
        endTime = endTime ?: "",
        activity = description,
        type = type
    )

    companion object {
        fun factory(tripId: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return EditItineraryViewModel(tripId) as T
                }
            }
    }
}