package org.ukrida.root.ui.user.screens.itinerary.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ItineraryHeader(
    date: String,
    description: String
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Take a Tour!",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFFE8D8C9)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Follow today's travel schedule.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = .8f)
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        Text(
            text = date,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFD7C7B6)
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

    }

}