package org.ukrida.root.ui.user.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.NotificationItem
import org.ukrida.root.data.repository.NotificationRepository

@Composable
fun NotificationPopup(viewModel: NotificationViewModel, onDismiss: () -> Unit) {
    val notifications by viewModel.notifications.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    LaunchedEffect(Unit) { viewModel.loadNotifications() }

    Popup(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.width(320.dp).heightIn(max = 400.dp)) {
            Column {
                Text("Notifications", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(12.dp))
                Divider()
                when {
                    isLoading -> Box(Modifier.fillMaxWidth().padding(24.dp), Alignment.Center) { CircularProgressIndicator() }
                    notifications.isEmpty() -> Text("No notifications", modifier = Modifier.padding(16.dp))
                    else -> LazyColumn {
                        items(notifications) { notif ->
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .clickable { viewModel.markRead(notif.id) }
                                    .background(if (notif.isRead == 0) Color(0xFFEFF6FF) else Color.Transparent)
                                    .padding(12.dp)
                            ) {
                                Column {
                                    Text(notif.message, style = MaterialTheme.typography.bodyMedium)
                                    Text(notif.createdAt, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                }
                            }
                            Divider()
                        }
                    }
                }
            }
        }
    }
}