package org.ukrida.root.ui.Public.screens.hymn.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.Song

@Composable
fun HymnDetailHeader(
    song: Song
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = song.title,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = song.author ?: "-",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFFE8D8C9)
        )
    }
}