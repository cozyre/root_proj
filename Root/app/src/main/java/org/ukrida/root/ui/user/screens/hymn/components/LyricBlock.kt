package org.ukrida.root.ui.user.screens.hymn.components

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

@Composable
fun LyricBlock(
    title: String,
    lyrics: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFFE8D8C9)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = lyrics,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}