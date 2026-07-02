package org.ukrida.root.ui.user.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

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
        style = MaterialTheme.typography.headlineLarge,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFFE5C19A)
    )

}