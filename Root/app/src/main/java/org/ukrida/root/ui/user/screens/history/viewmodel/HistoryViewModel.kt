package org.ukrida.root.ui.user.screens.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Group

class HistoryViewModel : ViewModel() {
    private val _historyGroups =
        MutableStateFlow<List<Group>>(emptyList())
    val historyGroups =
        _historyGroups.asStateFlow()
    init {
        loadHistory()
    }
    private fun loadHistory() {
        viewModelScope.launch {
            val groups = getDummyGroups()
            _historyGroups.value = groups
        }
    }
    private fun getDummyGroups(): List<Group> {
        return listOf(
            Group(
                id = 1,
                name = "Holy Land",
                description = "Experience the places where Jesus walked.",
                location = "Jerusalem",
                dresscode = "Casual",
                status = "Completed",
                startDate = "2026-10-01",
                endDate = "2026-10-12",
                meetupTime = "08:00",
                meetupAddress = "Soekarno Hatta Airport",
                joinDate = "2026-07-15",
                statusJoin = "Approved"
            ),
            Group(
                id = 2,
                name = "Jordan Pilgrimage",
                description = "Visit the Jordan River and Mount Nebo.",
                location = "Jordan",
                dresscode = "Casual",
                status = "Completed",
                startDate = "2026-11-05",
                endDate = "2026-11-12",
                meetupTime = "09:00",
                meetupAddress = "Soekarno Hatta Airport",
                joinDate = "2026-08-01",
                statusJoin = "Approved"
            ),
            Group(
                id = 3,
                name = "Holy Land",
                description = "Experience the places where Jesus walked.",
                location = "Jerusalem",
                dresscode = "Casual",
                status = "Completed",
                startDate = "2026-10-01",
                endDate = "2026-10-12",
                meetupTime = "08:00",
                meetupAddress = "Soekarno Hatta Airport",
                joinDate = "2026-07-15",
                statusJoin = "Approved"
            )
        )
    }
}