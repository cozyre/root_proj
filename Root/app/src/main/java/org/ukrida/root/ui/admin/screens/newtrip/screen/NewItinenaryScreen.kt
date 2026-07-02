package org.ukrida.root.ui.admin.screens.newtrip.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.ukrida.root.ui.admin.components.TimePickerField
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.screens.newtrip.viewmodel.NewItineraryItemUiState
import org.ukrida.root.ui.admin.screens.newtrip.viewmodel.NewItineraryViewModel
import org.ukrida.root.ui.admin.screens.ongoing.components.TimelineBar
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MainButton
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun NewItineraryScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: NewItineraryViewModel
) {
    val items by viewModel.items.collectAsState()
    val selectedDay by viewModel.selectedDay.collectAsState()
    val availableDays by viewModel.availableDays.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val submitSuccess by viewModel.submitSuccess.collectAsState()

    val context = LocalContext.current

    val filteredItems = items.filter {
        it.day == selectedDay
    }

    var showDayDropdown by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    LaunchedEffect(submitSuccess) {
        if (submitSuccess) {
            Toast.makeText(context, "Itinerary berhasil dibuat", Toast.LENGTH_SHORT).show()

            navController.navigate("new_songs/${viewModel.tripId}") {
                popUpTo("new_itinerary/${viewModel.tripId}") {
                    inclusive = true
                }
            }

            viewModel.resetSubmitSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        TopBar(
            title = "ADD ITINERARY",
            onMenuClick = onMenuClick
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = {
                    navController.popBackStack()
                },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "< Back",
                    color = TitleColor,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TimelineBar(
                onStageClick = {}
            )

            Spacer(modifier = Modifier.height(22.dp))

            Box {
                OutlinedButton(
                    onClick = {
                        showDayDropdown = true
                    },
                    modifier = Modifier
                        .width(110.dp)
                        .height(32.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MainButton,
                        contentColor = H1Color
                    ),
                    border = null
                ) {
                    Text(
                        text = "DAY $selectedDay",
                        style = MaterialTheme.typography.titleLarge,
                        color = H1Color,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = H1Color
                    )
                }

                DropdownMenu(
                    expanded = showDayDropdown,
                    onDismissRequest = {
                        showDayDropdown = false
                    },
                    containerColor = DrawerBackground
                ) {
                    if (availableDays.isEmpty()) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Day belum tersedia",
                                    color = H1Color
                                )
                            },
                            onClick = {
                                showDayDropdown = false
                            }
                        )
                    } else {
                        availableDays.forEach { day ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "DAY $day",
                                        color = H1Color
                                    )
                                },
                                onClick = {
                                    viewModel.selectDay(day)
                                    showDayDropdown = false
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Text(
                text = "ITINERARY",
                style = MaterialTheme.typography.titleLarge,
                color = H1Color
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(
                    items = filteredItems,
                    key = { it.id }
                ) { item ->
                    NewItineraryCard(
                        item = item,
                        onStartTimeChange = {
                            viewModel.updateStartTime(item.id, it)
                        },
                        onEndTimeChange = {
                            viewModel.updateEndTime(item.id, it)
                        },
                        onActivityChange = {
                            viewModel.updateActivity(item.id, it)
                        },
                        onDelete = {
                            viewModel.deleteItem(item.id)
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedButton(
                        onClick = {
                            viewModel.addItem()
                        },
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(30.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MainButton,
                            contentColor = H1Color
                        ),
                        border = null
                    ) {
                        Text(
                            text = "ADD MORE",
                            style = MaterialTheme.typography.titleLarge,
                            letterSpacing = 1.sp,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.submitItinerary()
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainButton
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = H1Color,
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "SUBMIT",
                        color = H1Color,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun NewItineraryCard(
    item: NewItineraryItemUiState,
    onStartTimeChange: (String) -> Unit,
    onEndTimeChange: (String) -> Unit,
    onActivityChange: (String) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFF6B5C4E),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimePickerField(
                        value = item.startTime,
                        onTimeSelected = onStartTimeChange
                    )

                    Text(
                        text = "-",
                        color = H1Color,
                        fontSize = 14.sp
                    )

                    TimePickerField(
                        value = item.endTime,
                        onTimeSelected = onEndTimeChange
                    )
                }

                OutlinedTextField(
                    value = item.activity,
                    onValueChange = onActivityChange,
                    placeholder = {
                        Text(
                            text = "type here",
                            color = Color(0xFF6B5C4E),
                            fontSize = 11.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(110.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = H1Color,
                        unfocusedTextColor = H1Color,
                        focusedBorderColor = TitleColor,
                        unfocusedBorderColor = Color(0xFF6B5C4E),
                        focusedContainerColor = Color(0xFF2A2018),
                        unfocusedContainerColor = Color(0xFF2A2018)
                    ),
                    textStyle = MaterialTheme.typography.bodySmall
                )
            }
        }

        IconButton(
            onClick = onDelete,
            modifier = Modifier
                .size(34.dp)
                .background(
                    color = Color(0xFFA63232),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}