package org.ukrida.root.ui.admin.screens.ongoing.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.ui.admin.screens.ongoing.model.ItineraryItem

class EditItineraryViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val tripId: Int = savedStateHandle.get<Int>("tripId") ?: 1

    // Dropdown hari yang dipilih
    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    private val _itineraryList = MutableStateFlow<List<ItineraryItem>>(emptyList())
    val itineraryList: StateFlow<List<ItineraryItem>> = _itineraryList.asStateFlow()

    // Counter untuk generate ID unik saat ADD MORE
    private var nextId = 10

    private val mockData = listOf(
        ItineraryItem(1, 1, "08:00", "10:00", "Penjemputan di Bandara"),
        ItineraryItem(2, 1, "12:00", "13:00", "Makan Siang Lokal"),
        ItineraryItem(3, 2, "09:00", "11:00", "Tur Kota Tua"),
    )

    init {
        loadDummyData()
    }

    private fun loadDummyData() {
        _itineraryList.value = mockData
    }

    fun selectDay(day: Int) {
        _selectedDay.value = day
    }

    fun updateActivity(itemId: Int, newActivity: String) {
        _itineraryList.value = _itineraryList.value.map {
            if (it.id == itemId) it.copy(activity = newActivity) else it
        }
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

    fun addItem() {
        val currentDay = _selectedDay.value
        val newItem = ItineraryItem(
            id = nextId++,
            day = currentDay,
            startTime = "00:00",
            endTime = "00:00",
            activity = ""
        )
        _itineraryList.value = _itineraryList.value + newItem
    }

    fun deleteItem(itemId: Int) {
        _itineraryList.value = _itineraryList.value.filter { it.id != itemId }
    }
}