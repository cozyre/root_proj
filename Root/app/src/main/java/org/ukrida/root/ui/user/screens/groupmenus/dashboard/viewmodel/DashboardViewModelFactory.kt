package org.ukrida.root.ui.user.screens.groupmenus.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.AccountRepository
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.ui.user.screens.groupmenus.dailybread.viewmodel.DailyBreadViewModel

class DashboardViewModelFactory(
    private val accountRepository: AccountRepository,
    private val galleryRepository: GalleryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DashboardViewModel(
                accountRepository,
                galleryRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}