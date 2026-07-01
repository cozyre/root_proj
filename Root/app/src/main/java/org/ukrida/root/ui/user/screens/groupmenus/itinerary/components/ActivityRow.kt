package org.ukrida.root.ui.user.screens.groupmenus.itinerary.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.ItineraryItem

@Composable
fun ActivityRow(
    item: ItineraryItem
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.15f)
            )
    ) {

        Box(
            modifier = Modifier
                .weight(0.22f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.startTime,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }

        VerticalDivider(
            thickness = 1.dp,
            color = Color.White.copy(alpha = 0.15f)
        )

        Box(
            modifier = Modifier
                .weight(0.22f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.endTime ?: "-",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }

        VerticalDivider(
            thickness = 1.dp,
            color = Color.White.copy(alpha = 0.15f)
        )

        Box(
            modifier = Modifier
                .weight(0.56f)
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = item.description,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }

}