package org.ukrida.root.ui.admin.screens.ongoing.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.ukrida.root.ui.admin.screens.ongoing.components.TimelineBar
import org.ukrida.root.ui.admin.screens.ongoing.model.ItineraryItem
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.EditItineraryViewModel
import org.ukrida.root.ui.theme.*

@Composable
fun EditItineraryScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: EditItineraryViewModel
) {
    val itineraryItems by viewModel.itineraryList.collectAsState()
    val selectedDay by viewModel.selectedDay.collectAsState()

    // Filter item sesuai hari yang dipilih
    val filteredItems = itineraryItems.filter { it.day == selectedDay }

    // Daftar hari yang tersedia dari data
    val availableDays = itineraryItems.map { it.day }.distinct().sorted()

    var showDayDropdown by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            OnGoingTopBar(
                title = "EDIT ITINERARY",
                onMenuClick = onMenuClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            // < Back
            TextButton(
                onClick = { navController.popBackStack() },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "< Back",
                    color = TitleColor,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Timeline Bar
            TimelineBar(onStageClick = { stage ->
                when (stage) {
                    "General Information" -> navController.navigate("ongoing_detail/${viewModel.tripId}")
                    "Daily Bread" -> navController.navigate("daily_bread/${viewModel.tripId}")
                    "Hymn for Him" -> navController.navigate("edit_songs/${viewModel.tripId}")
                }
            })

            Spacer(modifier = Modifier.height(20.dp))

            // DAY X Dropdown
            Box {
                OutlinedButton(
                    onClick = { showDayDropdown = true },
                    modifier = Modifier
                        .width(110.dp)
                        .height(30.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MainButton,
                        contentColor = Color.White
                    ),
                    border = null
                ) {
                    Text(
                        text = "DAY $selectedDay",
                        style = MaterialTheme.typography.titleLarge,
                        color = H1Color,
                        fontSize = 15.sp
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
                    onDismissRequest = { showDayDropdown = false },
                    containerColor = DrawerBackground
                ) {
                    availableDays.forEach { day ->
                        DropdownMenuItem(
                            text = { Text("DAY $day", color = H1Color) },
                            onClick = {
                                viewModel.selectDay(day)
                                showDayDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Label ITINERARY
            Text(
                text = "ITINERARY",
                style = MaterialTheme.typography.titleLarge,
                color = H1Color
            )

            Spacer(modifier = Modifier.height(12.dp))

            // List itinerary + ADD MORE
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredItems, key = { it.id }) { item ->
                    ItineraryEditCard(
                        item = item,
                        onStartTimeChange = { viewModel.updateStartTime(item.id, it) },
                        onEndTimeChange = { viewModel.updateEndTime(item.id, it) },
                        onActivityChange = { viewModel.updateActivity(item.id, it) },
                        onDelete = { viewModel.deleteItem(item.id) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))

                    // ADD MORE button — outline style
                    OutlinedButton(
                        onClick = { viewModel.addItem() },
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(28.dp),
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

            // SUBMIT button
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainButton
                )
            ) {
                Text(
                    text = "SUBMIT",
                    color = H1Color,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ItineraryEditCard(
    item: ItineraryItem,
    onStartTimeChange: (String) -> Unit,
    onEndTimeChange: (String) -> Unit,
    onActivityChange: (String) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Card utama
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .weight(1f)
                .padding(end = 20.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFF6B5C4E),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(modifier = Modifier.padding(12.dp)) {

                // Row: XX:XX - XX:XX + textarea
                Row(
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top,

                ) {
                    // Kolom kiri: start time - end time (pill style)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Start time pill
                        OutlinedTextField(
                            value = item.startTime,
                            onValueChange = onStartTimeChange,
                            modifier = Modifier.width(72.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(50.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = TitleColor,
                                unfocusedBorderColor = Color(0xFF6B5C4E),
                                focusedContainerColor = Color(0xFF2A2018),
                                unfocusedContainerColor = Color(0xFF2A2018)
                            ),
                            textStyle = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        )

                        Text("-", color = Color.White, fontSize = 14.sp)

                        // End time pill
                        OutlinedTextField(
                            value = item.endTime,
                            onValueChange = onEndTimeChange,
                            modifier = Modifier.width(72.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(50.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = TitleColor,
                                unfocusedBorderColor = Color(0xFF6B5C4E),
                                focusedContainerColor = Color(0xFF2A2018),
                                unfocusedContainerColor = Color(0xFF2A2018)
                            ),
                            textStyle = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        )
                    }

                    // Kolom kanan: textarea activity
                    OutlinedTextField(
                        value = item.activity,
                        onValueChange = onActivityChange,
                        placeholder = { Text("type here", color = Color(0xFF6B5C4E)) },
                        modifier = Modifier
                            .weight(1f)
                            .height(110.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = TitleColor,
                            unfocusedBorderColor = Color(0xFF6B5C4E),
                            focusedContainerColor = Color(0xFF2A2018),
                            unfocusedContainerColor = Color(0xFF2A2018)
                        ),
                        textStyle = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Tombol delete merah (di luar card)
        IconButton(
            onClick = onDelete,
            modifier = Modifier
                .size(18.dp)
                .background(
                    color = Color(0xFFA63232),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Hapus",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}