package org.ukrida.root.ui.Public.screens.promisedland.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.Group
import org.ukrida.root.ui.Public.screens.home.components.RecommendationCard
import java.time.Year

@Composable
fun AllTripSection(
    allTrips: List<Group>,
    onTripClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "ALL TRIP ${Year.now().value}",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFFE8D8C9)
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (allTrips.isEmpty()) {
            Text(
                text = "No trips available.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.7f)
            )
        } else {
            allTrips.forEach { group ->
                RecommendationCard(
                    group = group,
                    onClick = {
                        onTripClick(group.id)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}