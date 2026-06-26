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
import org.ukrida.root.ui.admin.navigation.Screen
import org.ukrida.root.ui.admin.screens.ongoing.components.*
import org.ukrida.root.ui.admin.screens.ongoing.model.Documentation
import org.ukrida.root.ui.admin.screens.ongoing.model.Member
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.OnGoingDetailViewModel
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.DrawerBackground

@Composable
fun OnGoingDetailScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: OnGoingDetailViewModel,
    modifier: Modifier = Modifier
) {
    val tripData by viewModel.tripState.collectAsState()

    // Ambil properti dari tripData dengan nilai cadangan (fallback) jika data sedang memuat
    val currentTitle = tripData?.title ?: "Loading..."
    val currentDescription = tripData?.description ?: "Memuat deskripsi..."
    val currentDateRange = tripData?.dateRange ?: "DD - DD MM YYYY"

    // --- STATE MANAGEMENT ---
    var showDeleteMemberDialog by remember { mutableStateOf(false) }
    var selectedMemberToRemove by remember { mutableStateOf<Member?>(null) }

    var showDeleteDocDialog by remember { mutableStateOf(false) }
    var selectedDocToRemove by remember { mutableStateOf<Documentation?>(null) }

    // Dummy List (Saat data MySQL siap, ganti dengan data dari ViewModel)
    var memberList by remember {
        mutableStateOf(
            listOf(
                Member("1", "Mike", android.R.drawable.ic_menu_gallery),
                Member("2", "Josh", android.R.drawable.ic_menu_gallery),
                Member("3", "Austin", android.R.drawable.ic_menu_gallery)
            )
        )
    }

    var docList by remember {
        mutableStateOf(
            listOf(
                Documentation("1", android.R.drawable.ic_menu_gallery),
                Documentation("2", android.R.drawable.ic_menu_gallery)
            )
        )
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> /* Aksi update URI gambar utama ke database MySQL */ }
    )

    Scaffold(
        containerColor = BackgroundDark, // Gunakan BackgroundDark aplikasi Anda langsung di Scaffold
        topBar = {
            // Memindahkan TopBar ke slot parameter resmi Scaffold agar posisinya mantap di atas
            OnGoingTopBar(
                title = "EDIT TOUR",
                onMenuClick = onMenuClick
            )
        }
    ) { innerPadding ->

        // Cukup gunakan SATU Column utama untuk membungkus seluruh konten scrollable
        Column(
            modifier = modifier
                .padding(innerPadding) // innerPadding wajib dipasang di layout terluar dalam Scaffold
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // 1. Breadcrumb (Poin 1)
            Text(
                text = "$currentTitle > Ongoing Trip > Detail",
                color = DrawerBackground,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.popBackStack() }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Timeline Bar Component (Poin 2)
            TimelineBar(onStageClick = { stage ->
                when (stage) {
                    "Hymn for Him" ->
                        navController.navigate("edit_songs/${viewModel.tripId}")
                    "Itinerary" ->
                        navController.navigate("edit_itinerary/${viewModel.tripId}")
                    "Daily Bread" ->
                        navController.navigate("daily_bread/${viewModel.tripId}")
                    // "Hymn for Him" = halaman ini, tidak di-navigate ulang
                }
            })

            Spacer(modifier = Modifier.height(32.dp))

            // 3. Trip Header Component (Poin 3)
            TripHeader(
                title = currentTitle,
                description = currentDescription,
                dateRange = currentDateRange,
                onEditClick = { navController.navigate("EditSongsScreens") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Image Placeholder Component
            ImagePlaceholder(
                imageUrl = tripData?.imageUrl,
                onClick = {
                    galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Section Group Member
            Text(text = "GROUP MEMBER", color = Color(0xFFD4C5B9), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            val gridRows = (memberList.size + 2) / 3  // hitung berapa baris yang dibutuhkan
            val gridHeight = (gridRows * 100).dp       // sesuaikan 100.dp dengan tinggi 1 item MemberGridItem

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(gridHeight)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),          // 3 kolom, otomatis wrap ke baris baru
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    userScrollEnabled = false              // scroll sudah ditangani Column luar
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

            Spacer(modifier = Modifier.height(16.dp))

            // 6. Section Dokumentasi
            Box(modifier = Modifier.height(240.dp)) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    userScrollEnabled = false
                ) {
                    items(docList) { doc ->
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

            Text(
                text = "See More",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clickable { /* See More Dokumentasi */ }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // --- ALERTLOG CONFIRMATION (Poin 5 - Dua Kali Konfirmasi Hapus) ---
    if (showDeleteMemberDialog && selectedMemberToRemove != null) {
        AlertDialog(
            onDismissRequest = { showDeleteMemberDialog = false },
            title = { Text(text = "Hapus Anggota") },
            text = { Text(text = "Apakah Anda yakin ingin menghapus ${selectedMemberToRemove?.name}?") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9534F)),
                    onClick = {
                        memberList = memberList.filter { it.id != selectedMemberToRemove?.id }
                        showDeleteMemberDialog = false
                        selectedMemberToRemove = null
                    }
                ) {
                    Text("Ya, Hapus", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteMemberDialog = false }) { Text("Batal") }
            }
        )
    }

    if (showDeleteDocDialog && selectedDocToRemove != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDocDialog = false },
            title = { Text(text = "Hapus Foto") },
            text = { Text(text = "Apakah Anda yakin ingin menghapus foto dokumentasi ini?") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9534F)),
                    onClick = {
                        docList = docList.filter { it.id != selectedDocToRemove?.id }
                        showDeleteDocDialog = false
                        selectedDocToRemove = null
                    }
                ) {
                    Text("Ya, Hapus", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDocDialog = false }) { Text("Batal") }
            }
        )
    }
}