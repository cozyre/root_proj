package org.ukrida.root.ui.user.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import org.ukrida.root.ui.theme.DarkBrown
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MicroElement
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun NotificationPopup(viewModel: NotificationViewModel, onDismiss: () -> Unit) {
    val notifications by viewModel.notifications.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    LaunchedEffect(Unit) { viewModel.loadNotifications() }

    Popup(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .width(320.dp)
                .heightIn(max = 400.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.background(DrawerBackground)
            ) {

                Text(
                    text = "Notifications",
                    style = MaterialTheme.typography.titleMedium,
                    color = H1Color,
                    modifier = Modifier.padding(16.dp)
                )

                Divider(
                    color = H1Color.copy(alpha = 0.4f)
                )

                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = MicroElement
                            )
                        }
                    }

                    notifications.isEmpty() -> {
                        Text(
                            text = "No notifications",
                            color = Color.LightGray,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    else -> {
                        LazyColumn {
                            items(notifications) { notif ->

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.markRead(notif.id)
                                        }
                                        .background(
                                            if (notif.isRead == 0)
                                                MicroElement.copy(alpha = 0.15f)
                                            else
                                                Color.Transparent
                                        )
                                        .padding(14.dp)
                                ) {

                                    Column {
                                        Text(
                                            text = notif.message,
                                            color = TitleColor,
                                            style = MaterialTheme.typography.bodyMedium
                                        )

                                        Spacer(
                                            modifier = Modifier.height(4.dp)
                                        )

                                        Text(
                                            text = notif.createdAt,
                                            color = Color.Gray,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                }

                                Divider(
                                    color = MicroElement.copy(alpha = 0.2f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}