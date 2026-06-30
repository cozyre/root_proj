package org.ukrida.root.ui.user.screens.history.screen

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
import org.ukrida.root.ui.user.components.PublicBottomNavigation
import org.ukrida.root.ui.user.components.PublicDestination
import org.ukrida.root.ui.user.components.PublicTopBar
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.screens.history.components.HistorySection
import org.ukrida.root.ui.user.screens.history.viewmodel.HistoryViewModel

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel(),
    navController: NavHostController
) {
    val historyGroups by viewModel.historyGroups.collectAsState()
    Scaffold(
        containerColor = Color(0xFF2A2522),
        bottomBar = {
            PublicBottomNavigation(
                currentDestination = PublicDestination.PROMISED_LAND,
                onNavigate = { destination ->
                    when(destination){
                        PublicDestination.HOME ->
                            navController.navigate(PublicScreen.Home.route)
                        PublicDestination.PROMISED_LAND ->
                            navController.popBackStack()
                        PublicDestination.GROUP ->
                            navController.navigate(PublicScreen.Group.route)
                        PublicDestination.PROFILE ->
                            navController.navigate(PublicScreen.Profile.route)
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
            PublicTopBar(
                title = "HISTORY"
            )
            Spacer(modifier = Modifier.height(20.dp))
            HistorySection(
                historyGroups = historyGroups,
                onHistoryClick = { groupId ->
                    navController.navigate(
                        PublicScreen.HistoryDetail.createRoute(groupId)
                    )
                }
            )

        }

    }

}