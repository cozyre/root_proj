package org.ukrida.root.ui.user.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardTopBar(
    title: String,
    expanded: Boolean,
    onExpandClick: () -> Unit,
    onNotificationClick: () -> Unit = {}
) {
    val cream = Color(0xFFE5C19A)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        // Logo
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "R",
                color = cream,
                fontSize = 34.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        }

        // Title
        Box(
            modifier = Modifier.weight(3f),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFFE5C19A),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    modifier = Modifier.size(28.dp),
                    onClick = onExpandClick
                ) {
                    Icon(
                        imageVector = if (expanded)
                            Icons.Default.ArrowDropUp
                        else
                            Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color(0xFFE5C19A)
                    )
                }
            }
        }

        // Notification
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(
                onClick = onNotificationClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = Color(0xFFE5C19A)
                )
            }
        }
    }
}