package org.ukrida.root.ui.user.screens.history.viewmodel

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

class HistoryViewModel(
    private val groupRepository: GroupRepository,
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val _historyGroups =
        MutableStateFlow<Resource<List<Group>>>(Resource.Loading())

    val historyGroups: StateFlow<Resource<List<Group>>> =
        _historyGroups

    private val _coverImages =
        MutableStateFlow<Map<Int, String?>>(emptyMap())

    val coverImages: StateFlow<Map<Int, String?>> =
        _coverImages.asStateFlow()

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

            loadCoverImages(tours)

            _historyGroups.value =
                Resource.Success(tours)

        } else {

            _historyGroups.value = Resource.Error(
                result.exceptionOrNull()?.message
                    ?: "Failed to load Tours"
            )
        }
    }

    private suspend fun loadCoverImages(
        groups: List<Group>
    ) {

        val coverMap = groups.associate { group ->

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

                    else -> null
                }

            group.id to imageUrl
        }

        _coverImages.value = coverMap
    }

    private fun normalizeImageUrl(
        url: String?
    ): String? {

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