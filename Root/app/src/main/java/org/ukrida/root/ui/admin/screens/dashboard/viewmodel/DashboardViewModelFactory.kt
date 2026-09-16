package org.ukrida.root.ui.admin.screens.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.data.repository.GroupRepository

class DashboardViewModelFactory(
    private val adminRepository: AdminRepository,
    private val groupRepository: GroupRepository,
    private val galleryRepository: GalleryRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(
                adminRepository,
                groupRepository,
                galleryRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}