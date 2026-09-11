package org.ukrida.root.ui.admin.screens.hymn.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.screens.hymn.components.SongCard
import org.ukrida.root.ui.admin.screens.hymn.viewmodel.HymnForHimViewModel
import org.ukrida.root.ui.theme.DarkBrown
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MicroElement

@Composable
fun HymnForHimScreen(
    onMenuClick: () -> Unit,
    onCreateSongClick: () -> Unit = {},
    onSongClick: (Int) -> Unit = {},
    viewModel: HymnForHimViewModel = viewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBrown)
            .verticalScroll(rememberScrollState())
    ) {

        TopBar(
            title = "HYMN FOR HIM",
            onMenuClick = onMenuClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Hymn for Him",
                style = MaterialTheme.typography.headlineSmall,
                color = H1Color
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Manage worship songs that have been registered and used during ROOT journeys.",
                color = BodyColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onCreateSongClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MicroElement
                )
            ) {
                Text(
                    text = "CREATE NEW",
                    color = H1Color
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "SONGS",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                color = H1Color
            )

            Spacer(modifier = Modifier.height(20.dp))

            when {
                viewModel.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                viewModel.errorMessage != null -> {
                    Text(
                        text = viewModel.errorMessage ?: "Failed to load songs",
                        color = BodyColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                viewModel.songs.isEmpty() -> {
                    Text(
                        text = "No songs available yet.",
                        color = BodyColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                else -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        viewModel.songs.forEach { song ->
                            SongCard(
                                title = song.title,
                                author = song.author,
                                onClick = {
                                    onSongClick(song.id)
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}