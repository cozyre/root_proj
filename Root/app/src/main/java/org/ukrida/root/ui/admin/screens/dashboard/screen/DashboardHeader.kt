package org.ukrida.root.ui.admin.screens.dashboard.screen

import org.ukrida.root.ui.theme.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardHeader() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "R",
            style = MaterialTheme.typography.headlineLarge,
            color = TitleColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "ROOT ADMIN",
            style = MaterialTheme.typography.titleLarge,
            color = H1Color
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            style = MaterialTheme.typography.bodyLarge,
            color = BodyColor
        )
    }
}