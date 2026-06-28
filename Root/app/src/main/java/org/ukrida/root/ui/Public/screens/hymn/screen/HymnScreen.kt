package org.ukrida.root.ui.Public.screens.hymn.screen

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.ui.Public.components.PublicBottomNavigation
import org.ukrida.root.ui.Public.components.PublicDestination
import org.ukrida.root.ui.Public.navigation.PublicScreen
import org.ukrida.root.ui.Public.screens.dashboard.components.DashboardMenu
import org.ukrida.root.ui.Public.screens.dashboard.components.DashboardTopBar
import org.ukrida.root.ui.Public.screens.hymn.components.DayHeader
import org.ukrida.root.ui.Public.screens.hymn.components.HymnCard
import org.ukrida.root.ui.Public.screens.hymn.components.HymnSearchBar
import org.ukrida.root.ui.Public.screens.hymn.viewmodel.HymnViewModel

@Composable
fun HymnScreen(
    navController: NavHostController,
    groupId: Int
) {
    val viewModel: HymnViewModel = viewModel()
    val songs by viewModel.songs.collectAsState()
    var expanded by remember {
        mutableStateOf(false)
    }
    var searchQuery by remember {
        mutableStateOf("")
    }
    LaunchedEffect(groupId) {
        viewModel.loadSongs(groupId)
    }
    Scaffold(
        containerColor = Color(0xFF2A2522),
        bottomBar = {
            PublicBottomNavigation(
                currentDestination = PublicDestination.GROUP,
                onNavigate = { destination ->
                    when(destination){
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            DashboardTopBar(
                title = "HYMN FOR HIM",
                expanded = expanded,
                onExpandClick = {
                    expanded = !expanded
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            androidx.compose.material3.Text(
                text = "Hymn for Him",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFFE8D8C9)
            )
            Spacer(modifier = Modifier.height(8.dp))
            androidx.compose.material3.Text(
                text = "Search your songs.",
                color = Color.White.copy(alpha = .8f)
            )
            HymnSearchBar(
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                    // TODO Backend Integration
                    // Search songs from API
                }
            )
            // TODO Backend Integration
            // Display songs grouped by itinerary/day returned from API.
            // Current implementation uses dummy "DAY 01".
            Spacer(modifier = Modifier.height(24.dp))
            DayHeader(
                title = "DAY 01"
            )
            songs.forEach { song ->
                HymnCard(
                    song = song,
//                    onClick = {
//                        navController.navigate(
//                            PublicScreen.HymnDetail.createRoute(song.id)
//                        )
//                    }
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
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        expanded = false
                    }
            )
        }
        if (expanded) {
            DashboardMenu(
                onDashboardClick = {
                    expanded = false
                },
                onItineraryClick = {
                    expanded = false
                },
                onHymnClick = {
                    expanded = false
                },
                onDailyBreadClick = {
                    expanded = false
                },
                onJournalClick = {
                    expanded = false
                },
                onGalleryClick = {
                    expanded = false
                },
                onMembersClick = {
                    expanded = false
                }
            )
        }
    }
}