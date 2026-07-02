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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.components.DashboardMenu
import org.ukrida.root.ui.user.components.DashboardTopBar
import org.ukrida.root.ui.user.screens.groupmenus.hymn.components.HymnBackButton
import org.ukrida.root.ui.user.screens.groupmenus.hymn.components.HymnLyricsSection
import org.ukrida.root.ui.user.screens.groupmenus.hymn.viewmodel.HymnViewModel
import org.ukrida.root.ui.user.screens.groupmenus.hymn.components.HymnDetailHeader
import org.ukrida.root.utils.Resource

@Composable
fun HymnDetailScreen(
    viewModel: HymnViewModel,
    navController: NavHostController,
    songId: Int
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(songId) {
        viewModel.loadSongDetail(songId)
    }

    Scaffold(
        containerColor = Color(0xFF2A2522)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2A2522))
                .padding(padding)
        ) {
            when (val songRes = uiState.song) {
                is Resource.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                is Resource.Error -> {
                    Column {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = songRes.message, color = Color.White)
                        }
                    }
                }
                is Resource.Success -> {
                    val song = songRes.data
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            HymnBackButton(
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                            HymnDetailHeader(
                                song = song
                            )
                            Spacer(
                                modifier = Modifier.height(20.dp)
                            )
                            HymnLyricsSection(
                                song = song
                            )
                        }
                    }
                }
            }
        }
    }
}
