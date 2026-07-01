package org.ukrida.root.ui.user.screens.groupmenus.journal.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.components.DashboardMenu
import org.ukrida.root.ui.user.components.DashboardTopBar
import org.ukrida.root.ui.user.screens.groupmenus.journal.components.CustomJournalField
import org.ukrida.root.ui.user.screens.groupmenus.journal.components.JournalBackButton
import org.ukrida.root.ui.user.screens.groupmenus.journal.components.JournalDatePicker
import org.ukrida.root.ui.user.screens.groupmenus.journal.components.JournalSaveButton
import org.ukrida.root.ui.user.screens.groupmenus.journal.viewmodel.JournalEditorViewModel
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalEditorScreen(
    navController: NavHostController,
    groupId: Int,
    journalId: Int?
) {

    val viewModel: org.ukrida.root.ui.user.screens.groupmenus.journal.viewmodel.JournalEditorViewModel = viewModel()

    val title by viewModel.title.collectAsState()
    val content by viewModel.content.collectAsState()
    val journalDate by viewModel.journalDate.collectAsState()
    val isEditMode by viewModel.isEditMode.collectAsState()
    val titleError by viewModel.titleError.collectAsState()
    val contentError by viewModel.contentError.collectAsState()

    var expanded by remember {
        mutableStateOf(false)
    }
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState()
    LaunchedEffect(journalId) {
        viewModel.loadJournal(journalId)
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
                DashboardTopBar(
                    title = "JOURNAL",
                    expanded = expanded,
                    onExpandClick = {
                        expanded = !expanded
                    }
                )
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    _root_ide_package_.org.ukrida.root.ui.user.screens.groupmenus.journal.components.JournalBackButton(
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    _root_ide_package_.org.ukrida.root.ui.user.screens.groupmenus.journal.components.CustomJournalField(
                        label = "Title",
                        value = title,
                        placeholder = "Enter journal title",
                        error = titleError,
                        onValueChange = {
                            viewModel.updateTitle(it)
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    _root_ide_package_.org.ukrida.root.ui.user.screens.groupmenus.journal.components.JournalDatePicker(
                        date = journalDate,
                        onClick = {
                            showDatePicker = true
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    _root_ide_package_.org.ukrida.root.ui.user.screens.groupmenus.journal.components.CustomJournalField(
                        label = "Content",
                        value = content,
                        placeholder = "Write your journal...",
                        error = contentError,
                        minLines = 10,
                        onValueChange = {
                            viewModel.updateContent(it)
                        }
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    _root_ide_package_.org.ukrida.root.ui.user.screens.groupmenus.journal.components.JournalSaveButton(
                        isEditMode = isEditMode,
                        onClick = {
                            val success = viewModel.saveJournal()
                            if (success) {
                                // TODO Backend Integration
                                // After create/update succeeds:
                                //
                                // JournalScreen should call:
                                // viewModel.loadJournals(groupId)
                                //
                                // Then:
                                navController.popBackStack()
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = .4f))
                        .clickable(
                            indication = null,
                            interactionSource = remember {
                                MutableInteractionSource()
                            }
                        ) {
                            expanded = false
                        }
                )
            }
            if (expanded) {
                DashboardMenu(
                    onDashboardClick = {
                        expanded = false
                        navController.navigate(
                            PublicScreen.Dashboard.createRoute(groupId)
                        )
                    },
                    onItineraryClick = {
                        expanded = false
                        navController.navigate(
                            PublicScreen.Itinerary.createRoute(groupId)
                        )
                    },
                    onHymnClick = {
                        expanded = false
                        navController.navigate(
                            PublicScreen.Hymn.createRoute(groupId)
                        )
                    },
                    onDailyBreadClick = {
                        expanded = false
                        navController.navigate(
                            PublicScreen.DailyBread.createRoute(groupId)
                        )
                    },
                    onJournalClick = {
                        expanded = false
                        navController.navigate(
                            PublicScreen.Journal.createRoute(groupId)
                        )
                    },
                    onGalleryClick = {
                        expanded = false
                        navController.navigate(
                            PublicScreen.Gallery.createRoute(groupId)
                        )
                    },
                    onMembersClick = {
                        expanded = false
                        navController.navigate(
                            PublicScreen.Members.createRoute(groupId)
                        )
                    }
                )
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
                                val date = java.time.Instant
                                    .ofEpochMilli(millis)
                                    .atZone(java.time.ZoneId.systemDefault())
                                    .toLocalDate()
                                viewModel.updateDate(date.toString())
                            }
                            showDatePicker = false
                        }
                    ) {
                        androidx.compose.material3.Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = false
                        }
                    ) {
                        androidx.compose.material3.Text("Cancel")
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