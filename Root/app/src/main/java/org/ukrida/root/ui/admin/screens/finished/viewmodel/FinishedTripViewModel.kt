package org.ukrida.root.ui.admin.screens.finished.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.CompletedTrip
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.utils.Resource

data class FinishedTripUiState(
    val isLoading: Boolean = false,
    val trips: List<CompletedTrip> = emptyList(),
    val errorMessage: String? = null
)

class FinishedTripViewModel(private val repository: AdminRepository) : ViewModel() {

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

            when (val result = repository.getCompletedTrips()) {

                is Resource.Success -> {

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        trips = result.data
                    )
                }

                is Resource.Error -> {

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }

                else -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    companion object {
        fun factory(repository: AdminRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FinishedTripViewModel(repository) as T
                }
            }
    }
}