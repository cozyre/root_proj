package org.ukrida.root.ui.user.screens.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(10.dp),

        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall
            )
        },

        colors = OutlinedTextFieldDefaults.colors(

            focusedBorderColor = Color(0xFF7A8A4A),
            unfocusedBorderColor = Color(0xFF7A8A4A),

            focusedLabelColor = Color(0xFF7A8A4A),
            unfocusedLabelColor = Color.Gray,

            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,

            cursorColor = Color(0xFF7A8A4A),

            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent

        )
    )

}