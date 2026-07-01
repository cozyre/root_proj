package org.ukrida.root.ui.user.screens.groupmenus.journal.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import org.ukrida.root.ui.user.screens.groupmenus.journal.components.CreateJournalButton
import org.ukrida.root.ui.user.screens.groupmenus.journal.components.JournalCard
import org.ukrida.root.ui.user.screens.groupmenus.journal.viewmodel.JournalViewModel

// TODO Backend Integration
// Flow:
//
// 1. Load journals using listJournals(groupId).
// 2. If list is empty, show empty state.
// 3. Create button opens JournalEditorScreen in Create Mode.
// 4. Clicking a card opens JournalEditorScreen in Edit Mode.
// 5. After Save, reload journal list.
@Composable
fun JournalScreen(
    viewModel: JournalViewModel = viewModel(),
    navController: NavHostController,
    groupId: Int
) {
    val viewModel: JournalViewModel = viewModel()
    val journals by viewModel.journals.collectAsState()

    var expanded by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(groupId) {
        viewModel.loadJournals(groupId)
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2A2522))
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                DashboardTopBar(
                    title = "JOURNAL",
                    expanded = expanded,
                    onExpandClick = {
                        expanded = !expanded
                    }
                )
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Journal",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFFE8D8C9)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Write your spiritual journey during the pilgrimage.",
                        color = Color.White.copy(alpha = .8f)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CreateJournalButton(
                            onClick = {
                                navController.navigate(
                                    PublicScreen.JournalEditor.createRoute(groupId)
                                )
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    if (journals.isEmpty()) {
                        Spacer(modifier = Modifier.height(48.dp))
                        Text(
                            text = "No journal yet.",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Create your first journal to record your pilgrimage.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.5f)
                        )
                    } else {
                        journals.forEach { journal ->
                            JournalCard(
                                journal = journal,
                                onClick = {
                                    navController.navigate(
                                        PublicScreen.JournalEditor.createRoute(
                                            groupId,
                                            journal.id
                                        )
                                    )
                                }
                            )
                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )
                        }
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
                        navController.navigate(
                            PublicScreen.Itinerary.createRoute(groupId)
                        )
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
}