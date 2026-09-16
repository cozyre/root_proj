package org.ukrida.root.ui.user.screens.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LogoutButton(
    onLogout: () -> Unit
) {

    Text(
        text = "Log out from account",
        color = Color.Red,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.clickable {
            onLogout()
        }
    )

}