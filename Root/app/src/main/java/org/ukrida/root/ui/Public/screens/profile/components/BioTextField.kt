package org.ukrida.root.ui.Public.screens.profile.components

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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@Composable
fun BioTextField(
    value: String,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,

        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),

        shape = RoundedCornerShape(10.dp),

        textStyle = MaterialTheme.typography.bodyMedium,

        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences
        ),

        label = {
            Text("Bio")
        },

        colors = OutlinedTextFieldDefaults.colors(

            focusedBorderColor = Color(0xFF7A8A4A),
            unfocusedBorderColor = Color(0xFF7A8A4A),

            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,

            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,

            cursorColor = Color(0xFF7A8A4A)

        )
    )

}