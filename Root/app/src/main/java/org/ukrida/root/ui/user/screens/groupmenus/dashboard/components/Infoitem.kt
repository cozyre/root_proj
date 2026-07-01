package org.ukrida.root.ui.user.screens.groupmenus.dashboard.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InfoItem(
    title: String,
    value: String
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = Color(0xFFE5C19A)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(18.dp))
    }
}