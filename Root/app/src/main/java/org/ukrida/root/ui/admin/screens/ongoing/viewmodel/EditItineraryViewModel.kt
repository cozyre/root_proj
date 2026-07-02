package org.ukrida.root.ui.admin.screens.ongoing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.CreateItineraryItemRequest
import org.ukrida.root.data.model.ItineraryItem
import org.ukrida.root.data.model.UpdateItineraryRequest
import org.ukrida.root.data.remote.RetrofitClient
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.data.repository.ItineraryRepository
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class ItineraryItemUiState(
    val id: Int,
    val itineraryId: Int,
    val day: Int,
    val startTime: String,
    val endTime: String,
    val activity: String,
    val type: String = "activity"
)

class EditItineraryViewModel(
    val tripId: Int
) : ViewModel() {

    private val itineraryRepository = ItineraryRepository(RetrofitClient.instance)
    private val groupRepository = GroupRepository(RetrofitClient.instance)

    private val _itineraryList = MutableStateFlow<List<ItineraryItemUiState>>(emptyList())
    val itineraryList: StateFlow<List<ItineraryItemUiState>> = _itineraryList.asStateFlow()

    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    private val _availableDays = MutableStateFlow<List<Int>>(emptyList())
    val availableDays: StateFlow<List<Int>> = _availableDays.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val dayToItineraryId = mutableMapOf<Int, Int>()

    init {
        loadItinerary()
    }

    private fun loadItinerary() {
        viewModelScope.launch {
            _isLoading.value = true

            groupRepository.getTourById(tripId).onSuccess { group ->
                val startDate = group.startDate
                val endDate = group.endDate

                if (startDate != null && endDate != null) {
                    _availableDays.value = calculateDays(startDate, endDate)
                }
            }

            itineraryRepository.getItineraryDates(tripId).onSuccess { dates ->
                val allItems = mutableListOf<ItineraryItemUiState>()

                dates.forEachIndexed { index, itineraryDate ->
                    val dayNumber = index + 1

                    itineraryRepository.getItinerary(tripId, itineraryDate.date)
                        .onSuccess { itinerary ->
                            dayToItineraryId[dayNumber] = itinerary.id

                            allItems.addAll(
                                itinerary.items.map { item ->
                                    item.toUiState(
                                        day = dayNumber,
                                        itineraryId = itinerary.id
                                    )
                                }
                            )
                        }
                }

                _itineraryList.value = allItems.sortedWith(
                    compareBy<ItineraryItemUiState> { it.day }.thenBy { it.startTime }
                )

                if (_availableDays.value.isEmpty()) {
                    _availableDays.value = dates.indices.map { it + 1 }
                }

                _selectedDay.value = _availableDays.value.firstOrNull() ?: 1
            }

            _isLoading.value = false
        }
    }

    private fun calculateDays(startDate: String, endDate: String): List<Int> {
        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)
        val totalDays = ChronoUnit.DAYS.between(start, end).toInt() + 1
        return (1..totalDays).toList()
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

    fun addItem() {
        val tempId = -System.currentTimeMillis().toInt()

        _itineraryList.value = _itineraryList.value + ItineraryItemUiState(
            id = tempId,
            itineraryId = dayToItineraryId[_selectedDay.value] ?: return,
            day = selectedDay.value,
            startTime = "00:00",
            endTime = "00:00",
            activity = "",
            type = "activity"
        )
    }

    private val deletedItemIds = mutableListOf<Int>()

    fun deleteItem(itemId: Int) {
        if (itemId > 0) {
            deletedItemIds.add(itemId)
        }

        _itineraryList.value = _itineraryList.value.filter {
            it.id != itemId
        }
    }

    fun submitAll() {
        viewModelScope.launch {
            deletedItemIds.forEach { id ->
                itineraryRepository.deleteItem(id)
            }

            _itineraryList.value.forEach { item ->
                if (item.id < 0) {
                    itineraryRepository.createItem(
                        CreateItineraryItemRequest(
                            itenary_id = item.itineraryId,
                            start_time = item.startTime,
                            end_time = item.endTime,
                            type = item.type,
                            description = item.activity
                        )
                    )
                } else {
                    itineraryRepository.updateItem(
                        id = item.id,
                        request = UpdateItineraryRequest(
                            start_time = item.startTime,
                            end_time = item.endTime,
                            type = item.type,
                            description = item.activity
                        )
                    )
                }
            }
            deletedItemIds.clear()
            loadItinerary()
            _submitSuccess.value = true
        }
    }

    private fun ItineraryItem.toUiState(
        day: Int,
        itineraryId: Int
    ) = ItineraryItemUiState(
        id = id,
        itineraryId = itineraryId,
        day = day,
        startTime = startTime,
        endTime = endTime ?: "",
        activity = description,
        type = type.ifBlank { "activity" }
    )
    private val _submitSuccess = MutableStateFlow(false)
    val submitSuccess: StateFlow<Boolean> = _submitSuccess.asStateFlow()

    fun resetSubmitSuccess() {
        _submitSuccess.value = false
    }

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