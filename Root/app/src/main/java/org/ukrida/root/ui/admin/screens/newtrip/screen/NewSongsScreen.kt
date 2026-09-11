package org.ukrida.root.ui.admin.screens.newtrip.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import org.ukrida.root.data.model.SongBrowseItem
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.screens.newtrip.viewmodel.NewSongItemUiState
import org.ukrida.root.ui.admin.screens.newtrip.viewmodel.NewSongsViewModel
import org.ukrida.root.ui.admin.screens.ongoing.components.TimelineBar
import org.ukrida.root.ui.theme.DarkBrown
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MicroElement
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun NewSongsScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: NewSongsViewModel
) {
    val songItems by viewModel.songItems.collectAsState()
    val selectedDay by viewModel.selectedDay.collectAsState()
    val availableDays by viewModel.availableDays.collectAsState()
    val availableSongs by viewModel.availableSongs.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val submitSuccess by viewModel.submitSuccess.collectAsState()

    val context = LocalContext.current

    val filteredItems = songItems.filter {
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
            Toast.makeText(context, "Songs berhasil ditambahkan", Toast.LENGTH_SHORT).show()

            navController.navigate("new_daily_bread/${viewModel.tripId}") {
                popUpTo("new_songs/${viewModel.tripId}") {
                    inclusive = true
                }
            }

            viewModel.resetSubmitSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBrown)
    ) {
        TopBar(
            title = "ADD SONGS",
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
                        containerColor = MicroElement,
                        contentColor = H1Color
                    ),
                    border = null,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
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
                        tint = H1Color,
                        modifier = Modifier.size(18.dp)
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
                text = "HYMN FOR HIM",
                style = MaterialTheme.typography.titleLarge,
                color = H1Color
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(
                    items = filteredItems,
                    key = { it.id }
                ) { item ->
                    NewSongRow(
                        item = item,
                        songOptions = availableSongs,
                        onSelectSong = { selectedSong ->
                            viewModel.selectSong(
                                itemId = item.id,
                                song = selectedSong
                            )
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
                            containerColor = MicroElement,
                            contentColor = H1Color
                        ),
                        border = null,
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 0.dp)
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
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MicroElement
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
private fun NewSongRow(
    item: NewSongItemUiState,
    songOptions: List<SongBrowseItem>,
    onSelectSong: (SongBrowseItem) -> Unit,
    onDelete: () -> Unit
) {
    var dropdownExpanded by remember {
        mutableStateOf(false)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF9B7355)
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .weight(1f)
                .height(52.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.selectedSongTitle.ifBlank { "TITLE SONG X" },
                        color = H1Color,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 13.sp
                    )

                    if (!item.selectedSongAuthor.isNullOrBlank()) {
                        Text(
                            text = item.selectedSongAuthor,
                            color = Color(0xFFE8D3BC),
                            fontSize = 10.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box {
                    OutlinedButton(
                        onClick = {
                            dropdownExpanded = true
                        },
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(28.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MicroElement,
                            contentColor = H1Color
                        ),
                        border = null,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = "SELECT SONGS",
                            color = H1Color,
                            fontSize = 9.sp,
                            style = MaterialTheme.typography.labelSmall
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = H1Color,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = {
                            dropdownExpanded = false
                        },
                        containerColor = DrawerBackground,
                        modifier = Modifier.heightIn(max = 220.dp)
                    ) {
                        if (songOptions.isEmpty()) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "Lagu belum tersedia",
                                        color = H1Color
                                    )
                                },
                                onClick = {
                                    dropdownExpanded = false
                                }
                            )
                        } else {
                            songOptions.forEach { song ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(
                                                text = song.title,
                                                color = H1Color,
                                                fontSize = 13.sp
                                            )

                                            song.author?.let { author ->
                                                Text(
                                                    text = author,
                                                    color = Color(0xFFB8A48E),
                                                    fontSize = 11.sp
                                                )
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
        }

        Spacer(modifier = Modifier.width(18.dp))

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
                contentDescription = "Delete song",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}