package org.ukrida.root.ui.user.screens.groupmenus.gallery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.GalleryRepository

class GalleryViewModelFactory(
    private val galleryRepository: GalleryRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(GalleryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GalleryViewModel(
                galleryRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}