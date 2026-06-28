package org.ukrida.root.ui.Public.screens.hymn.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DayHeader(
    title: String
) {
    Text(
        text = title,
        modifier = Modifier.padding(vertical = 20.dp),
        style = MaterialTheme.typography.titleLarge,
        color = Color(0xFFE8D8C9)
    )
}