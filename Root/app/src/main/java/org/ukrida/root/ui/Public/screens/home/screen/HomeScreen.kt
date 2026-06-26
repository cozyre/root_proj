package org.ukrida.root.ui.Public.screens.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ukrida.root.ui.Public.components.PublicBottomNavigation
import org.ukrida.root.ui.Public.components.PublicDestination
import org.ukrida.root.ui.Public.components.PublicTopBar
import org.ukrida.root.ui.Public.screens.home.components.BookingBanner
import org.ukrida.root.ui.Public.screens.home.components.HeroSection
import org.ukrida.root.ui.Public.screens.home.components.MissionSection
import org.ukrida.root.ui.Public.screens.home.components.RecommendationSection
import org.ukrida.root.ui.Public.screens.home.components.VisionSection
import org.ukrida.root.ui.Public.screens.home.viewmodel.HomeViewModel

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        containerColor = Color(0xFF2A2522),
        bottomBar = {
            PublicBottomNavigation(
                currentDestination = PublicDestination.HOME,
                onNavigate = { destination ->
                    when (destination) {
                        PublicDestination.HOME -> {
                            // Sudah berada di Home
                        }
                        PublicDestination.GROUP -> {
                            // TODO: Navigate ke Group
                        }
                        PublicDestination.PROMISED_LAND -> {
                            // TODO: Navigate ke Promised Land
                        }
                        PublicDestination.PROFILE -> {
                            // TODO: Navigate ke Profile
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2A2522))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            PublicTopBar(title = "HOME")
            Spacer(modifier = Modifier.height(20.dp))
            HeroSection(
                title = uiState.heroTitle,
                description = uiState.heroDescription
            )
            Spacer(modifier = Modifier.height(50.dp))
            VisionSection(
                vision = uiState.vision
            )
            Spacer(modifier = Modifier.height(60.dp))
            MissionSection(missions = uiState.missions)
            Spacer(modifier = Modifier.height(60.dp))
            RecommendationSection(trips = uiState.recommendationTrips)
            Spacer(modifier = Modifier.height(50.dp))
            BookingBanner(
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}