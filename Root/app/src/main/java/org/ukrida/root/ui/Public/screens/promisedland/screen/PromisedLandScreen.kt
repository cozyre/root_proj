package org.ukrida.root.ui.Public.screens.promisedland.screen

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
import org.ukrida.root.ui.Public.components.PublicBottomNavigation
import org.ukrida.root.ui.Public.components.PublicDestination
import org.ukrida.root.ui.Public.components.PublicTopBar
import org.ukrida.root.ui.Public.navigation.PublicScreen
import org.ukrida.root.ui.Public.screens.promisedland.components.AllTripSection
import org.ukrida.root.ui.Public.screens.promisedland.components.HistorySection
import org.ukrida.root.ui.Public.screens.promisedland.viewmodel.PromisedLandViewModel

@Composable
fun PromisedLandScreen(
    navController: NavHostController
) {
    val viewModel: PromisedLandViewModel = viewModel()
    val historyGroups by viewModel.historyGroups.collectAsState()
    val allTrips by viewModel.allTrips.collectAsState()
    Scaffold(
        containerColor = Color(0xFF2A2522),
        bottomBar = {
            PublicBottomNavigation(
                currentDestination = PublicDestination.PROMISED_LAND,
                onNavigate = { destination ->
                    when (destination) {

                        PublicDestination.HOME -> {
                            navController.navigate(PublicScreen.Home.route)
                        }

                        PublicDestination.PROMISED_LAND -> {
                            // Sudah berada di Promised Land
                        }

                        PublicDestination.GROUP -> {
                            navController.navigate(PublicScreen.Group.route)
                        }

                        PublicDestination.PROFILE -> {
                            navController.navigate(PublicScreen.Profile.route)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFF2A2522))
                .verticalScroll(rememberScrollState())
        ) {
            PublicTopBar(title = "PROMISED LAND")
            Spacer(modifier = Modifier.height(12.dp))
            HistorySection(
                historyGroups = historyGroups,
                onSeeMoreClick = {
                    navController.navigate(
                        PublicScreen.History.route
                    )
                },
                onHistoryClick = { groupId ->
                    navController.navigate(
                        PublicScreen.HistoryDetail.createRoute(groupId)
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            AllTripSection(
                allTrips = allTrips,
                onTripClick = { groupId ->
                    navController.navigate(
                        PublicScreen.Order.createRoute(groupId)
                    )
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}