package org.ukrida.root.ui.user.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MicroElement

@Composable
fun HidePhoneCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MicroElement,
                uncheckedColor = H1Color,
                checkmarkColor = Color.White
            )
        )

        Text(
            text = "Show phone number to public",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )

    }

}