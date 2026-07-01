package org.ukrida.root.ui.admin.screens.hymn.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.ukrida.root.ui.admin.screens.hymn.components.SongSectionCard
import org.ukrida.root.ui.admin.screens.hymn.viewmodel.EditSongViewModel
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MainButton
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun EditSongScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: EditSongViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {

        Text(
            text = "< Back",
            color = BodyColor,
            modifier = Modifier.clickable {
                navController.popBackStack()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "EDIT SONGS",
            style = MaterialTheme.typography.titleLarge,
            color = H1Color
        )

        Text(
            text = "HYMN FOR HIM",
            style = MaterialTheme.typography.titleLarge,
            color = H1Color
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = viewModel.title,
            onValueChange = viewModel::updateTitle,
            label = {
                Text("Song Title")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = BodyColor,
                unfocusedTextColor = BodyColor,

                focusedBorderColor = TitleColor,
                unfocusedBorderColor = DrawerBackground,

                focusedLabelColor = TitleColor,
                unfocusedLabelColor = DrawerBackground,

                cursorColor = TitleColor
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.author,
            onValueChange = viewModel::updateAuthor,
            label = {
                Text("Author")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = BodyColor,
                unfocusedTextColor = BodyColor,

                focusedBorderColor = TitleColor,
                unfocusedBorderColor = DrawerBackground,

                focusedLabelColor = TitleColor,
                unfocusedLabelColor = DrawerBackground,

                cursorColor = TitleColor
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        SongSectionCard(
            sectionTitle = "Verse 1",

            onTitleChange = {
                // opsional jika title section bisa diubah
            },

            lyrics = viewModel.lyrics,

            onLyricsChange = {
                viewModel.updateLyrics(it)
            },

            onRemove = {
                // tidak perlu remove jika hanya edit 1 section
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // save changes
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainButton
            )
        ) {
            Text(
                text = "SAVE",
                color = H1Color
            )
        }
    }
}