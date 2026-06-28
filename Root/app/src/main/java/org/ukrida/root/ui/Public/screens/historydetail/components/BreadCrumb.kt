package org.ukrida.root.ui.Public.screens.historydetail.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun Breadcrumb() {

    Text(
        text = "Promised Land > History",
        style = MaterialTheme.typography.bodyMedium,
        color = Color.White.copy(alpha = 0.7f)
    )

}