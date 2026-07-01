package org.ukrida.root.ui.user.screens.groupmenus.dailybread.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.screens.groupmenus.dailybread.components.DevotionCard
import org.ukrida.root.ui.user.screens.groupmenus.dailybread.viewmodel.DailyBreadViewModel
import org.ukrida.root.utils.Resource

@Composable
fun DailyBreadScreen(
    viewModel: DailyBreadViewModel,
    navController: NavHostController,
    groupId: Int
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(groupId) {
        viewModel.loadDevotions(groupId)
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
                    text = "Daily Bread",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE8D8C9),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Read and reflect on today's devotion.",
                    color = Color.White.copy(alpha = .8f),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            Box(modifier = Modifier.weight(1f)) {
                when (val resource = uiState.devotions) {
                    is Resource.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color(0xFFE8D8C9)
                        )
                    }
                    is Resource.Success -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            itemsIndexed(resource.data) { index, devotion ->
                                Text(
                                    text = "DAY ${String.format("%02d", index + 1)}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE8D8C9)
                                )
                                DevotionCard(
                                    devotion = devotion,
                                    onClick = {
                                        navController.navigate(
                                            PublicScreen.DailyBreadDetail.createRoute(
                                                groupId,
                                                devotion.date
                                            )
                                        )
                                    }
                                )
                            }
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
}