package org.ukrida.root.ui.admin.screens.broadcast.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.Group
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.screens.broadcast.viewmodel.BroadcastViewModel
import org.ukrida.root.ui.admin.screens.broadcast.viewmodel.SendState
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.H1Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BroadcastScreen(
    viewModel: BroadcastViewModel,
    onMenuClick: () -> Unit
) {
    val groups by viewModel.groups.collectAsState()
    val sendState by viewModel.sendState.collectAsState()
    var selectedGroup by remember { mutableStateOf<Group?>(null) }
    var message by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadGroups()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TopBar(
            title = "BROADCAST",
            onMenuClick = onMenuClick
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Broadcast Notification",
                style = MaterialTheme.typography.titleLarge,
                color = H1Color
            )
            Spacer(Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedGroup?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Group / Trip") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    groups.forEach { g ->
                        DropdownMenuItem(
                            text = { Text(g.name) },
                            onClick = {
                                selectedGroup = g
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Message") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { selectedGroup?.let { viewModel.sendBroadcast(it.id, message) } },
                enabled = selectedGroup != null && message.isNotBlank() && sendState !is SendState.Loading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (sendState is SendState.Loading) "Sending..." else "Send Broadcast")
            }

            Spacer(Modifier.height(16.dp))
            when (val state = sendState) {
                is SendState.Success -> Text(
                    "Sent to ${state.recipientCount} users",
                    color = Color(0xFF2E7D32)
                )
                is SendState.Error -> Text(state.message, color = Color.Red)
                else -> {}
            }
        }
    }
}