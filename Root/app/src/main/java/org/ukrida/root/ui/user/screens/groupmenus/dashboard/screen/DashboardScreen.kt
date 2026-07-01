package org.ukrida.root.ui.user.screens.groupmenus.dashboard.screen

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
import org.ukrida.root.ui.user.components.PublicBottomNavigation
import org.ukrida.root.ui.user.components.PublicDestination
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.screens.groupmenus.dashboard.components.DashboardHeader
import org.ukrida.root.ui.user.components.DashboardMenu
import org.ukrida.root.ui.user.components.DashboardTopBar
import org.ukrida.root.ui.user.screens.groupmenus.dashboard.components.InformationSection
import org.ukrida.root.ui.user.screens.groupmenus.dashboard.components.MeetupSection
import org.ukrida.root.ui.user.screens.groupmenus.dashboard.components.OrganizerSection
import org.ukrida.root.ui.user.screens.groupmenus.dashboard.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    navController: NavHostController,
    groupId: Int
) {
    val group by viewModel.group.collectAsState()
    val gallery by viewModel.gallery.collectAsState()
    LaunchedEffect(groupId) {
        viewModel.loadDashboard(groupId)
    }

    Scaffold(
        containerColor = Color(0xFF2A2522),
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
        }
    }
}