package org.ukrida.root.ui.user.screens.groupmenus.journal.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.screens.groupmenus.journal.components.CustomJournalField
import org.ukrida.root.ui.user.screens.groupmenus.journal.components.JournalBackButton
import org.ukrida.root.ui.user.screens.groupmenus.journal.components.JournalDatePicker
import org.ukrida.root.ui.user.screens.groupmenus.journal.components.JournalDeleteButton
import org.ukrida.root.ui.user.screens.groupmenus.journal.components.JournalSaveButton
import org.ukrida.root.ui.user.screens.groupmenus.journal.viewmodel.JournalEditorViewModel
import org.ukrida.root.utils.Resource
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalEditorScreen(
    viewModel: JournalEditorViewModel,
    navController: NavHostController,
    groupId: Int,
    journalId: Int?
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    LaunchedEffect(groupId, journalId) {
        viewModel.loadJournal(groupId, journalId)
    }

    LaunchedEffect(uiState.saveResult) {
        uiState.saveResult?.let { resource ->
            when (resource) {
                is Resource.Success -> {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    viewModel.resetSaveResult()
                    navController.popBackStack()
                }
                is Resource.Error -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                    viewModel.resetSaveResult()
                }
                else -> {}
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFF2A2522)
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
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    JournalBackButton(
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomJournalField(
                        label = "Title",
                        value = uiState.title,
                        placeholder = "Enter journal title",
                        error = uiState.titleError,
                        onValueChange = {
                            viewModel.updateTitle(it)
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    JournalDatePicker(
                        date = uiState.journalDate,
                        onClick = {
                            showDatePicker = true
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    CustomJournalField(
                        label = "Content",
                        value = uiState.content,
                        placeholder = "Write your journal...",
                        error = uiState.contentError,
                        minLines = 10,
                        onValueChange = {
                            viewModel.updateContent(it)
                        }
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    if (uiState.saveResult is Resource.Loading) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xFFE8D8C9)
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            if (uiState.isEditMode) {
                                JournalDeleteButton(
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        viewModel.deleteJournal()
                                    }
                                )
                            }
                            JournalSaveButton(
                                modifier = Modifier.weight(1f),
                                isEditMode = uiState.isEditMode,
                                onClick = {
                                    viewModel.saveJournal(groupId)
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = {
                    showDatePicker = false
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val date = Instant
                                    .ofEpochMilli(millis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                viewModel.updateDate(date.toString())
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState
                )
            }
        }
    }
}
