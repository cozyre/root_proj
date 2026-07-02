package org.ukrida.root.ui.user.screens.groupmenus.gallery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.GroupImage
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.utils.Resource
import java.io.File

class GalleryViewModel(
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val _images = MutableStateFlow<Resource<List<GroupImage>>>(Resource.Loading())
    val images: StateFlow<Resource<List<GroupImage>>> = _images.asStateFlow()

    private val _uploadStatus = MutableStateFlow<Resource<GroupImage>?>(null)
    val uploadStatus: StateFlow<Resource<GroupImage>?> = _uploadStatus.asStateFlow()

    fun loadGallery(groupId: Int) {
        viewModelScope.launch {
            _images.value = Resource.Loading()
            _images.value = galleryRepository.listImages(groupId)
        }
    }

    fun uploadImage(groupId: Int, imageFile: File, caption: String? = null) {
        viewModelScope.launch {
            _uploadStatus.value = Resource.Loading()
            val result = galleryRepository.uploadImage(groupId, imageFile, caption)
            _uploadStatus.value = result
            if (result is Resource.Success) {
                loadGallery(groupId)
            }
        }
    }

    fun resetUploadStatus() {
        _uploadStatus.value = null
    }
}
