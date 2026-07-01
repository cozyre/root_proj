package org.ukrida.root.ui.user.screens.group.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.AccountRepository
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.ui.user.screens.promisedland.viewmodel.PromisedLandViewModel

class GroupViewModelFactory(
    private val groupRepository: GroupRepository,
    private val accountRepository: AccountRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(GroupViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GroupViewModel(
                groupRepository,
                accountRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}