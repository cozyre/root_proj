package org.ukrida.root.ui.user.screens.groupmenus.itinerary.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import org.ukrida.root.ui.user.components.DashboardMenu
import org.ukrida.root.ui.user.components.DashboardTopBar
import org.ukrida.root.ui.user.screens.groupmenus.itinerary.components.ActivityRow
import org.ukrida.root.ui.user.screens.groupmenus.itinerary.components.ActivityTableHeader
import org.ukrida.root.ui.user.screens.groupmenus.itinerary.components.DateNavigator
import org.ukrida.root.ui.user.screens.groupmenus.itinerary.components.ItineraryHeader
import org.ukrida.root.ui.user.screens.groupmenus.itinerary.viewmodel.ItineraryViewModel

@Composable
fun ItineraryScreen(
    navController: NavHostController,
    groupId: Int
) {
    val viewModel: ItineraryViewModel = viewModel()

    val itinerary by viewModel.itinerary.collectAsState()
    val dates by viewModel.dates.collectAsState()
    val selectedIndex by viewModel.selectedIndex.collectAsState()

    var expanded by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(groupId) {
        viewModel.load(groupId)
    }
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
                        PublicDestination.GROUP ->
                            navController.popBackStack()
                        PublicDestination.PROFILE ->
                            navController.navigate(PublicScreen.Profile.route)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF2A2522))
        ) {
            DashboardTopBar(
                title = "ITINERARY",
                expanded = expanded,
                onExpandClick = {
                    expanded = !expanded
                }
            )
            itinerary?.let { data ->
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    ItineraryHeader(
                        date = data.date,
                        description = data.desc
                    )
                    ActivityTableHeader()
                }
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    items(data.items) { item ->
                        ActivityRow(item)
                    }
                }
                DateNavigator(
                    date = dates[selectedIndex].date,
                    canPrevious = selectedIndex > 0,
                    canNext = selectedIndex < dates.lastIndex,
                    onPrevious = {
                        viewModel.previousDay(groupId)
                    },
                    onNext = {
                        viewModel.nextDay(groupId)
                    }
                )
            }
        }
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = .4f))
                    .clickable(
                        indication = null,
                        interactionSource = remember {
                            MutableInteractionSource()
                        }
                    ) {
                        expanded = false
                    }
            )
        }
        if (expanded) {
            DashboardMenu(
                onDashboardClick = {
                    expanded = false
                    navController.navigate(
                        PublicScreen.Dashboard.createRoute(groupId)
                    )
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
                    navController.navigate(
                        PublicScreen.DailyBread.createRoute(groupId)
                    )
                },
                onJournalClick = {
                    expanded = false
                    navController.navigate(
                        PublicScreen.Journal.createRoute(groupId)
                    )
                },
                onGalleryClick = {
                    expanded = false
                    navController.navigate(
                        PublicScreen.Gallery.createRoute(groupId)
                    )
                },
                onMembersClick = {
                    expanded = false
                    navController.navigate(
                        PublicScreen.Members.createRoute(groupId)
                    )
                }
            )
        }
    }
}