package org.ukrida.root.ui.admin.screens.trip.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.ukrida.root.ui.admin.navigation.Screen
import org.ukrida.root.ui.admin.screens.finished.screen.FinishedTripScreen
import org.ukrida.root.ui.admin.screens.trip.components.TripCard
import org.ukrida.root.ui.admin.screens.trip.components.TripHeaderSection
import org.ukrida.root.ui.admin.screens.trip.viewmodel.TripViewModel
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun TripScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: TripViewModel = viewModel()
) {

    val groups by viewModel.groups.collectAsState()

    val ongoingTrips = groups.filter {
        it.status.equals("ONGOING", ignoreCase = true)
    }

    val finishedTrips = groups.filter {
        it.status.equals("FINISHED", ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {

        TripTopBar(
            title = "TRIP",
            onMenuClick = onMenuClick
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                TripHeaderSection(
                    onNewTripClick = {
                        navController.navigate(Screen.CreateTrip.route)
                    }
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }

            item {
                Text(
                    text = "TRIP LIST (AVAILABLE TRIP)",
                    style = MaterialTheme.typography.titleLarge,
                    color = H1Color
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }

            items(ongoingTrips) { group ->

                TripCard(
                    group = group,
                    onClick = {

                        val route = Screen.OngoingDetail.route
                            .replace("{tripId}", group.id.toString())

                        navController.navigate(route)
                    }
                )
            }

            item {

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(
                        onClick = {
                            navController.navigate(Screen.OngoingTrip.route)
                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Text(
                            text = "See More",
                            color = BodyColor
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }

            // FINISHED

            item {

                Text(
                    text = "FINISHED TRIP",
                    style = MaterialTheme.typography.titleLarge,
                    color = TitleColor
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }

            items(finishedTrips) { group ->

                TripCard(
                    group = group,
                    onClick = {

                        val route = Screen.FinishedDetail.route
                            .replace("{tripId}", group.id.toString())

                        navController.navigate(route)
                    }
                )
            }

            item {

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(
                        onClick = {
                            navController.navigate(Screen.FinishedTrip.route)
                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Text(
                            text = "See More",
                            color = BodyColor
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(32.dp)
                )
            }
        }
    }
}