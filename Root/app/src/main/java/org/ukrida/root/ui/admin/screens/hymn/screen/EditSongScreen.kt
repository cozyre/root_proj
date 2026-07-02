package org.ukrida.root.ui.admin.screens.hymn.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.ukrida.root.ui.admin.screens.hymn.components.SongSectionCard
import org.ukrida.root.ui.admin.screens.hymn.viewmodel.EditSongViewModel
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MainButton
import org.ukrida.root.ui.theme.RejectButton
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun EditSongScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: EditSongViewModel
) {

    LaunchedEffect(viewModel.isSuccess) {
        if (viewModel.isSuccess) {
            viewModel.resetSuccessState()

            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("refreshSongs", true)

            navController.popBackStack()
        }
    }

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

        when {
            viewModel.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            else -> {
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

                viewModel.sections.forEachIndexed { index, section ->

                    SongSectionCard(
                        sectionTitle = section.title,

                        onTitleChange = { newTitle ->
                            viewModel.updateSectionTitle(
                                index = index,
                                value = newTitle
                            )
                        },

                        lyrics = section.lyrics,

                        onLyricsChange = { newLyrics ->
                            viewModel.updateSectionLyrics(
                                index = index,
                                value = newLyrics
                            )
                        },

                        onRemove = {
                            viewModel.removeSection(index)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                TextButton(
                    onClick = {
                        viewModel.addSection()
                    }
                ) {
                    Text(
                        text = "Add Section",
                        color = MainButton
                    )
                }

                if (viewModel.errorMessage != null) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = viewModel.errorMessage ?: "",
                        color = RejectButton,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        viewModel.saveSong()
                    },
                    enabled = !viewModel.isSaving,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainButton
                    )
                ) {
                    if (viewModel.isSaving) {
                        CircularProgressIndicator()
                    } else {
                        Text(
                            text = "SAVE",
                            color = H1Color
                        )
                    }
                }
            }
        }
    }
}