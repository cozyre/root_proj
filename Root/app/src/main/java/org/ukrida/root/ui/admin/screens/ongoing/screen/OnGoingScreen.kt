package org.ukrida.root.ui.admin.screens.ongoing.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.ukrida.root.data.model.Group
import org.ukrida.root.ui.admin.components.PriceTripCard
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.navigation.Screen
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.OnGoingViewModel
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun OnGoingScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: OnGoingViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    var selectedGroupToDelete by remember {
        mutableStateOf<Group?>(null)
    }
    var showDialog by remember { mutableStateOf(false) }
    var selectedId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {

        TopBar(
            title = "ONGOING TOUR",
            onMenuClick = onMenuClick
        )

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = TitleColor)
                }
            }

            uiState.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.errorMessage!!,
                        style = MaterialTheme.typography.bodyMedium,
                        color = BodyColor
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    item {
                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "ONGOING TRIP",
                            style = MaterialTheme.typography.titleLarge,
                            color = H1Color
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )
                        Text(
                            text = "View trips that are currently in progress " +
                                    "and still open for registration. Check " +
                                    "the travel schedule, explore trip details, " +
                                    "and secure your spot before availability runs out.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = BodyColor
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    items(uiState.groups) { group ->
                        PriceTripCard(
                            group = group,
                            imageUrl = uiState.coverImages[group.id],
                            onClick = {
                                navController.navigate("ongoing_detail/${group.id}")
                            },
                            showRemoveButton = true,
                            onRemoveClick = {
                                selectedGroupToDelete = group
                                showDeleteDialog = true
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
                if (showDialog) {
                    androidx.compose.material3.AlertDialog(
                        onDismissRequest = {
                            showDialog = false
                            selectedId = null
                        },
                        title = {
                            Text("Hapus Trip")
                        },
                        text = {
                            Text("Apakah kamu yakin ingin menghapus trip ini?")
                        },
                        confirmButton = {
                            androidx.compose.material3.TextButton(
                                onClick = {
                                    selectedId?.let {
                                        viewModel.deleteTrip(it)
                                    }
                                    showDialog = false
                                    selectedId = null
                                }
                            ) {
                                Text("Hapus")
                            }
                        },
                        dismissButton = {
                            androidx.compose.material3.TextButton(
                                onClick = {
                                    showDialog = false
                                    selectedId = null
                                }
                            ) {
                                Text("Batal")
                            }
                        }
                    )
                }
            }
        }
    }
}