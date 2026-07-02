package org.ukrida.root.ui.user.screens.groupmenus.journal.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.screens.groupmenus.journal.components.CreateJournalButton
import org.ukrida.root.ui.user.screens.groupmenus.journal.components.JournalCard
import org.ukrida.root.ui.user.screens.groupmenus.journal.viewmodel.JournalViewModel
import org.ukrida.root.utils.Resource

@Composable
fun JournalScreen(
    viewModel: JournalViewModel,
    navController: NavHostController,
    groupId: Int
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(groupId) {
        viewModel.loadJournals(groupId)
    }

    LaunchedEffect(uiState.actionResult) {
        uiState.actionResult?.let { resource ->
            when (resource) {
                is Resource.Success -> {
                    viewModel.resetActionResult()
                }
                is Resource.Error -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                    viewModel.resetActionResult()
                }
                else -> {}
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFF2A2522),
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2A2522))
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Journal",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFFE8D8C9)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Write your spiritual journey during the pilgrimage.",
                        color = Color.White.copy(alpha = .8f)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CreateJournalButton(
                            onClick = {
                                navController.navigate(
                                    PublicScreen.JournalEditor.createRoute(groupId)
                                )
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    when (val journalsRes = uiState.journals) {
                        is Resource.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = Color(0xFFE8D8C9))
                            }
                        }
                        is Resource.Error -> {
                            Text(
                                text = journalsRes.message,
                                color = Color.Red,
                                modifier = Modifier.padding(vertical = 20.dp)
                            )
                        }
                        is Resource.Success -> {
                            val journals = journalsRes.data
                            if (journals.isEmpty()) {
                                Spacer(modifier = Modifier.height(48.dp))
                                Text(
                                    text = "No journal yet.",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Create your first journal to record your pilgrimage.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White.copy(alpha = 0.5f)
                                )
                            } else {
                                journals.forEach { journal ->
                                    JournalCard(
                                        journal = journal,
                                        onClick = {
                                            navController.navigate(
                                                PublicScreen.JournalEditor.createRoute(
                                                    groupId,
                                                    journal.id
                                                )
                                            )
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
