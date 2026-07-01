package org.ukrida.root.ui.admin.screens.ongoing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.fake.FakeGroupRepository
import org.ukrida.root.data.model.Group

data class OnGoingUiState(
    val isLoading: Boolean = false,
    val groups: List<Group> = emptyList(),
    val errorMessage: String? = null
)

class OnGoingViewModel : ViewModel() {

    private val repository = FakeGroupRepository()

    private val _uiState = MutableStateFlow(OnGoingUiState())
    val uiState: StateFlow<OnGoingUiState> = _uiState.asStateFlow()

    init {
        loadOngoingTours()
    }

    fun loadOngoingTours() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            repository.getAllTours()
                .onSuccess { allGroups ->
                    val ongoingGroups = allGroups.filter {
                        it.status.equals("active", ignoreCase = true) ||
                                it.status.equals("upcoming", ignoreCase = true)
                    }
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        groups = ongoingGroups
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