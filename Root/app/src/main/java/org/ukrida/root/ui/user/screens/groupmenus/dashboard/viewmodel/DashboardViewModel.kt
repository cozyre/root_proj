package org.ukrida.root.ui.user.screens.groupmenus.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.data.model.Group

class DashboardViewModel : ViewModel() {

    private val _group = MutableStateFlow<Group?>(null)
    val group = _group.asStateFlow()

    fun loadDashboard(groupId: Int) {
        // TODO Backend Integration
        // repository.getGroupDetail(groupId)
        loadDummy(groupId)
    }

    private fun loadDummy(groupId: Int) {

        val groups = listOf(

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
            )

        )

        _group.value =
            groups.firstOrNull {
                it.id == groupId
            } ?: groups.first()

    }

}