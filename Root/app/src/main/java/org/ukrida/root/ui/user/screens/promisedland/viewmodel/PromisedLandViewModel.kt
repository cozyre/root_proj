package org.ukrida.root.ui.user.screens.promisedland.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.utils.Resource
import kotlin.collections.emptyList

class PromisedLandViewModel(
    private val groupRepository: GroupRepository
) : ViewModel() {
    private val _historyGroups = MutableStateFlow<Resource<List<Group>>>(Resource.Loading())
    val historyGroups: StateFlow<Resource<List<Group>>> = _historyGroups
    private val _allTrips = MutableStateFlow<Resource<List<Group>>>(Resource.Loading())
    val allTrips: StateFlow<Resource<List<Group>>> = _allTrips

    init {
        loadGroups()
    }

    private fun loadGroups() {
        viewModelScope.launch {
            loadAllTours()
            loadHistoryTours()
        }
    }

    private suspend fun loadAllTours() {
        _allTrips.value = Resource.Loading()
        val result = groupRepository.getAllTours()

        if (result.isSuccess) {
            val tours = result.getOrNull() ?: emptyList()
            _allTrips.value = Resource.Success(tours)
        } else {
            _allTrips.value = Resource.Error(
                result.exceptionOrNull()?.message ?: "Failed to load Tours"
            )
        }
    }

    private suspend fun loadHistoryTours() {
        _historyGroups.value = Resource.Loading()
        val result = groupRepository.getPastTours()

        if (result.isSuccess) {
            val tours = result.getOrNull() ?: emptyList()
            _historyGroups.value = Resource.Success(tours)
        } else {
            _historyGroups.value = Resource.Error(
                result.exceptionOrNull()?.message ?: "Past tours not found"
            )
        }
    }
}