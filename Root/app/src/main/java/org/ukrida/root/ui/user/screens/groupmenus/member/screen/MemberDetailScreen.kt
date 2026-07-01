package org.ukrida.root.ui.user.screens.members.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.components.DashboardMenu
import org.ukrida.root.ui.user.components.DashboardTopBar
import org.ukrida.root.ui.user.screens.members.components.MemberBackButton
import org.ukrida.root.ui.user.screens.members.components.MemberDetailHeader
import org.ukrida.root.ui.user.screens.members.components.MemberInfoItem
import org.ukrida.root.ui.user.screens.members.viewmodel.MemberDetailViewModel

@Composable
fun MemberDetailScreen(
    navController: NavHostController,
    groupId: Int,
    userId: Int
) {
    val viewModel: org.ukrida.root.ui.user.screens.members.viewmodel.MemberDetailViewModel = viewModel()
    val member by viewModel.member.collectAsState()
    var expanded by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(userId) {
        viewModel.loadMember(
            userId = userId,
            groupId = groupId
        )
    }
    Scaffold(
        containerColor = Color(0xFF2A2522)
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2A2522))
                .padding(padding)
        ) {
            member?.let { member ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    DashboardTopBar(
                        title = "MEMBER",
                        expanded = expanded,
                        onExpandClick = {
                            expanded = !expanded
                        }
                    )
                    _root_ide_package_.org.ukrida.root.ui.user.screens.members.components.MemberBackButton(
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                    _root_ide_package_.org.ukrida.root.ui.user.screens.members.components.MemberDetailHeader(
                        member = member
                    )
                    Spacer(
                        modifier = Modifier.height(32.dp)
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp)
                    ) {
                        _root_ide_package_.org.ukrida.root.ui.user.screens.members.components.MemberInfoItem(
                            label = "EMAIL",
                            value = member.email ?: "-"
                        )
                        _root_ide_package_.org.ukrida.root.ui.user.screens.members.components.MemberInfoItem(
                            label = "PHONE",
                            value = member.phone ?: "-"
                        )
                        _root_ide_package_.org.ukrida.root.ui.user.screens.members.components.MemberInfoItem(
                            label = "ROLE",
                            value = member.role
                        )
                        _root_ide_package_.org.ukrida.root.ui.user.screens.members.components.MemberInfoItem(
                            label = "STATUS",
                            value = member.statusJoin
                        )
                        _root_ide_package_.org.ukrida.root.ui.user.screens.members.components.MemberInfoItem(
                            label = "JOIN DATE",
                            value = member.joinDate
                        )
                        _root_ide_package_.org.ukrida.root.ui.user.screens.members.components.MemberInfoItem(
                            label = "BIO",
                            value = "No bio available."
                        )
                        // TODO Backend Integration
                        // Replace BIO with member.bio when backend provides it.
                        Spacer(
                            modifier = Modifier.height(32.dp)
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
}