package org.ukrida.root.ui.Public.screens.dashboard.screen

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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.ui.Public.components.PublicBottomNavigation
import org.ukrida.root.ui.Public.components.PublicDestination
import org.ukrida.root.ui.Public.navigation.PublicScreen
import org.ukrida.root.ui.Public.screens.dashboard.components.DashboardHeader
import org.ukrida.root.ui.Public.screens.dashboard.components.DashboardMenu
import org.ukrida.root.ui.Public.screens.dashboard.components.DashboardTopBar
import org.ukrida.root.ui.Public.screens.dashboard.components.InformationSection
import org.ukrida.root.ui.Public.screens.dashboard.components.MeetupSection
import org.ukrida.root.ui.Public.screens.dashboard.components.OrganizerSection
import org.ukrida.root.ui.Public.screens.dashboard.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    navController: NavHostController,
    groupId: Int
) {
    val viewModel: DashboardViewModel = viewModel()
    val group by viewModel.group.collectAsState()
    LaunchedEffect(groupId) {
        viewModel.loadDashboard(groupId)
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    Scaffold(
        containerColor = Color(0xFF2A2522),
        bottomBar = {
            PublicBottomNavigation(
                currentDestination = PublicDestination.GROUP,
                onNavigate = { destination ->
                    when(destination){
                        PublicDestination.HOME ->
                            navController.navigate(PublicScreen.Home.route)
                        PublicDestination.PROMISED_LAND ->
                            navController.navigate(PublicScreen.PromisedLand.route)
                        PublicDestination.GROUP ->
                            navController.popBackStack()
                        PublicDestination.PROFILE ->
                            navController.navigate(PublicScreen.Profile.route)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF2A2522))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                DashboardTopBar(
                    title = "DASHBOARD",
                    expanded = expanded,
                    onExpandClick = {
                        expanded = !expanded
                    }
                )
                group?.let { group ->
                    DashboardHeader(group)
                    Spacer(modifier = Modifier.height(32.dp))
                    OrganizerSection(group)
                    Spacer(modifier = Modifier.height(32.dp))
                    InformationSection(group)
                    Spacer(modifier = Modifier.height(32.dp))
                    MeetupSection(group)
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