package org.ukrida.root.ui.admin.screens.trip.viewmodel

import android.util.Log
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
import org.ukrida.root.data.remote.ApiUrl
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.utils.Resource

data class TripUiState(
    val isLoading: Boolean = false,
    val finishedTrips: List<CompletedTrip> = emptyList(),
    val coverImages: Map<Int, String?> = emptyMap(),
    val finishedCoverImages: Map<Int, String?> = emptyMap(),
    val errorMessage: String? = null
)

class TripViewModel : ViewModel() {

    private val groupRepository =
        GroupRepository(RetrofitClient.instance)

    private val adminRepository =
        AdminRepository(RetrofitClient.instance)

    private val galleryRepository =
        GalleryRepository(RetrofitClient.instance)

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

                    val coverMap =
                        result.associate { group ->

                            val imageUrl =
                                when (
                                    val imageResult =
                                        galleryRepository.listImages(group.id)
                                ) {

                                    is Resource.Success -> {

                                        val rawUrl =
                                            imageResult.data
                                                .firstOrNull()
                                                ?.imageUrl

                                        val fixedUrl =
                                            normalizeImageUrl(rawUrl)

                                        Log.d(
                                            "TRIP_IMAGE",
                                            "groupId=${group.id}"
                                        )

                                        Log.d(
                                            "TRIP_IMAGE",
                                            "rawUrl=$rawUrl"
                                        )

                                        Log.d(
                                            "TRIP_IMAGE",
                                            "fixedUrl=$fixedUrl"
                                        )

                                        fixedUrl
                                    }

                                    else -> null
                                }

                            group.id to imageUrl
                        }

                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            coverImages = coverMap
                        )
                }

                .onFailure { error ->

                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            errorMessage =
                                error.message
                                    ?: "Failed to load trips"
                        )
                }
        }
    }

    private fun loadFinishedTrips() {
        viewModelScope.launch {

            when (
                val result =
                    adminRepository.getCompletedTrips()
            ) {

                is Resource.Success -> {

                    val trips =
                        result.data.filter {

                            it.status.equals(
                                "completed",
                                true
                            ) ||

                                    it.status.equals(
                                        "archived",
                                        true
                                    )
                        }

                    val finishedCoverMap =
                        trips.associate { trip ->

                            val imageUrl =
                                when (
                                    val imageResult =
                                        galleryRepository.listImages(trip.id)
                                ) {

                                    is Resource.Success -> {

                                        val rawUrl =
                                            imageResult.data
                                                .firstOrNull()
                                                ?.imageUrl

                                        normalizeImageUrl(rawUrl)
                                    }

                                    else -> null
                                }

                            trip.id to imageUrl
                        }

                    _uiState.value =
                        _uiState.value.copy(
                            finishedTrips = trips,
                            finishedCoverImages = finishedCoverMap
                        )
                }

                is Resource.Error -> {

                    _uiState.value =
                        _uiState.value.copy(
                            errorMessage = result.message
                        )
                }

                else -> Unit
            }
        }
    }

    private fun normalizeImageUrl(
        url: String?
    ): String? {
        return ApiUrl.normalize(url)
    }

    fun refresh() {
        loadTours()
        loadFinishedTrips()
    }
}