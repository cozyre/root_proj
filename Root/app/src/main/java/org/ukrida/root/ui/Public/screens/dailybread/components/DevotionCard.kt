package org.ukrida.root.ui.Public.screens.dailybread.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.DevotionDate

@Composable
fun DevotionCard(
    devotion: DevotionDate,
    onClick: (DevotionDate) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(devotion)
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFA67D5B)
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = devotion.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = devotion.date,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = .8f)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Tap to read today's devotion.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = .9f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }

    }

}