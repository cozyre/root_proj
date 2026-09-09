package org.ukrida.root.ui.user.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.NotificationItem
import org.ukrida.root.data.repository.NotificationRepository

class NotificationViewModel(private val repo: NotificationRepository) : ViewModel() {
    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications: StateFlow<List<NotificationItem>> = _notifications
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadNotifications() {
        viewModelScope.launch {
            _isLoading.value = true
            repo.getNotifications().onSuccess { _notifications.value = it }
            _isLoading.value = false
        }
    }

    fun markRead(id: Int) {
        viewModelScope.launch {
            repo.markRead(id)
            _notifications.value = _notifications.value.map {
                if (it.id == id) it.copy(isRead = 1) else it
            }
        }
    }
}