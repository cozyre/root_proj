package org.ukrida.root.ui.Public.screens.journal.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// TODO Backend Integration
// Navigate to Create Journal screen.
// After successful creation, refresh journal list.
@Composable
fun CreateJournalButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF7A8A4A)
        ),
        modifier = Modifier.height(28.dp)
    ) {
        Text(
            text = "CREATE NEW JOURNAL",
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}