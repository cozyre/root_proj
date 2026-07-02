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
import org.ukrida.root.data.model.CompletedTrip
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.remote.RetrofitClient
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.utils.Resource

data class TripUiState(
    val isLoading: Boolean = false,
    val finishedTrips: List<CompletedTrip> = emptyList(),
    val errorMessage: String? = null
)

class TripViewModel : ViewModel() {

    private val groupRepository =
        GroupRepository(RetrofitClient.instance)

    private val adminRepository =
        AdminRepository(RetrofitClient.instance)

    private val _groups =
        MutableStateFlow<List<Group>>(emptyList())

    val groups: StateFlow<List<Group>> =
        _groups.asStateFlow()

    private val _uiState =
        MutableStateFlow(TripUiState())

    val uiState: StateFlow<TripUiState> =
        _uiState.asStateFlow()

    val ongoingTrips: StateFlow<List<Group>> =
        groups.map { list ->
            list.filter {
                it.status.equals("upcoming", ignoreCase = true) ||
                        it.status.equals("active", ignoreCase = true)
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        loadTours()
        loadFinishedTrips()
    }

    private fun loadTours() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            groupRepository.getAllTours()
                .onSuccess { result ->
                    _groups.value = result

                    _uiState.value = _uiState.value.copy(
                        isLoading = false
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Failed to load trips"
                    )
                }
        }
    }

    private fun loadFinishedTrips() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            when (val result = adminRepository.getCompletedTrips()) {
                is Resource.Success -> {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            finishedTrips = result.data.filter {
                                it.status.equals("completed", true) ||
                                        it.status.equals("archived", true)
                            }
                        )
                }

                is Resource.Error -> {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                }

                else -> Unit
            }
        }
    }

    fun refresh() {
        loadTours()
        loadFinishedTrips()
    }
}