package org.ukrida.root.ui.admin.screens.dashboard.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.admin.screens.dashboard.components.ApprovalCard
import org.ukrida.root.ui.theme.H1Color

@Composable
fun ApprovalSection() {

    Column {

        Text(
            text = "APPROVAL REQUEST",
            style = MaterialTheme.typography.titleLarge,
            color = H1Color
        )

        Spacer(modifier = Modifier.height(20.dp))

        ApprovalCard()

        Spacer(modifier = Modifier.height(16.dp))

        ApprovalCard()
    }
}