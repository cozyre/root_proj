package org.ukrida.root.ui.user.screens.groupmenus.member.screen

import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
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
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.screens.groupmenus.member.components.MemberCard
import org.ukrida.root.ui.user.screens.groupmenus.member.viewmodel.MemberViewModel
import org.ukrida.root.utils.Resource


@Composable
fun MemberScreen(
    viewModel: MemberViewModel = viewModel(),
    navController: NavHostController,
    groupId: Int
) {
    val membersState by viewModel.members.collectAsState()

    LaunchedEffect(groupId) {
        viewModel.loadMembers(groupId)
    }
    Scaffold(
        containerColor = Color(0xFF2A2522),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF2A2522))
        ) {
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

            when (val state = membersState) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFFE8D8C9))
                    }
                }
                is Resource.Success -> {
                    val members = state.data
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
