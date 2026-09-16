package org.ukrida.root.ui.user.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun PublicTopBar(
    title: String,
    onNotificationClick: () -> Unit = {},
    onBackClick: (() -> Unit)? = null,
    notificationViewModel: NotificationViewModel
) {
    val titleColor = TitleColor
    var showNotifications by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {

        // Kiri (Back atau Logo)
        if (onBackClick != null) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = titleColor
                )
            }
        } else {
            Text(
                modifier = Modifier.align(Alignment.CenterStart),
                text = "R",
                color = titleColor,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }

        // Tengah (Selalu Simetris)
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title.uppercase(),
            color = titleColor,
            style = MaterialTheme.typography.titleLarge,
        )

        // Kanan
        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = {
                showNotifications = !showNotifications
                onNotificationClick()
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = titleColor
            )
        }

        if (showNotifications) {
            NotificationPopup(
                viewModel = notificationViewModel
            ) {
                showNotifications = false
            }
        }
    }
}
