package org.ukrida.root.ui.user.screens.order.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.AccountRepository
import org.ukrida.root.data.repository.GroupRepository

class OrderViewModelFactory(
    private val groupRepository: GroupRepository,
    private val accountRepository: AccountRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrderViewModel(
                groupRepository,
                accountRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}