package org.ukrida.root.ui.user.screens.groupmenus.hymn.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.screens.groupmenus.hymn.components.DayHeader
import org.ukrida.root.ui.user.screens.groupmenus.hymn.components.HymnCard
import org.ukrida.root.ui.user.screens.groupmenus.hymn.components.HymnSearchBar
import org.ukrida.root.ui.user.screens.groupmenus.hymn.viewmodel.HymnViewModel
import org.ukrida.root.utils.Resource

@Composable
fun HymnScreen(
    viewModel: HymnViewModel,
    navController: NavHostController,
    groupId: Int
) {
    val uiState by viewModel.uiState.collectAsState()
    var expanded by remember {
        mutableStateOf(false)
    }
    var searchQuery by remember {
        mutableStateOf("")
    }

    LaunchedEffect(groupId) {
        viewModel.loadSongs(groupId)
    }

    Scaffold(
        containerColor = Color(0xFF2A2522),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF2A2522))
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Hymn for Him",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFFE8D8C9),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Search your songs.",
                        color = Color.White.copy(alpha = .8f),
                        textAlign = TextAlign.Center
                    )
                }

                HymnSearchBar(
                    query = searchQuery,
                    onQueryChange = {
                        searchQuery = it
                        if (it.isEmpty()) {
                            viewModel.loadSongs(groupId)
                        } else {
                            viewModel.searchSongs(groupId, it)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                when (val songsRes = uiState.songs) {
                    is Resource.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.White)
                        }
                    }
                    is Resource.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = songsRes.message, color = Color.White)
                        }
                    }
                    is Resource.Success -> {
                        val songs = songsRes.data
                        if (songs.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "No songs found", color = Color.White)
                            }
                        } else {
                            // DayHeader template
                            DayHeader(title = "DAY 0")
                            
                            songs.forEach { song ->
                                HymnCard(
                                    song = song,
                                    onClick = {
                                        navController.navigate(
                                            PublicScreen.HymnDetail.createRoute(groupId, song.id)
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
