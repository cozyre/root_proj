package org.ukrida.root.ui.admin.screens.trip.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.ukrida.root.data.fake.FakeGroupRepository
import org.ukrida.root.data.model.Group

class TripViewModel(
    private val repository: FakeGroupRepository = FakeGroupRepository()
) : ViewModel() {

    private val _groups =
        MutableStateFlow<List<Group>>(emptyList())

    val groups: StateFlow<List<Group>> =
        _groups.asStateFlow()

    val ongoingTrips: StateFlow<List<Group>> =
        groups.map { list ->
            list.filter {
                it.status.lowercase() in listOf(
                    "upcoming",
                    "active"
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val finishedTrips: StateFlow<List<Group>> =
        groups.map { list ->
            list.filter {
                it.status.equals(
                    "completed",
                    true
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        loadTours()
    }

    private fun loadTours() {

        viewModelScope.launch {

            repository
                .getAllTours()
                .onSuccess {
                    _groups.value = it
                }
        }
    }
}