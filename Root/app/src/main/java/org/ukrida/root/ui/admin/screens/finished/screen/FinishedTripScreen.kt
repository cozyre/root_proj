package org.ukrida.root.ui.admin.screens.finished.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.ukrida.root.ui.admin.components.FinishedTripCard
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.navigation.Screen
import org.ukrida.root.ui.admin.screens.finished.viewmodel.FinishedTripViewModel
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun FinishedTripScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: FinishedTripViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {

        TopBar(
            title = "FINISHED TRIP",
            onMenuClick = onMenuClick
        )

        when {

            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = TitleColor)
                }
            }

            uiState.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.errorMessage!!,
                        style = MaterialTheme.typography.bodyMedium,
                        color = BodyColor
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    item {

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "FINISHED TRIP",
                            style = MaterialTheme.typography.titleLarge,
                            color = H1Color
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Text(
                            text = "Revisit the journeys you have completed " +
                                    "and relive your memorable experiences. " +
                                    "View past trip details, schedules, and " +
                                    "highlights from every trip you have joined.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = BodyColor
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    items(uiState.groups) { group ->

                        FinishedTripCard(
                            group = group,
                            onClick = {
                                val route = Screen.FinishedDetail.route
                                    .replace("{tripId}", group.id.toString())

                                navController.navigate(route)
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}