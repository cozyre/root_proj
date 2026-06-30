package org.ukrida.root.ui.user.screens.journal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomJournalField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    minLines: Int = 1,
    error: String? = null
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFFE8D8C9)
        )
        androidx.compose.foundation.layout.Spacer(
            modifier = Modifier.padding(top = 8.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            minLines = minLines,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .background(
                    Color(0xFF9A775B),
                    RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.White.copy(alpha = .5f)
                    )
                }
                innerTextField()
            }
        )
        if (error != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = error,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}