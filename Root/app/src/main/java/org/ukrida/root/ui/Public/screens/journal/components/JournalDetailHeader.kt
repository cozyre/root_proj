package org.ukrida.root.ui.Public.screens.journal.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.Journal

@Composable
fun JournalDetailHeader(
    journal: Journal
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = journal.title,
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFFE8D8C9)
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        // TODO Backend Integration
        // Format journalDate before displaying if API returns ISO datetime.
        Text(
            text = journal.journalDate,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}