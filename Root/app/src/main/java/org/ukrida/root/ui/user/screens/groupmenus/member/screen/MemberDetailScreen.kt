package org.ukrida.root.ui.user.screens.groupmenus.member.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.screens.groupmenus.member.components.MemberBackButton
import org.ukrida.root.ui.user.screens.groupmenus.member.components.MemberDetailHeader
import org.ukrida.root.ui.user.screens.groupmenus.member.components.MemberInfoItem
import org.ukrida.root.ui.user.screens.groupmenus.member.viewmodel.MemberViewModel
import org.ukrida.root.utils.Resource

@Composable
fun MemberDetailScreen(
    viewModel: MemberViewModel,
    navController: NavHostController,
    groupId: Int,
    userId: Int
) {
    val memberState by viewModel.member.collectAsState()

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
            when (val state = memberState) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFFE8D8C9))
                    }
                }
                is Resource.Success -> {
                    val member = state.data
                    if (member != null) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            MemberBackButton(
                                onBackClick = {
                                    navController.popBackStack()
                                }
                            )
                            MemberDetailHeader(
                                member = member
                            )
                            Spacer(
                                modifier = Modifier.height(32.dp)
                            )
                            Column(
                                modifier = Modifier.padding(horizontal = 20.dp)
                            ) {
                                MemberInfoItem(
                                    label = "EMAIL",
                                    value = member.email ?: "-"
                                )
                                MemberInfoItem(
                                    label = "PHONE",
                                    value = member.phone ?: "-"
                                )
                                MemberInfoItem(
                                    label = "ROLE",
                                    value = member.role
                                )
                                MemberInfoItem(
                                    label = "STATUS",
                                    value = member.statusJoin
                                )
                                MemberInfoItem(
                                    label = "JOIN DATE",
                                    value = member.joinDate
                                )
                                MemberInfoItem(
                                    label = "BIO",
                                    value = member.bio ?: "-"
                                )
                                Spacer(
                                    modifier = Modifier.height(32.dp)
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Member not found",
                                color = Color.White
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
