package org.ukrida.root.ui.admin.screens.trip.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import org.ukrida.root.ui.admin.components.FinishedTripCard
import org.ukrida.root.ui.admin.components.PriceTripCard
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.navigation.Screen
import org.ukrida.root.ui.admin.screens.trip.viewmodel.TripViewModel
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MainButton
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun TripScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    onNewTripClick: () -> Unit,
    viewModel: TripViewModel = viewModel()
) {

    val groups by viewModel.groups.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val ongoingTrips = groups.filter {
        it.status.equals("upcoming", ignoreCase = true) ||
                it.status.equals("active", ignoreCase = true)
    }.take(2)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {

            Column {

                TopBar(
                    title = "TRIP",
                    onMenuClick = onMenuClick
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "MANAGE TRIP HERE",
                        style = MaterialTheme.typography.headlineSmall,
                        color = H1Color
                    )

                    Text(
                        text = "Create, manage, and monitor all tour activities.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = BodyColor,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Button(
                        onClick = onNewTripClick,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .height(40.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MainButton
                        )
                    ) {

                        Text(
                            text = "NEW TRIP",
                            color = H1Color
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }
        }

        // ONGOING TRIP

        item {

            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {

                Text(
                    text = "AVAILABLE TRIP",
                    style = MaterialTheme.typography.titleLarge,
                    color = H1Color
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
                Text(
                    text = "View trips that are currently in progress " +
                            "and still open for registration. Check " +
                            "the travel schedule, explore trip details, " +
                            "and secure your spot before availability runs out.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = BodyColor
                )
            }
        }

        items(
            ongoingTrips.take(3)
        ) { group ->

            Box(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {

                PriceTripCard(
                    group = group,
                    onClick = {

                        val route = Screen.OngoingDetail.route
                            .replace(
                                "{tripId}",
                                group.id.toString()
                            )

                        navController.navigate(route)
                    }
                )
            }
        }

        item {

            if (ongoingTrips.size > 3) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {

                    TextButton(
                        onClick = {
                            navController.navigate(
                                Screen.OngoingTrip.route
                            )
                        },
                        modifier = Modifier.align(
                            Alignment.CenterEnd
                        )
                    ) {

                        Text(
                            text = "See More",
                            color = BodyColor
                        )
                    }
                }
            }
        }

        // FINISHED TRIP

        item {

            Column(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

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
            }
        }

        items(
            uiState.finishedTrips.take(3)
        ) { trip ->

            Box(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {

                FinishedTripCard(
                    trip = trip,
                    onClick = {

                        val route =
                            Screen.FinishedDetail.route
                                .replace(
                                    "{tripId}",
                                    trip.id.toString()
                                )

                        navController.navigate(route)
                    }
                )
            }
        }

        item {

            if (uiState.finishedTrips.size > 3) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {

                    TextButton(
                        onClick = {
                            navController.navigate(
                                Screen.FinishedTrip.route
                            )
                        },
                        modifier = Modifier.align(
                            Alignment.CenterEnd
                        )
                    ) {

                        Text(
                            text = "See More",
                            color = BodyColor
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(32.dp)
            )
        }
    }
}