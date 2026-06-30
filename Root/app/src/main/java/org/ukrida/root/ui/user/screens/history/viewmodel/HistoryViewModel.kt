package org.ukrida.root.ui.user.screens.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.utils.Resource

class HistoryViewModel(
    private val groupRepository: GroupRepository
) : ViewModel() {
    private val _historyGroups = MutableStateFlow<Resource<List<Group>>>(Resource.Loading())
    val historyGroups: StateFlow<Resource<List<Group>>> = _historyGroups

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            loadPastTours()
        }
    }

    private suspend fun loadPastTours() {
        _historyGroups.value = Resource.Loading()
        val result = groupRepository.getPastTours(20)

        if (result.isSuccess) {
            val tours = result.getOrNull() ?: emptyList()
            _historyGroups.value = Resource.Success(tours)
        } else {
            _historyGroups.value = Resource.Error(
                result.exceptionOrNull()?.message ?: "Failed to load Tours"
            )
        }
    }
}