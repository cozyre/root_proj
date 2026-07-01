package org.ukrida.root.ui.admin.screens.ongoing.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.ukrida.root.ui.admin.components.DocumentationGridItem
import org.ukrida.root.ui.admin.components.ImagePlaceholder
import org.ukrida.root.ui.admin.components.LeaderItem
import org.ukrida.root.ui.admin.components.MemberGridItem
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.navigation.Screen
import org.ukrida.root.ui.admin.screens.ongoing.components.*
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.OnGoingDetailViewModel
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.admin.screens.finished.viewmodel.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnGoingDetailScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: OnGoingDetailViewModel,
    modifier: Modifier = Modifier
) {
    var showAllMembers by remember { mutableStateOf(false) }
    var showAllDocs by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()
    val tripData = uiState.tripState

    val currentTitle = tripData?.title ?: "Loading..."
    val currentDescription = tripData?.description ?: "Memuat deskripsi..."
    val currentDateRange = tripData?.dateRange ?: "DD - DD MM YYYY"

    // --- STATE DIALOG ---
    var showDeleteMemberDialog by remember { mutableStateOf(false) }
    var selectedMemberToRemove by remember { mutableStateOf<MemberUiModel?>(null) }

    var showDeleteDocDialog by remember { mutableStateOf(false) }
    var selectedDocToRemove by remember { mutableStateOf<DocumentationUiModel?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> /* TODO: update foto utama */ }
    )
    var showEditSheet by remember {
        mutableStateOf(false)
    }

    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            TopBar(
                title = "EDIT TOUR",
                onMenuClick = onMenuClick
            )
        }
    ) { innerPadding ->

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFFC49A6C))
                }
            }

            uiState.errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.errorMessage!!,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }

            else -> {
                Column(
                    modifier = modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Breadcrumb
                    Text(
                        text = "$currentTitle > Ongoing Trip > Detail",
                        color = DrawerBackground,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.OngoingTrip.route) {
                                    popUpTo(Screen.OngoingTrip.route)
                                    launchSingleTop = true
                                }
                            }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Timeline
                    TimelineBar(onStageClick = { stage ->
                        when (stage) {
                            "Hymn for Him" -> navController.navigate("edit_songs/${viewModel.tripId}")
                            "Itinerary" -> navController.navigate("edit_itinerary/${viewModel.tripId}")
                            "Daily Bread" -> navController.navigate("daily_bread/${viewModel.tripId}")
                        }
                    })

                    Spacer(modifier = Modifier.height(32.dp))

                    // Trip Header
                    TripHeader(
                        title = currentTitle,
                        description = currentDescription,
                        dateRange = currentDateRange,
                        onEditClick = { showEditSheet = true }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Image Placeholder
                    ImagePlaceholder(
                        imageUrl = tripData?.imageUrl,
                        onClick = {
                            galleryLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Section Group Member
                    Text(
                        text = "GROUP MEMBER",
                        color = Color(0xFFD4C5B9),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    uiState.mentor?.let {
                        LeaderItem(
                            role = "MENTOR",
                            name = it.name
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    uiState.coordinator?.let {
                        LeaderItem(
                            role = "COORDINATOR",
                            name = it.name
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                    val memberList =
                        if (showAllMembers)
                            uiState.members
                        else
                            uiState.members.take(3)
                    val gridRows = (memberList.size + 2) / 3
                    val gridHeight = (gridRows * 100).dp

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(gridHeight)
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            userScrollEnabled = false
                        ) {
                            items(memberList, key = { it.id }) { member ->
                                Box(contentAlignment = Alignment.Center) {
                                    MemberGridItem(
                                        member = member,
                                        onRemoveClick = {
                                            selectedMemberToRemove = it
                                            showDeleteMemberDialog = true
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (showAllMembers) "See Less" else "See More",
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .clickable {
                                    showAllMembers = !showAllMembers
                                }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Section Dokumentasi
                    Text(
                        text = "DOKUMENTASI",
                        color = Color(0xFFD4C5B9),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    val docList =
                        if (showAllDocs)
                            uiState.documentations
                        else
                            uiState.documentations.take(6)
                    val docGridRows = (docList.size + 1) / 2
                    val docGridHeight = (docGridRows * 112).dp

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(docGridHeight)
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            userScrollEnabled = false
                        ) {
                            items(docList, key = { it.id }) { doc ->
                                DocumentationGridItem(
                                    documentation = doc,
                                    onRemoveClick = {
                                        selectedDocToRemove = it
                                        showDeleteDocDialog = true
                                    }
                                )
                            }
                        }
                    }
                    if (uiState.documentations.size > 6) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = if (showAllDocs) "See Less" else "See More",
                                color = Color.White,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .clickable {
                                        showAllDocs = !showAllDocs
                                    }
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }

    // Dialog hapus member
    if (showDeleteMemberDialog && selectedMemberToRemove != null) {
        AlertDialog(
            onDismissRequest = { showDeleteMemberDialog = false },
            title = { Text("Hapus Anggota") },
            text = { Text("Apakah Anda yakin ingin menghapus ${selectedMemberToRemove?.name}?") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9534F)),
                    onClick = {
                        selectedMemberToRemove?.let { viewModel.removeMember(it) }
                        showDeleteMemberDialog = false
                        selectedMemberToRemove = null
                    }
                ) { Text("Ya, Hapus", color = Color.White) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteMemberDialog = false }) { Text("Batal") }
            }
        )
    }

    // Dialog hapus dokumentasi
    if (showDeleteDocDialog && selectedDocToRemove != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDocDialog = false },
            title = { Text("Hapus Foto") },
            text = { Text("Apakah Anda yakin ingin menghapus foto dokumentasi ini?") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9534F)),
                    onClick = {
                        selectedDocToRemove?.let { viewModel.removeDocumentation(it) }
                        showDeleteDocDialog = false
                        selectedDocToRemove = null
                    }
                ) { Text("Ya, Hapus", color = Color.White) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDocDialog = false }) { Text("Batal") }
            }
        )
    }
    if (showEditSheet) {

        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                showEditSheet = false
            },
            containerColor = DrawerBackground
        ) {
            EditTripBottomSheet(
                currentTitle = currentTitle,
                currentDescription = currentDescription,
                currentMentor = uiState.mentor?.name ?: "",
                currentCoordinator = uiState.coordinator?.name ?: "",
                onClose = {
                    showEditSheet = false
                }
            )
        }
    }
}