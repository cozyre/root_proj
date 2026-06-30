package org.ukrida.root.ui.user.screens.history.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.ui.user.screens.history.viewmodel.HistoryViewModel

class HistoryViewModelFactory (
    private val groupRepository: GroupRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(
                groupRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}