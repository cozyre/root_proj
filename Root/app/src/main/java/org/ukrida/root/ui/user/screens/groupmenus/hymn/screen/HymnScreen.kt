package org.ukrida.root.ui.user.screens.groupmenus.hymn.screen

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.components.PublicBottomNavigation
import org.ukrida.root.ui.user.components.PublicDestination
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.components.DashboardMenu
import org.ukrida.root.ui.user.components.DashboardTopBar
import org.ukrida.root.ui.user.screens.groupmenus.hymn.components.DayHeader
import org.ukrida.root.ui.user.screens.groupmenus.hymn.components.HymnCard
import org.ukrida.root.ui.user.screens.groupmenus.hymn.components.HymnSearchBar
import org.ukrida.root.ui.user.screens.groupmenus.hymn.viewmodel.HymnViewModel

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
        ) {
            DashboardTopBar(
                title = "Hymn For Him",
                expanded = expanded,
                onExpandClick = {
                    expanded = !expanded
                }
            )
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Hymn for Him",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFFE8D8C9),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Search your songs.",
                        color = Color.White.copy(alpha = .8f),
                        textAlign = TextAlign.Center
                    )
                }
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
                        onClick = {
                            navController.navigate(
                                PublicScreen.HymnDetail.createRoute(groupId, song.id)
                            )
                        }
                    )
                }
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
                    navController.navigate(
                        PublicScreen.Dashboard.createRoute(groupId)
                    )
                },
                onItineraryClick = {
                    expanded = false
                    navController.navigate(
                        PublicScreen.Itinerary.createRoute(groupId)
                    )
                },
                onHymnClick = {
                    expanded = false
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