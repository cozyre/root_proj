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
import org.ukrida.root.data.model.SongBrowseItem
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.components.TimelineBar
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.EditSongsViewModel
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.SongItemUiState
import org.ukrida.root.ui.theme.*

@Composable
fun EditSongsScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: EditSongsViewModel
) {
    val songItems     by viewModel.songList.collectAsState()
    val selectedDay   by viewModel.selectedDay.collectAsState()
    val availableDays by viewModel.availableDays.collectAsState()
    val availableSongs by viewModel.availableSongs.collectAsState()

    val filteredItems = songItems.filter { it.day == selectedDay }

    var showDayDropdown by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            TopBar(
                title = "EDIT SONGS",
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

            TextButton(
                onClick = { navController.popBackStack() },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "< Back", color = TitleColor, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            TimelineBar(onStageClick = { stage ->
                when (stage) {
                    "General Information" -> navController.navigate("ongoing_detail/${viewModel.tripId}")
                    "Itinerary" -> navController.navigate("edit_itinerary/${viewModel.tripId}")
                    "Daily Bread" -> navController.navigate("daily_bread/${viewModel.tripId}")
                }
            })

            Spacer(modifier = Modifier.height(20.dp))

            // DAY Dropdown
            Box {
                OutlinedButton(
                    onClick = { showDayDropdown = true },
                    modifier = Modifier.width(110.dp).height(30.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MainButton,
                        contentColor = Color.White
                    ),
                    border = null,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = "DAY $selectedDay",
                        style = MaterialTheme.typography.titleLarge,
                        color = H1Color,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = H1Color)
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

            Text(
                text = "HYMN FOR HIM",
                style = MaterialTheme.typography.titleLarge,
                color = H1Color
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredItems, key = { it.id }) { item ->
                    SongEditRow(
                        item = item,
                        songOptions = availableSongs,
                        onSelectSong = { viewModel.selectSong(item.id, it) },
                        onDelete = { viewModel.deleteItem(item.id) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedButton(
                        onClick = { viewModel.addItem() },
                        modifier = Modifier.wrapContentWidth().height(28.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MainButton,
                            contentColor = H1Color
                        ),
                        border = null,
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
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
                    viewModel.submitSongs()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().height(32.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MainButton)
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
fun SongEditRow(
    item: SongItemUiState,
    songOptions: List<SongBrowseItem>,
    onSelectSong: (SongBrowseItem) -> Unit,
    onDelete: () -> Unit
) {
    var dropdownExpanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .weight(1f)
                .border(1.dp, Color(0xFF6B5C4E), RoundedCornerShape(12.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Judul lagu
                Text(
                    text = item.selectedSongTitle.ifEmpty { "TITLE SONG" },
                    color = if (item.selectedSongTitle.isNotEmpty()) H1Color else Color(0xFF9E8878),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // SELECT SONGS dropdown
                Box {
                    OutlinedButton(
                        onClick = { dropdownExpanded = true },
                        modifier = Modifier.wrapContentWidth().height(28.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF6C7A49),
                            contentColor = H1Color
                        ),
                        border = null,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = "SELECT SONGS",
                            color = H1Color,
                            fontSize = 10.sp,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = H1Color,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },
                        containerColor = DrawerBackground,
                        modifier = Modifier.heightIn(max = 200.dp) // scrollable jika banyak
                    ) {
                        songOptions.forEach { song ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(song.title, color = H1Color, fontSize = 13.sp)
                                        song.author?.let {
                                            Text(it, color = Color(0xFF9E8878), fontSize = 11.sp)
                                        }
                                    }
                                },
                                onClick = {
                                    onSelectSong(song)
                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

        IconButton(
            onClick = onDelete,
            modifier = Modifier
                .size(28.dp)
                .background(Color(0xFFA63232), RoundedCornerShape(8.dp))
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Hapus lagu",
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}