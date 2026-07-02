package org.ukrida.root.ui.user.screens.groupmenus.dashboard.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.model.GroupDetail

@Composable
fun InformationSection(
    group: GroupDetail
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Information",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFFE8D8C9)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = group.description ?: "-",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.85f),
            textAlign = TextAlign.Center
        )
    }

}