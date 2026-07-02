package org.ukrida.root.ui.user.screens.groupmenus.dailybread.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.components.DashboardMenu
import org.ukrida.root.ui.user.components.DashboardTopBar
import org.ukrida.root.ui.user.screens.groupmenus.dailybread.components.DevotionBackButton
import org.ukrida.root.ui.user.screens.groupmenus.dailybread.components.DevotionContent
import org.ukrida.root.ui.user.screens.groupmenus.dailybread.components.DevotionDetailHeader
import org.ukrida.root.ui.user.screens.groupmenus.dailybread.viewmodel.DailyBreadViewModel
import org.ukrida.root.utils.Resource

@Composable
fun DailyBreadDetailScreen(
    viewModel: DailyBreadViewModel,
    navController: NavHostController,
    groupId: Int,
    date: String
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(groupId, date) {
        viewModel.loadDevotionDetail(groupId, date)
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
            when (val resource = uiState.devotionDetail) {
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFFE8D8C9)
                    )
                }
                is Resource.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        DevotionBackButton(
                            onBackClick = {
                                navController.popBackStack()
                            }
                        )
                        DevotionDetailHeader(
                            devotion = resource.data
                        )
                        Spacer(
                            modifier = Modifier.height(24.dp)
                        )
                        DevotionContent(
                            devotion = resource.data
                        )
                    }
                }
                    is Resource.Error -> {
                        Text(
                            text = resource.message ?: "An error occurred",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.Center).padding(16.dp)
                        )
                    }
            }
        }
    }
}