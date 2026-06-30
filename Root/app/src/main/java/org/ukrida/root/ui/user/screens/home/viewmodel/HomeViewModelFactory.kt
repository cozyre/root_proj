package org.ukrida.root.ui.user.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.GroupRepository
import kotlin.jvm.java

class HomeViewModelFactory(
    private val groupRepository: GroupRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(
                groupRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}