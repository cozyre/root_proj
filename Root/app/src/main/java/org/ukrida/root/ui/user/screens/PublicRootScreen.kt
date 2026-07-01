package org.ukrida.root.ui.user.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.ukrida.root.data.AppContainer
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.user.components.DashboardMenu
import org.ukrida.root.ui.user.components.DashboardTopBar
import org.ukrida.root.ui.user.components.PublicBottomNavigation
import org.ukrida.root.ui.user.components.PublicDestination
import org.ukrida.root.ui.user.components.PublicTopBar
import org.ukrida.root.ui.user.navigation.PublicNavigation
import org.ukrida.root.ui.user.navigation.PublicScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicRootScreen(appContainer: AppContainer, onLogout: () -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBackButton = currentRoute in listOf(
        PublicScreen.Order.route,
        PublicScreen.History.route
    )

    val (menuExpanded, setMenuExpanded) = remember { mutableStateOf(false) }

    val isInGroupContext = currentRoute?.startsWith("group/") == true
    val groupId = if (isInGroupContext) {
        extractGroupIdFromRoute(currentRoute)
    } else {
        null
    }

    LaunchedEffect(currentRoute, isInGroupContext, groupId, menuExpanded) {
        println("DEBUG: currentRoute=$currentRoute")
        println("DEBUG: isInGroupContext=$isInGroupContext")
        println("DEBUG: groupId=$groupId")
        println("DEBUG: menuExpanded=$menuExpanded")
    }

    val currentDestination = when {
        currentRoute == PublicScreen.Group.route -> PublicDestination.GROUP
        currentRoute?.startsWith("group/") == true -> PublicDestination.GROUP
        currentRoute == PublicScreen.Home.route -> PublicDestination.HOME
        currentRoute == PublicScreen.PromisedLand.route -> PublicDestination.PROMISED_LAND
        currentRoute == PublicScreen.Profile.route -> PublicDestination.PROFILE
        else -> PublicDestination.HOME
    }

    val title = when {
        currentRoute?.startsWith("group/") == true -> getTitleForGroupRoute(currentRoute)
        currentRoute == PublicScreen.Home.route -> "Home"
        currentRoute == PublicScreen.PromisedLand.route -> "Promised Land"
        currentRoute == PublicScreen.History.route -> "History"
        currentRoute == PublicScreen.Group.route -> "Group"
        currentRoute == PublicScreen.Profile.route -> "Profile"
        currentRoute == PublicScreen.Order.route -> "Order"
        else -> "Root"
    }

    // Wrap everything in a Box to allow menu to escape Scaffold bounds
    Box {
        Scaffold(
            containerColor = BackgroundDark,
            topBar = {
                if (isInGroupContext && groupId != null) {
                    DashboardTopBar(
                        title = title,
                        expanded = menuExpanded,
                        onExpandClick = { setMenuExpanded(!menuExpanded) }
                    )
                } else {
                    PublicTopBar(
                        title = title,
                        onBackClick = if (showBackButton) {
                            { navController.popBackStack() }
                        } else null
                    )
                }
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
                appContainer = appContainer,
                onLogout
            )
        }

        // Menu overlay & menu—drawn on top of Scaffold
        if (isInGroupContext && groupId != null && menuExpanded) {
            AnimatedVisibility(
                visible = menuExpanded,
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
                            setMenuExpanded(false)
                        }
                )
            }
            DashboardMenu(
                onDashboardClick = {
                    navController.navigate(PublicScreen.Dashboard.createRoute(groupId)) {
                        popUpTo(PublicScreen.Dashboard.route) { inclusive = true }
                        launchSingleTop = true
                    }
                    setMenuExpanded(false)
                },
                onItineraryClick = {
                    navController.navigate(PublicScreen.Itinerary.createRoute(groupId)) {
                        popUpTo(PublicScreen.Itinerary.route) { inclusive = true }
                        launchSingleTop = true
                    }
                    setMenuExpanded(false)
                },
                onHymnClick = {
                    navController.navigate(PublicScreen.Hymn.createRoute(groupId)) {
                        popUpTo(PublicScreen.Hymn.route) { inclusive = true }
                        launchSingleTop = true
                    }
                    setMenuExpanded(false)
                },
                onDailyBreadClick = {
                    navController.navigate(PublicScreen.DailyBread.createRoute(groupId)) {
                        popUpTo(PublicScreen.DailyBread.route) { inclusive = true }
                        launchSingleTop = true
                    }
                    setMenuExpanded(false)
                },
                onJournalClick = {
                    navController.navigate(PublicScreen.Journal.createRoute(groupId)) {
                        popUpTo(PublicScreen.Journal.route) { inclusive = true }
                        launchSingleTop = true
                    }
                    setMenuExpanded(false)
                },
                onGalleryClick = {
                    navController.navigate(PublicScreen.Gallery.createRoute(groupId)) {
                        popUpTo(PublicScreen.Gallery.route) { inclusive = true }
                        launchSingleTop = true
                    }
                    setMenuExpanded(false)
                },
                onMembersClick = {
                    navController.navigate(PublicScreen.Members.createRoute(groupId)) {
                        popUpTo(PublicScreen.Members.route) { inclusive = true }
                        launchSingleTop = true
                    }
                    setMenuExpanded(false)
                }
            )
        }
    }
}

/**
 * Extract groupId from a route like "group/{groupId}/dashboard"
 * Returns null if not in group context
 */
private fun extractGroupIdFromRoute(route: String?): Int? {
    if (route == null) return null
    val parts = route.split("/")
    return try {
        if (parts.size >= 2 && (parts[0] == "group")) {
            return parts[1].toIntOrNull()
        } else {
            return null
        }
    } catch (e: Exception) {
        null
    }
}

/**
 * Get human-readable title for group-scoped routes
 * Routes like "group/5/dashboard" -> "Dashboard"
 */
private fun getTitleForGroupRoute(route: String?): String {
    if (route == null) return "Root"
    return try {
        val parts = route.split("/")
        if (parts.size >= 3) {
            when (parts[2]) {
                "dashboard" -> "Dashboard"
                "hymn" -> "Hymn For Him"
                "itinerary" -> "Itinerary"
                "daily_bread" -> "Daily Bread"
                "journal" -> "Journal"
                "gallery" -> "Gallery"
                "members" -> "Members"
                "hymn_detail" -> "Hymn Lyrics"
                "daily_bread_detail" -> "Daily Bread"
                "journal_editor" -> "Journal Editor"
                "member_detail" -> "Member Detail"
                else -> "Group Menu"
            }
        } else {
            "Group"
        }
    } catch (e: Exception) {
        "Group"
    }
}
