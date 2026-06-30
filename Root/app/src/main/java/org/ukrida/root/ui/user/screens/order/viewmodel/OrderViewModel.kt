package org.ukrida.root.ui.user.screens.order.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Group

class OrderViewModel : ViewModel() {
    private val _group = MutableStateFlow<Group?>(null)
    val group = _group.asStateFlow()
    fun loadOrder(groupId: Int) {
        viewModelScope.launch {
            // TODO:
            // repository.getTourById(groupId)
            _group.value = getDummyGroup(groupId)
        }
    }

    private fun getDummyGroup(groupId: Int): Group {
        val groups = listOf(
            Group(
                id = 1,
                name = "Holy Land",
                description = "Experience the places where Jesus walked.",
                location = "Jerusalem",
                dresscode = "Casual",
                status = "Open",
                startDate = "2026-10-01",
                endDate = "2026-10-12",
                meetupTime = "08:00",
                meetupAddress = "Soekarno Hatta Airport",
                joinDate = null,
                statusJoin = null
            ),
            Group(
                id = 2,
                name = "Jordan Pilgrimage",
                description = "Visit the Jordan River and Mount Nebo.",
                location = "Jordan",
                dresscode = "Casual",
                status = "Open",
                startDate = "2026-11-05",
                endDate = "2026-11-12",
                meetupTime = "09:00",
                meetupAddress = "Soekarno Hatta Airport",
                joinDate = null,
                statusJoin = "Pending"
            )
        )
        return groups.firstOrNull { it.id == groupId }
            ?: groups.first()
    }
}