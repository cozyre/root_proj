package org.ukrida.root.ui.user.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Group

class HomeViewModel : ViewModel() {
    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups = _groups.asStateFlow()
    init {
        loadGroups()
    }
    private fun loadGroups() {
        viewModelScope.launch {
            // TODO: Ganti menjadi repository.getAllTours() saat backend selesai
            _groups.value = getDummyGroups()
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
                status = "Open",
                startDate = "2026-10-01",
                endDate = "2026-10-12",
                meetupTime = "08:00",
                meetupAddress = "Soekarno Hatta Airport",
                joinDate = null,
                statusJoin = null
            )
        )
    }
}