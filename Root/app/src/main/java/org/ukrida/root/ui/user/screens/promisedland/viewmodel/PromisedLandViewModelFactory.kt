package org.ukrida.root.ui.user.screens.promisedland.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.GroupRepository

class PromisedLandViewModelFactory(
    private val groupRepository: GroupRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(PromisedLandViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PromisedLandViewModel(
                groupRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}