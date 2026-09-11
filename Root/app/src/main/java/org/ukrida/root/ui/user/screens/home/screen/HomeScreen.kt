package org.ukrida.root.ui.user.screens.home.screen

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
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.screens.home.components.BookingBanner
import org.ukrida.root.ui.user.screens.home.components.HeroSection
import org.ukrida.root.ui.user.screens.home.components.MissionSection
import org.ukrida.root.ui.user.screens.home.components.RecommendationSection
import org.ukrida.root.ui.user.screens.home.components.VisionSection
import org.ukrida.root.ui.user.screens.home.viewmodel.HomeViewModel
import org.ukrida.root.utils.Resource

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navController: NavHostController
) {
    val groupState by viewModel.groups.collectAsState()
    val coverImages by viewModel.coverImages.collectAsState()
    val groups = (groupState as? Resource.Success)?.data

    Scaffold(
        containerColor = Color(0xFF2A2522),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2A2522))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            HeroSection()
            Spacer(modifier = Modifier.height(50.dp))
            VisionSection()
            Spacer(modifier = Modifier.height(60.dp))
            MissionSection()
            Spacer(modifier = Modifier.height(60.dp))
            RecommendationSection(
                groups = groups?.take(4),
                coverImages = coverImages,
                onTripClick = { groupId ->
                    navController.navigate(
                        PublicScreen.Order.createRoute(groupId)
                    )
                }
            )
            Spacer(modifier = Modifier.height(50.dp))
            BookingBanner(
                modifier = Modifier.padding(horizontal = 20.dp),
                onSeeMore = {
                    navController.navigate(PublicScreen.Group.route)
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}