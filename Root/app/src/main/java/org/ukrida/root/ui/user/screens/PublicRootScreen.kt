package org.ukrida.root.ui.user.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.ukrida.root.data.AppContainer
import org.ukrida.root.ui.user.components.PublicBottomNavigation
import org.ukrida.root.ui.user.components.PublicDestination
import org.ukrida.root.ui.user.components.PublicTopBar
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.public.navigation.PublicNavigation
import org.ukrida.root.ui.theme.BackgroundDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicRootScreen(appContainer: AppContainer, onLogout: () -> Unit) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val currentDestination = when (currentRoute) {
        PublicScreen.Group.route -> PublicDestination.GROUP
        PublicScreen.Home.route -> PublicDestination.HOME
        PublicScreen.PromisedLand.route -> PublicDestination.PROMISED_LAND
        PublicScreen.Profile.route -> PublicDestination.PROFILE
        else -> PublicDestination.HOME
    }

    val title = when (currentRoute) {
        PublicScreen.Home.route -> "Home"
        PublicScreen.PromisedLand.route -> "Promised Land"
        PublicScreen.History.route -> "History"
        PublicScreen.Group.route -> "Group"
        PublicScreen.Profile.route -> "Profile"
        PublicScreen.HistoryDetail.route -> "History Detail"
        PublicScreen.Order.route -> "Order"
        else -> "Root"
    }

    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            PublicTopBar(
                title = title
            )
        },
        bottomBar = {
            PublicBottomNavigation(
                currentDestination = currentDestination,
                onNavigate = { destination ->
                    val route = when (destination) {
                        PublicDestination.GROUP -> PublicScreen.Group.route
                        PublicDestination.HOME -> PublicScreen.Home.route
                        PublicDestination.PROMISED_LAND -> PublicScreen.PromisedLand.route
                        PublicDestination.PROFILE -> PublicScreen.Profile.route
                    }
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        PublicNavigation(
            navController = navController,
            modifier = Modifier.padding(padding),
            appContainer = appContainer
        )
    }
}
