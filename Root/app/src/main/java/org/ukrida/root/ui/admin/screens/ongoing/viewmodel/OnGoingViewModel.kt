package org.ukrida.root.ui.admin.screens.ongoing.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.remote.RetrofitClient
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.utils.Resource

data class OnGoingUiState(
    val isLoading: Boolean = false,
    val groups: List<Group> = emptyList(),
    val coverImages: Map<Int, String?> = emptyMap(),
    val errorMessage: String? = null,

)

class OnGoingViewModel : ViewModel() {

    private val groupRepository =
        GroupRepository(RetrofitClient.instance)

    private val adminRepository =
        AdminRepository(RetrofitClient.instance)

    private val galleryRepository =
        GalleryRepository(RetrofitClient.instance)

    private val _uiState =
        MutableStateFlow(OnGoingUiState())

    val uiState: StateFlow<OnGoingUiState> =
        _uiState.asStateFlow()

    init {
        loadOngoingTours()
    }

    fun loadOngoingTours() {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            groupRepository.getAllTours()
                .onSuccess { allGroups ->

                    val ongoingGroups = allGroups.filter {
                        it.status.equals("active", ignoreCase = true) ||
                                it.status.equals("upcoming", ignoreCase = true)
                    }

                    val coverMap =
                        ongoingGroups.associate { group ->
                            val imageUrl =
                                when (
                                    val imageResult = galleryRepository.listImages(group.id)
                                ) {
                                    is Resource.Success -> {
                                        val rawUrl = imageResult.data.firstOrNull()?.imageUrl
                                        val fixedUrl = normalizeImageUrl(rawUrl)

                                        Log.d("ONGOING_IMAGE", "groupId=${group.id}")
                                        Log.d("ONGOING_IMAGE", "rawUrl=$rawUrl")
                                        Log.d("ONGOING_IMAGE", "fixedUrl=$fixedUrl")

                                        fixedUrl
                                    }

                                    is Resource.Error -> null

                                    is Resource.Loading -> null
                                }

                            group.id to imageUrl
                        }

                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            groups = ongoingGroups,
                            coverImages = coverMap
                        )
                }

                .onFailure {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = it.message ?: "Terjadi kesalahan"
                    )
                }
        }
    }

    fun refresh() {
        loadOngoingTours()
    }

    private fun normalizeImageUrl(url: String?): String? {
        if (url.isNullOrBlank()) return null

        return url
            .replace("http://localhost/", "http://10.0.2.2/")
            .replace("http://127.0.0.1/", "http://10.0.2.2/")
            .replace("https://localhost/", "http://10.0.2.2/")
    }

    fun deleteTrip(id: Int) {
        viewModelScope.launch {

            val result = adminRepository.deleteTrip(id)

            result.onSuccess {
                loadOngoingTours()
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    errorMessage = it.message ?: "Gagal menghapus trip"
                )
            }
        }
    }
}