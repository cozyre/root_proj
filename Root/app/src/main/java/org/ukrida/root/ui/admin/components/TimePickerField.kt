package org.ukrida.root.ui.admin.components

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun TimePickerField(
    value: String,
    onTimeSelected: (String) -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .width(88.dp)
            .height(56.dp)
            .clickable {
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        onTimeSelected(
                            "%02d:%02d".format(hour, minute)
                        )
                    },
                    0,
                    0,
                    true
                ).show()
            }
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            modifier = Modifier.fillMaxSize(),
            singleLine = true,
            shape = RoundedCornerShape(50.dp),
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.White,
                disabledBorderColor = Color(0xFF6B5C4E),
                disabledContainerColor = Color(0xFF2A2018)
            ),
            textStyle = MaterialTheme.typography.bodySmall.copy(
                fontSize = 11.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}