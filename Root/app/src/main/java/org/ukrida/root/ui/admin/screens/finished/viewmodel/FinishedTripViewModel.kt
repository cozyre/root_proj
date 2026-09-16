package org.ukrida.root.ui.admin.screens.finished.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.remote.ApiUrl
import org.ukrida.root.data.model.CompletedTrip
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.utils.Resource

data class FinishedTripUiState(
    val isLoading: Boolean = false,
    val trips: List<CompletedTrip> = emptyList(),
    val coverImages: Map<Int, String?> = emptyMap(),
    val errorMessage: String? = null
)

class FinishedTripViewModel(
    private val repository: AdminRepository,
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(FinishedTripUiState())

    val uiState: StateFlow<FinishedTripUiState> =
        _uiState.asStateFlow()

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

                    val trips = result.data

                    val coverMap =
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

                                        val fixedUrl =
                                            normalizeImageUrl(rawUrl)

                                        Log.d(
                                            "FINISHED_IMAGE",
                                            "tripId=${trip.id}"
                                        )

                                        Log.d(
                                            "FINISHED_IMAGE",
                                            "rawUrl=$rawUrl"
                                        )

                                        Log.d(
                                            "FINISHED_IMAGE",
                                            "fixedUrl=$fixedUrl"
                                        )

                                        fixedUrl
                                    }

                                    is Resource.Error -> {

                                        Log.e(
                                            "FINISHED_IMAGE",
                                            "gallery error tripId=${trip.id} : ${imageResult.message}"
                                        )

                                        null
                                    }

                                    is Resource.Loading -> null
                                }

                            trip.id to imageUrl
                        }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        trips = trips,
                        coverImages = coverMap
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

    fun refresh() {
        loadFinishedTrips()
    }

    private fun normalizeImageUrl(
        url: String?
    ): String? {
        return ApiUrl.normalize(url)
    }

    companion object {

        fun factory(
            repository: AdminRepository,
            galleryRepository: GalleryRepository
        ): ViewModelProvider.Factory =

            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {

                    return FinishedTripViewModel(
                        repository,
                        galleryRepository
                    ) as T
                }
            }
    }
}