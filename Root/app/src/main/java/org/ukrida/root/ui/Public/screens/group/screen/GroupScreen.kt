package org.ukrida.root.ui.Public.screens.group.screen

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
import org.ukrida.root.ui.Public.screens.group.components.GroupSection
import org.ukrida.root.ui.Public.screens.group.viewmodel.GroupViewModel

@Composable
fun GroupScreen(
    navController: NavHostController
) {
    val viewModel: GroupViewModel = viewModel()
    val groups by viewModel.groups.collectAsState()
    Scaffold(
        containerColor = Color(0xFF2A2522),
        bottomBar = {
            PublicBottomNavigation(
                currentDestination = PublicDestination.GROUP,
                onNavigate = { destination ->
                    when (destination) {
                        PublicDestination.HOME ->
                            navController.navigate(PublicScreen.Home.route)
                        PublicDestination.PROMISED_LAND ->
                            navController.navigate(PublicScreen.PromisedLand.route)
                        PublicDestination.GROUP -> {}
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
                .padding(paddingValues)
                .background(Color(0xFF2A2522))
                .verticalScroll(rememberScrollState())
        ) {
            PublicTopBar(
                title = "GROUP"
            )
            Spacer(modifier = Modifier.height(20.dp))
            GroupSection(
                groups = groups,
                onGroupClick = { groupId ->
                    navController.navigate(
                        PublicScreen.Dashboard.createRoute(groupId)
                    )
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}