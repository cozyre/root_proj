package org.ukrida.root.ui.user.screens.groupmenus.journal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun JournalDatePicker(
    date: String,
    onClick: () -> Unit
) {
    Column {
        Text(
            text = "Date",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFFE8D8C9)
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFF9A775B),
                    RoundedCornerShape(16.dp)
                )
                .clickable {
                    onClick()
                }
                .padding(
                    horizontal = 16.dp,
                    vertical = 18.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text =
                    date.ifBlank { "Select journal date" },
                color =
                    if (date.isBlank())
                        Color.White.copy(alpha = .5f)
                    else
                        Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                imageVector = Icons.Outlined.DateRange,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}