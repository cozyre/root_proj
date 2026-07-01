package org.ukrida.root.ui.user.screens.groupmenus.dailybread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.DevotionRepository
import org.ukrida.root.ui.user.screens.historydetail.viewmodel.HistoryDetailViewModel

class DailyBreadViewModelFactory(
    private val devotionRepository: DevotionRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DailyBreadViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DailyBreadViewModel(
                devotionRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}