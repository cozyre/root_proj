package org.ukrida.root.ui.user.screens.groupmenus.dashboard.screen

import androidx.compose.foundation.background
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.screens.groupmenus.dashboard.components.DashboardHeader
import org.ukrida.root.ui.user.screens.groupmenus.dashboard.components.InformationSection
import org.ukrida.root.ui.user.screens.groupmenus.dashboard.components.MeetupSection
import org.ukrida.root.ui.user.screens.groupmenus.dashboard.components.OrganizerSection
import org.ukrida.root.ui.user.screens.groupmenus.dashboard.viewmodel.DashboardViewModel
import org.ukrida.root.utils.Resource

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    navController: NavHostController,
    groupId: Int
) {
    val uiState by viewModel.uiState.collectAsState()

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
            when (val groupResource = uiState.group) {
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                }
                is Resource.Success -> {
                    val group = groupResource.data
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        DashboardHeader(group)
                        Spacer(modifier = Modifier.height(32.dp))
                        OrganizerSection(group)
                        Spacer(modifier = Modifier.height(32.dp))
                        InformationSection(group)
                        Spacer(modifier = Modifier.height(32.dp))
                        MeetupSection(group)
                    }
                }
                is Resource.Error -> {
                    Text(
                        text = groupResource.message ?: "Unknown Error",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
