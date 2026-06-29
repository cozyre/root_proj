package org.ukrida.root.ui.Public.screens.hymn.screen

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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.ui.Public.navigation.PublicScreen
import org.ukrida.root.ui.Public.screens.dashboard.components.DashboardMenu
import org.ukrida.root.ui.Public.screens.dashboard.components.DashboardTopBar
import org.ukrida.root.ui.Public.screens.hymn.components.HymnBackButton
import org.ukrida.root.ui.Public.screens.hymn.components.HymnLyricsSection
import org.ukrida.root.ui.Public.screens.hymn.viewmodel.HymnDetailViewModel
import org.ukrida.root.ui.Public.screens.hymn.components.HymnDetailHeader

@Composable
fun HymnDetailScreen(
    navController: NavHostController,
    groupId: Int,
    songId: Int
) {
    val viewModel: HymnDetailViewModel = viewModel()
    val song by viewModel.song.collectAsState()
    var expanded by remember {
        mutableStateOf(false)
    }
    // TODO Backend Integration
    // Load song detail from repository using songId.
    LaunchedEffect(songId) {
        viewModel.loadSong(songId)
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
            song?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp)
                ) {
                    DashboardTopBar(
                        title = "HYMN FOR HIM",
                        expanded = expanded,
                        onExpandClick = {
                            expanded = !expanded
                        }
                    )
                    HymnBackButton(
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                    HymnDetailHeader(
                        song = it
                    )
                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                    HymnLyricsSection(
                        song = it
                    )
                }
                if (expanded) {
                    AnimatedVisibility(
                        visible = expanded,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.4f))
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    expanded = false
                                }
                        )
                    }
                    DashboardMenu(
                        onDashboardClick = {
                            expanded = false
                            navController.navigate(
                                PublicScreen.Dashboard.createRoute(groupId)
                            )
                        },
                        onItineraryClick = {
                            expanded = false
                        },
                        onHymnClick = {
                            expanded = false
                            navController.navigate(
                                PublicScreen.Hymn.createRoute(groupId)
                            )
                        },
                        onDailyBreadClick = {
                            expanded = false
                        },
                        onJournalClick = {
                            expanded = false
                        },
                        onGalleryClick = {
                            expanded = false
                            navController.navigate(
                                PublicScreen.Gallery.createRoute(groupId)
                            )
                        },
                        onMembersClick = {
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}