package org.ukrida.root.ui.user.screens.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.data.repository.GroupRepository

class HistoryViewModelFactory (
    private val groupRepository: GroupRepository,
    private val galleryRepository: GalleryRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(
                groupRepository,
                galleryRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}