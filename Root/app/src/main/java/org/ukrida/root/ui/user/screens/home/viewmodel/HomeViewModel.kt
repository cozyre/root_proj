package org.ukrida.root.ui.user.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.utils.Resource

class HomeViewModel(
    private val groupRepository: GroupRepository,
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val _groups =
        MutableStateFlow<Resource<List<Group>>>(Resource.Loading())

    val groups: StateFlow<Resource<List<Group>>> =
        _groups

    private val _coverImages =
        MutableStateFlow<Map<Int, String?>>(emptyMap())

    val coverImages: StateFlow<Map<Int, String?>> =
        _coverImages.asStateFlow()

    init {
        loadGroups()
    }

    private fun loadGroups() {
        viewModelScope.launch {
            loadAllTours()
        }
    }

    private suspend fun loadAllTours() {

        _groups.value = Resource.Loading()

        val result = groupRepository.getAllTours()

        if (result.isSuccess) {

            val tours =
                result.getOrNull() ?: emptyList()

            val coverMap =
                tours.associate { group ->

                    val imageUrl =
                        when (
                            val imageResult =
                                galleryRepository.listImages(group.id)
                        ) {
                            is Resource.Success -> {
                                normalizeImageUrl(
                                    imageResult.data.firstOrNull()?.imageUrl
                                )
                            }

                            is Resource.Error -> null
                            is Resource.Loading -> null
                        }

                    group.id to imageUrl
                }

            _coverImages.value = coverMap

            _groups.value =
                Resource.Success(tours)

        } else {

            _groups.value =
                Resource.Error(
                    result.exceptionOrNull()?.message
                        ?: "Failed to load Tours"
                )
        }
    }

    private fun normalizeImageUrl(url: String?): String? {

        if (url.isNullOrBlank()) return null

        return url
            .replace(
                "http://localhost/",
                "http://10.0.2.2/"
            )
            .replace(
                "http://127.0.0.1/",
                "http://10.0.2.2/"
            )
            .replace(
                "https://localhost/",
                "http://10.0.2.2/"
            )
    }
}