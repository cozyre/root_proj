package org.ukrida.root.ui.Public.screens.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DashboardMenuItem(
    title: String,
    onClick: () -> Unit
) {

    Text(
        text = title,
        modifier = Modifier.clickable {
            onClick()
        },
        style = MaterialTheme.typography.titleLarge,
        color = Color(0xFFE5C19A)
    )

}