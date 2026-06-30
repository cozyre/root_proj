package org.ukrida.root.ui.Public.screens.history.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Breadcrumb(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Promised Land > History",
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge,
        color = Color.White.copy(alpha = 0.8f)
    )
}