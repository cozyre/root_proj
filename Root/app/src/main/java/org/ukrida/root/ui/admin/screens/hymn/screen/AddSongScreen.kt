package org.ukrida.root.ui.admin.screens.hymn.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.admin.screens.hymn.components.SongSectionCard
import org.ukrida.root.ui.admin.screens.hymn.viewmodel.SongSectionData
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MainButton
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun AddSongScreen(
    onBackClick: () -> Unit
) {

    var title by remember {
        mutableStateOf("")
    }

    var author by remember {
        mutableStateOf("")
    }

    var sections by remember {
        mutableStateOf(
            listOf(
                SongSectionData(
                    title = "Verse 1",
                    lyrics = ""
                )
            )
        )
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
                onBackClick()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "ADD SONGS",
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
            value = title,
            onValueChange = {
                title = it
            },
            label = {
                Text("Song Title")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = BodyColor,
                unfocusedTextColor = DrawerBackground,

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
            value = author,
            onValueChange = {
                author = it
            },
            label = {
                Text("Author")
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = BodyColor,
                unfocusedTextColor = DrawerBackground,

                focusedBorderColor = TitleColor,
                unfocusedBorderColor = DrawerBackground,

                focusedLabelColor = TitleColor,
                unfocusedLabelColor = DrawerBackground,

                cursorColor = TitleColor
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        sections.forEachIndexed { index, section ->

            SongSectionCard(
                sectionTitle = section.title,

                onTitleChange = { newTitle ->

                    sections = sections.toMutableList().apply {
                        this[index] = this[index].copy(
                            title = newTitle
                        )
                    }
                },

                lyrics = section.lyrics,

                onLyricsChange = { newLyrics ->

                    sections = sections.toMutableList().apply {
                        this[index] = this[index].copy(
                            lyrics = newLyrics
                        )
                    }
                },

                onRemove = {
                    if (sections.size > 1) {
                        sections = sections.toMutableList().apply {
                            removeAt(index)
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        TextButton(
            onClick = {

                val nextNumber =
                    sections.count {
                        it.title.startsWith("Verse")
                    } + 1

                sections = sections + SongSectionData(
                    title = "Verse $nextNumber",
                    lyrics = ""
                )
            }
        ) {
            Text("Add Section", color = MainButton)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // submit
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainButton),
        ) {
            Text("SUBMIT", color = H1Color)
        }
    }
}