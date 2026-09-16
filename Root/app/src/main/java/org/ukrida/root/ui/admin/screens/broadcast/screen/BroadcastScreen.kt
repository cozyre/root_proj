package org.ukrida.root.ui.admin.screens.broadcast.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.Group
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.screens.broadcast.viewmodel.BroadcastViewModel
import org.ukrida.root.ui.admin.screens.broadcast.viewmodel.SendState
import org.ukrida.root.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BroadcastScreen(
    viewModel: BroadcastViewModel,
    onMenuClick: () -> Unit
) {

    val groups by viewModel.groups.collectAsState()
    val sendState by viewModel.sendState.collectAsState()

    var selectedGroup by remember {
        mutableStateOf<Group?>(null)
    }

    var message by remember {
        mutableStateOf("")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.loadGroups()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBrown)
    ) {

        TopBar(
            title = "BROADCAST",
            onMenuClick = onMenuClick
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "SEND BROADCAST",
                style = MaterialTheme.typography.titleLarge,
                color = H1Color
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text =
                    "Send announcements, reminders, and important information to all members of a selected trip.",
                style = MaterialTheme.typography.bodyLarge,
                color = BodyColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            // SELECT TRIP CARD
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        DrawerBackground,
                        RoundedCornerShape(24.dp)
                    )
                    .padding(20.dp)
            ) {

                Text(
                    text = "SELECT TRIP",
                    style = MaterialTheme.typography.titleMedium,
                    color = H1Color
                )

                Spacer(modifier = Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {

                    OutlinedTextField(
                        value = selectedGroup?.name ?: "",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        placeholder = {
                            Text("Choose a trip")
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MicroElement,
                            unfocusedBorderColor = BodyColor,
                            focusedTextColor = H1Color,
                            unfocusedTextColor = H1Color
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {

                        groups.forEach { group ->

                            DropdownMenuItem(
                                text = {
                                    Text(group.name)
                                },
                                onClick = {
                                    selectedGroup = group
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // MESSAGE CARD
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        DrawerBackground,
                        RoundedCornerShape(24.dp)
                    )
                    .padding(20.dp)
            ) {

                Text(
                    text = "MESSAGE",
                    style = MaterialTheme.typography.titleMedium,
                    color = H1Color
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = message,
                    onValueChange = {
                        message = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 6,
                    placeholder = {
                        Text("Write your message here...")
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MicroElement,
                        unfocusedBorderColor = BodyColor,
                        focusedTextColor = H1Color,
                        unfocusedTextColor = H1Color
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    selectedGroup?.let {
                        viewModel.sendBroadcast(
                            it.id,
                            message
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled =
                    selectedGroup != null &&
                            message.isNotBlank() &&
                            sendState !is SendState.Loading,
                shape = RoundedCornerShape(50.dp),
                colors = buttonColors(
                    containerColor = MicroElement
                )
            ) {

                Text(
                    text =
                        if (sendState is SendState.Loading)
                            "SENDING..."
                        else
                            "SEND BROADCAST",
                    color = H1Color
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            when (val state = sendState) {

                is SendState.Success -> {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = DrawerBackground
                        )
                    ) {

                        Text(
                            text = "Broadcast sent to ${state.recipientCount} users",
                            color = H1Color,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                is SendState.Error -> {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = DrawerBackground
                        )
                    ) {

                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                else -> Unit
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}