package org.ukrida.root.ui.admin.screens.finished.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.fake.FakeGroupRepository
import org.ukrida.root.data.model.Group

data class FinishedTripUiState(
    val isLoading: Boolean = false,
    val groups: List<Group> = emptyList(),
    val errorMessage: String? = null
)

class FinishedTripViewModel : ViewModel() {

    private val repository = FakeGroupRepository()

    private val _uiState = MutableStateFlow(FinishedTripUiState())
    val uiState: StateFlow<FinishedTripUiState> = _uiState.asStateFlow()

    init {
        loadFinishedTrips()
    }

    fun loadFinishedTrips() {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            repository.getAllTours()
                .onSuccess { allGroups ->

                    val completedGroups = allGroups.filter {
                        it.status.equals("completed", ignoreCase = true)
                    }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        groups = completedGroups
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Terjadi kesalahan"
                    )
                }
        }
    }
}