package org.ukrida.root.ui.Public.screens.itinerary.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ActivityTableHeader() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.25f)
            )
    ) {

        Box(
            modifier = Modifier
                .weight(0.22f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "FROM",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        VerticalDivider(
            thickness = 1.dp,
            color = Color.White.copy(alpha = 0.25f)
        )

        Box(
            modifier = Modifier
                .weight(0.22f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "UNTIL",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        VerticalDivider(
            thickness = 1.dp,
            color = Color.White.copy(alpha = 0.25f)
        )

        Box(
            modifier = Modifier
                .weight(0.56f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "ACTIVITY",
                modifier = Modifier.padding(start = 12.dp),
                color = Color.White,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}