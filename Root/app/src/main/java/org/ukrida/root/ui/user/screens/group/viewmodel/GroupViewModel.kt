package org.ukrida.root.ui.user.screens.group.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.data.model.Group

class GroupViewModel : ViewModel() {

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups = _groups.asStateFlow()

    init {
        loadGroups()
    }

    fun loadGroups() {
        // TODO Backend Integration
        // repository.getMyGroups()
        loadDummy()
    }

    private fun loadDummy() {

        _groups.value = listOf(

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
                statusJoin = "Rejected"
            ),

            Group(
                id = 3,
                name = "Egypt Pilgrimage",
                description = "Journey through biblical Egypt.",
                location = "Egypt",
                dresscode = "Casual",
                status = "Completed",
                startDate = "2026-12-01",
                endDate = "2026-12-10",
                meetupTime = "08:30",
                meetupAddress = "Soekarno Hatta Airport",
                joinDate = "2026-09-10",
                statusJoin = "Pending"
            )

        )

    }

}