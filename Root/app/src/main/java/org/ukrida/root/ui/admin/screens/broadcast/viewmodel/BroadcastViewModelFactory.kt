package org.ukrida.root.ui.admin.screens.broadcast.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.data.repository.NotificationRepository

class BroadcastViewModelFactory(
    private val notificationRepo: NotificationRepository,
    private val groupRepo: GroupRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BroadcastViewModel::class.java)) {
            return BroadcastViewModel(notificationRepo, groupRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}