package org.ukrida.root.ui.user.screens.groupmenus.itinerary.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.data.model.Itinerary
import org.ukrida.root.data.model.ItineraryDate
import org.ukrida.root.data.model.ItineraryItem

class ItineraryViewModel : ViewModel() {

    // Current itinerary
    private val _itinerary = MutableStateFlow<Itinerary?>(null)
    val itinerary = _itinerary.asStateFlow()

    // Available dates
    private val _dates = MutableStateFlow<List<ItineraryDate>>(emptyList())
    val dates = _dates.asStateFlow()

    // Current selected date index
    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex = _selectedIndex.asStateFlow()

    fun load(groupId: Int) {
        // TODO Backend Integration
        // repository.getItineraryDates(groupId)
        loadDummy(groupId)
    }
    fun nextDay(groupId: Int) {
        if (_selectedIndex.value < _dates.value.lastIndex) {
            _selectedIndex.value++
            // TODO Backend Integration
            // repository.getItinerary(groupId, dates[selectedIndex].date)
            loadDummyItinerary(_dates.value[_selectedIndex.value].date)
        }
    }
    fun previousDay(groupId: Int) {
        if (_selectedIndex.value > 0) {
            _selectedIndex.value--
            // TODO Backend Integration
            // repository.getItinerary(groupId, dates[selectedIndex].date)
            loadDummyItinerary(_dates.value[_selectedIndex.value].date)
        }
    }
    // ------------------------------------------------------------
    // Dummy Data
    // ------------------------------------------------------------

    private fun loadDummy(groupId: Int) {
        _dates.value = listOf(
            ItineraryDate(
                date = "2026-06-29",
                desc = "Arrival"
            ),
            ItineraryDate(
                date = "2026-06-30",
                desc = "Jerusalem"
            ),
            ItineraryDate(
                date = "2026-07-01",
                desc = "Bethlehem"
            )
        )
        _selectedIndex.value = 0
        loadDummyItinerary(_dates.value.first().date)
    }

    private fun loadDummyItinerary(date: String) {
        _itinerary.value = Itinerary(
            id = 1,
            date = date,
            desc = "Holy Land Pilgrimage",
            items = listOf(
                ItineraryItem(
                    id = 1,
                    startTime = "07:00",
                    endTime = "08:00",
                    type = "activity",
                    description = "Breakfast"
                ),
                ItineraryItem(
                    id = 2,
                    startTime = "08:30",
                    endTime = "10:00",
                    type = "activity",
                    description = "Visit Church of Nativity"
                ),
                ItineraryItem(
                    id = 3,
                    startTime = "10:30",
                    endTime = "12:00",
                    type = "activity",
                    description = "Guided City Tour"
                ),
                ItineraryItem(
                    id = 4,
                    startTime = "12:00",
                    endTime = "13:30",
                    type = "activity",
                    description = "Lunch"
                ),
                ItineraryItem(
                    id = 5,
                    startTime = "14:00",
                    endTime = "16:00",
                    type = "activity",
                    description = "Museum Visit"
                )
            )
        )
    }
}