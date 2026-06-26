package org.ukrida.root.ui.admin.screens.trip.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.Group
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MainButton
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun TripCard(
    group: Group,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column {

            // IMAGE PLACEHOLDER

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(androidx.compose.ui.graphics.Color.Gray)
            )

            // CONTENT

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        androidx.compose.ui.graphics.Color(0xFF9E7B5F)
                    )
                    .padding(16.dp)
            ) {

                Text(
                    text = group.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = H1Color
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = group.description ?: "-",
                    style = MaterialTheme.typography.bodyMedium,
                    color = BodyColor,
                    maxLines = 2
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = group.location ?: "-",
                    style = MaterialTheme.typography.bodySmall,
                    color = BodyColor
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Text(
                        text = buildString {
                            append(group.startDate ?: "-")
                            append(" - ")
                            append(group.endDate ?: "-")
                        },
                        style = MaterialTheme.typography.labelLarge,
                        color = H1Color
                    )
                }
            }
        }
    }
}