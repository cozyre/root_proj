package org.ukrida.root.ui.admin.screens.ongoing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.admin.screens.ongoing.model.Trip
import org.ukrida.root.ui.theme.*

@Composable
fun OngoingTripCard(
    trip: Trip,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = DrawerBackground)

        ) {

        Column() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(MainButton)
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = trip.title,
                style = MaterialTheme.typography.headlineLarge,
                color = H1Color
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = trip.description,
                color = BodyColor
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = trip.dateRange,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                color = H1Color,
                textAlign = TextAlign.End
            )
        }
    }
}