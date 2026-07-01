package org.ukrida.root.ui.admin.screens.hymn.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.RejectButton
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun SongSectionCard(
    sectionTitle: String,
    lyrics: String,
    onTitleChange: (String) -> Unit,
    onLyricsChange: (String) -> Unit,
    onRemove: () -> Unit
) {
    Column {

        OutlinedTextField(
            value = sectionTitle,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = BodyColor,
                unfocusedTextColor = DrawerBackground,

                focusedBorderColor = TitleColor,
                unfocusedBorderColor = DrawerBackground,

                focusedLabelColor = TitleColor,
                unfocusedLabelColor = DrawerBackground,

                cursorColor = TitleColor
            ),
            onValueChange = onTitleChange,
            label = {
                Text("Section Title")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = lyrics,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = BodyColor,
                unfocusedTextColor = DrawerBackground,

                focusedBorderColor = TitleColor,
                unfocusedBorderColor = DrawerBackground,

                focusedLabelColor = TitleColor,
                unfocusedLabelColor = DrawerBackground,

                cursorColor = TitleColor
            ),
            onValueChange = onLyricsChange,
            label = {
                Text("Lyrics")
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 5
        )

        TextButton(
            onClick = onRemove
        ) {
            Text("Remove", color = RejectButton)
        }
    }
}