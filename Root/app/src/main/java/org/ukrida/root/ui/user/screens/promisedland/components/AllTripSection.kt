package org.ukrida.root.ui.user.screens.promisedland.components

import android.util.Log
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
import org.ukrida.root.ui.user.screens.home.components.RecommendationCard
import java.time.Year

@Composable
fun AllTripSection(
    allTrips: List<Group>?,
    onTripClick: (Int) -> Unit,
    coverImages: Map<Int, String?>,
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
        if (allTrips.isNullOrEmpty()) {
            Text(
                text = "No trips available.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.7f)
            )
        } else {
            allTrips.forEach { group ->

                Log.d(
                    "PROMISED_IMAGE",
                    "groupId=${group.id}, imageUrl=${coverImages[group.id]}"
                )

                RecommendationCard(
                    group = group,
                    onClick = {
                        onTripClick(group.id)
                    },
                    imageUrl = coverImages[group.id],
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}