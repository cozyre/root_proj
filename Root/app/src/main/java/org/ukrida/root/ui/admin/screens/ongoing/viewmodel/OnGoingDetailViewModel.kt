package org.ukrida.root.ui.admin.screens.ongoing.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.ui.admin.screens.ongoing.model.Trip

// Langsung minta tripId: Int di konstruktor utamanya
class OnGoingDetailViewModel(
    val tripId: Int
) : ViewModel() {

    private val _tripState = MutableStateFlow<Trip?>(null)
    val tripState: StateFlow<Trip?> = _tripState.asStateFlow()

    private val tripsMockData = listOf(
        Trip(id = 1, title = "Promised Land", description = "Lorem ipsum dolor sit amet", imageUrl = null, dateRange = "01/07/2026"),
        Trip(id = 2, title = "Holy Journey", description = "Lorem ipsum dolor sit amet", imageUrl = null, dateRange = "15/07/2026")
    )

    init {
        loadTripDetail()
    }

    private fun loadTripDetail() {
        _tripState.value = tripsMockData.find { it.id == tripId }
    }
}