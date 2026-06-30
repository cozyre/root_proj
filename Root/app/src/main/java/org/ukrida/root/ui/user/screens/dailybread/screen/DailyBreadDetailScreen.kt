package org.ukrida.root.ui.user.screens.dailybread.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.screens.dashboard.components.DashboardMenu
import org.ukrida.root.ui.user.screens.dashboard.components.DashboardTopBar
import org.ukrida.root.ui.user.screens.dailybread.components.DevotionBackButton
import org.ukrida.root.ui.user.screens.dailybread.components.DevotionContent
import org.ukrida.root.ui.user.screens.dailybread.components.DevotionDetailHeader
import org.ukrida.root.ui.user.screens.dailybread.viewmodel.DailyBreadDetailViewModel

@Composable
fun DailyBreadDetailScreen(
    navController: NavHostController,
    groupId: Int,
    date: String
) {
    val viewModel: DailyBreadDetailViewModel = viewModel()
    val devotion by viewModel.devotion.collectAsState()

    var expanded by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(date) {
        viewModel.loadDevotion(groupId, date)
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
            devotion?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    DashboardTopBar(
                        title = "DAILY BREAD",
                        expanded = expanded,
                        onExpandClick = {
                            expanded = !expanded
                        }
                    )
                    DevotionBackButton(
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                    DevotionDetailHeader(
                        devotion = it
                    )
                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )
                    DevotionContent(
                        devotion = it
                    )
                }
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
                            .background(Color.Black.copy(alpha = .4f))
                            .clickable(
                                indication = null,
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }
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
                        navController.navigate(
                            PublicScreen.Itinerary.createRoute(groupId)
                        )
                    },
                    onHymnClick = {
                        expanded = false
                        navController.navigate(
                            PublicScreen.Hymn.createRoute(groupId)
                        )
                    },
                    onDailyBreadClick = {
                        expanded = false
                        navController.navigate(
                            PublicScreen.DailyBread.createRoute(groupId)
                        )
                    },
                    onJournalClick = {
                        expanded = false
                        navController.navigate(
                            PublicScreen.Journal.createRoute(groupId)
                        )
                    },
                    onGalleryClick = {
                        expanded = false
                        navController.navigate(
                            PublicScreen.Gallery.createRoute(groupId)
                        )
                    },
                    onMembersClick = {
                        expanded = false
                        navController.navigate(
                            PublicScreen.Members.createRoute(groupId)
                        )
                    }
                )
            }
        }
    }
}