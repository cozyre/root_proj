package org.ukrida.root.ui.admin.screens.newtrip.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.CreateItineraryItemRequest
import org.ukrida.root.data.model.Itinerary
import org.ukrida.root.data.repository.ItineraryRepository

data class NewItineraryItemUiState(
    val id: Int,
    val itineraryId: Int,
    val day: Int,
    val startTime: String = "00:00",
    val endTime: String = "00:00",
    val activity: String = "",
    val type: String = "activity"
)

class NewItineraryViewModel(
    val tripId: Int,
    private val itineraryRepository: ItineraryRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<NewItineraryItemUiState>>(emptyList())
    val items: StateFlow<List<NewItineraryItemUiState>> = _items.asStateFlow()

    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    private val _availableDays = MutableStateFlow<List<Int>>(emptyList())
    val availableDays: StateFlow<List<Int>> = _availableDays.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _submitSuccess = MutableStateFlow(false)
    val submitSuccess: StateFlow<Boolean> = _submitSuccess.asStateFlow()

    private val dayToItineraryId = mutableMapOf<Int, Int>()

    init {
        loadItineraryStubs()
    }

    private fun loadItineraryStubs() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            itineraryRepository.getItineraryDates(tripId)
                .onSuccess { dates ->
                    val days = dates.indices.map { index -> index + 1 }

                    _availableDays.value = days
                    _selectedDay.value = days.firstOrNull() ?: 1

                    dates.forEachIndexed { index, itineraryDate ->
                        val dayNumber = index + 1

                        itineraryRepository.getItinerary(
                            groupId = tripId,
                            date = itineraryDate.date
                        ).onSuccess { itinerary ->
                            dayToItineraryId[dayNumber] = itinerary.id
                        }
                    }

                    if (_items.value.isEmpty() && dayToItineraryId.isNotEmpty()) {
                        addItem()
                    }
                }
                .onFailure { error ->
                    _errorMessage.value = error.message ?: "Gagal memuat tanggal itinerary"
                }

            _isLoading.value = false
        }
    }

    fun selectDay(day: Int) {
        _selectedDay.value = day

        val alreadyHasItem = _items.value.any {
            it.day == day
        }

        if (!alreadyHasItem) {
            addItem()
        }
    }

    fun addItem() {
        val selected = _selectedDay.value
        val itineraryId = dayToItineraryId[selected]

        if (itineraryId == null) {
            _errorMessage.value = "Itinerary untuk Day $selected belum tersedia"
            return
        }

        val tempId = -System.currentTimeMillis().toInt()

        _items.value = _items.value + NewItineraryItemUiState(
            id = tempId,
            itineraryId = itineraryId,
            day = selected,
            startTime = "00:00",
            endTime = "00:00",
            activity = "",
            type = "activity"
        )
    }

    fun deleteItem(itemId: Int) {
        _items.value = _items.value.filter {
            it.id != itemId
        }
    }

    fun updateStartTime(itemId: Int, value: String) {
        _items.value = _items.value.map {
            if (it.id == itemId) it.copy(startTime = value) else it
        }
    }

    fun updateEndTime(itemId: Int, value: String) {
        _items.value = _items.value.map {
            if (it.id == itemId) it.copy(endTime = value) else it
        }
    }

    fun updateActivity(itemId: Int, value: String) {
        _items.value = _items.value.map {
            if (it.id == itemId) it.copy(activity = value) else it
        }
    }

    fun submitItinerary() {
        val currentItems = _items.value

        if (currentItems.isEmpty()) {
            _errorMessage.value = "Minimal tambahkan 1 itinerary"
            return
        }

        val invalidItem = currentItems.firstOrNull {
            it.activity.isBlank()
        }

        if (invalidItem != null) {
            _errorMessage.value = "Activity tidak boleh kosong"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            var hasError = false
            var errorText = ""

            currentItems.forEach { item ->
                itineraryRepository.createItem(
                    CreateItineraryItemRequest(
                        itenary_id = item.itineraryId,
                        start_time = item.startTime,
                        end_time = item.endTime,
                        type = item.type,
                        description = item.activity
                    )
                ).onFailure { error ->
                    hasError = true
                    errorText = error.message ?: "Gagal membuat itinerary"
                }
            }

            if (hasError) {
                _errorMessage.value = errorText
            } else {
                _submitSuccess.value = true
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
            itineraryRepository: ItineraryRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NewItineraryViewModel(
                        tripId = tripId,
                        itineraryRepository = itineraryRepository
                    ) as T
                }
            }
        }
    }
}