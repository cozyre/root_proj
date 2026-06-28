package org.ukrida.root.ui.admin.screens.ongoing.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.ukrida.root.ui.admin.navigation.Screen
import org.ukrida.root.ui.admin.screens.ongoing.components.OngoingTripCard
import org.ukrida.root.ui.admin.screens.ongoing.model.Trip
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun OnGoingScreen(
    navController: NavController,
    onMenuClick: () -> Unit
) {

    val trips = listOf(
        Trip(
            1,
            "Promised Land",
            "Lorem ipsum dolor sit amet",
            "",
            "01/07/2026"
        ),
        Trip(
            2,
            "Holy Journey",
            "Lorem ipsum dolor sit amet",
            "",
            "15/07/2026"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {

        OnGoingTopBar(
            title = "ONGOING TOUR",
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

                Text(
                    text = "ONGOING TRIP",
                    style = MaterialTheme.typography.titleLarge,
                    color = TitleColor
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }

            items(trips) { trip ->

                OngoingTripCard(
                    trip = trip,
                    onClick = {
                        val route = Screen.OngoingDetail.route.replace("{tripId}", trip.id.toString())
                        navController.navigate(route)
                    }
                )
            }

            item {
                Spacer(
                    modifier = Modifier.height(24.dp)
                )
            }
        }
    }
}