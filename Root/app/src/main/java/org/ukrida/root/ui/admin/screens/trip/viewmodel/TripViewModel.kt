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
import org.ukrida.root.data.model.CompletedTrip
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.remote.RetrofitClient
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.utils.Resource

data class TripUiState(
    val isLoading: Boolean = false,
    val finishedTrips: List<CompletedTrip> = emptyList(),
    val errorMessage: String? = null
)

class TripViewModel(
    private val repository: FakeGroupRepository = FakeGroupRepository()
) : ViewModel() {

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

    init {
        loadTours()
        loadFinishedTrips()
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

    private fun loadFinishedTrips() {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            when (val result =
                adminRepository.getCompletedTrips()) {

                is Resource.Success -> {

                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            finishedTrips = result.data.filter {
                                it.status.equals(
                                    "completed",
                                    true
                                )
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
}