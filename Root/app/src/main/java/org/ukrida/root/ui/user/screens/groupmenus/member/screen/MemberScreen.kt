package org.ukrida.root.ui.user.screens.members.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.components.PublicBottomNavigation
import org.ukrida.root.ui.user.components.PublicDestination
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.components.DashboardMenu
import org.ukrida.root.ui.user.components.DashboardTopBar
import org.ukrida.root.ui.user.screens.members.components.MemberCard
import org.ukrida.root.ui.user.screens.members.viewmodel.MemberViewModel

@Composable
fun MemberScreen(
    viewModel: MemberViewModel = viewModel(),
    navController: NavHostController,
    groupId: Int
) {
    val members by viewModel.members.collectAsState()
    var expanded by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(groupId) {
        viewModel.loadMembers(groupId)
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
        ) {
            DashboardTopBar(
                title = "MEMBERS",
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
                    text = "Meet People!",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE8D8C9),
                    textAlign = TextAlign.Center
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                Text(
                    text = "Meet and connect with everyone in your group.",
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White.copy(alpha = .8f),
                    textAlign = TextAlign.Center
                )
            }
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(
                    horizontal = 30.dp,
                    vertical = 24.dp
                ),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(
                    items = members,
                    key = { it.id }
                ) { member ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        MemberCard(
                            member = member,
                            onClick = {
                                navController.navigate(
                                    PublicScreen.MemberDetail.createRoute(
                                        groupId,
                                        member.id
                                    )
                                )
                            }
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
                }
            )
        }
    }
}