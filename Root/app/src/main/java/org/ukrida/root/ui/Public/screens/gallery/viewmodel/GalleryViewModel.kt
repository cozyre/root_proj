package org.ukrida.root.ui.Public.screens.gallery.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.data.model.GroupImage

class GalleryViewModel : ViewModel() {

    private val _images = MutableStateFlow<List<GroupImage>>(emptyList())
    val images = _images.asStateFlow()
    fun loadGallery(groupId: Int) {
        // TODO Backend Integration
        // Replace loadDummy() with:
        // repository.listImages(groupId)
        // Then update _images with API response.
        loadDummy(groupId)
    }

    private fun loadDummy(groupId: Int) {

        _images.value = listOf(
            GroupImage(
                id = 1,
                groupId = groupId,
                imageUrl = "",
                caption = "Gallery Image 1",
                imageType = "image",
                sortOrder = 1,
                createdAt = "2026-06-29"
            ),
            GroupImage(
                id = 2,
                groupId = groupId,
                imageUrl = "",
                caption = "Gallery Image 2",
                imageType = "image",
                sortOrder = 2,
                createdAt = "2026-06-29"
            ),
            GroupImage(
                id = 3,
                groupId = groupId,
                imageUrl = "",
                caption = "Gallery Image 3",
                imageType = "image",
                sortOrder = 3,
                createdAt = "2026-06-29"
            ),
            GroupImage(
                id = 4,
                groupId = groupId,
                imageUrl = "",
                caption = "Gallery Image 4",
                imageType = "image",
                sortOrder = 4,
                createdAt = "2026-06-29"
            ),
            GroupImage(
                id = 5,
                groupId = groupId,
                imageUrl = "",
                caption = "Gallery Image 5",
                imageType = "image",
                sortOrder = 5,
                createdAt = "2026-06-29"
            ),
            GroupImage(
                id = 6,
                groupId = groupId,
                imageUrl = "",
                caption = "Gallery Image 6",
                imageType = "image",
                sortOrder = 6,
                createdAt = "2026-06-29"
            ),
            GroupImage(
                id = 7,
                groupId = groupId,
                imageUrl = "",
                caption = "Gallery Image 7",
                imageType = "image",
                sortOrder = 7,
                createdAt = "2026-06-29"
            ),
            GroupImage(
                id = 8,
                groupId = groupId,
                imageUrl = "",
                caption = "Gallery Image 8",
                imageType = "image",
                sortOrder = 8,
                createdAt = "2026-06-29"
            )
        )
    }
}