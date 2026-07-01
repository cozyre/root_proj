package org.ukrida.root.ui.user.screens.groupmenus.dailybread.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.Devotion

@Composable
fun DevotionDetailHeader(
    devotion: Devotion
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = devotion.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE8D8C9)
        )
        Text(
            text = devotion.date,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = .7f)
        )
    }
}