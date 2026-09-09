package org.ukrida.root.ui.admin.screens.broadcast.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.data.repository.NotificationRepository

sealed class SendState {
    object Idle : SendState()
    object Loading : SendState()
    data class Success(val recipientCount: Int) : SendState()
    data class Error(val message: String) : SendState()
}

class BroadcastViewModel(
    private val notificationRepo: NotificationRepository,
    private val groupRepo: GroupRepository
) : ViewModel() {
    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups: StateFlow<List<Group>> = _groups
    
    private val _sendState = MutableStateFlow<SendState>(SendState.Idle)
    val sendState: StateFlow<SendState> = _sendState

    fun loadGroups() {
        viewModelScope.launch { 
            groupRepo.getAllTours().onSuccess { 
                _groups.value = it 
            } 
        }
    }

    fun sendBroadcast(groupId: Int, message: String) {
        viewModelScope.launch {
            _sendState.value = SendState.Loading
            notificationRepo.broadcast(groupId, message)
                .onSuccess { 
                    _sendState.value = SendState.Success(it.recipientCount)
                    // Reset message state handled in UI or here? Usually UI.
                }
                .onFailure { _sendState.value = SendState.Error(it.message ?: "Failed to send broadcast") }
        }
    }

    fun resetSendState() {
        _sendState.value = SendState.Idle
    }
}