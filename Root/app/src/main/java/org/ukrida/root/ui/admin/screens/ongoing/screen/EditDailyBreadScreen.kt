package org.ukrida.root.ui.admin.screens.ongoing.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.ukrida.root.ui.admin.screens.ongoing.components.TimelineBar
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.EditDailyBreadViewModel
import org.ukrida.root.ui.theme.*

@Composable
fun EditDailyBreadScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: EditDailyBreadViewModel
) {
    val dailyBreadList by viewModel.dailyBreadList.collectAsState()
    val selectedDay    by viewModel.selectedDay.collectAsState()
    val availableDays  by viewModel.availableDays.collectAsState()

    // Ambil item untuk hari yang dipilih
    val currentItem = dailyBreadList.find { it.day == selectedDay }

    var showDayDropdown by remember { mutableStateOf(false) }

    // State edit title — aktif saat pensil ditekan
    var isEditingTitle by remember { mutableStateOf(false) }
    val titleFocusRequester = remember { FocusRequester() }

    // Saat mode edit title aktif, langsung fokus ke TextField
    LaunchedEffect(isEditingTitle) {
        if (isEditingTitle) titleFocusRequester.requestFocus()
    }

    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            OnGoingTopBar(
                title = "EDIT DAILY BREAD",
                onMenuClick = onMenuClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            // ── < Back ──────────────────────────────────────────────────────
            TextButton(
                onClick = { navController.popBackStack() },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(text = "< Back", color = TitleColor, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ── Timeline Bar ─────────────────────────────────────────────────
            TimelineBar(onStageClick = { stage ->
                when (stage) {
                    "General Information" ->
                        navController.navigate("ongoing_detail/${viewModel.tripId}")
                    "Itinerary" ->
                        navController.navigate("edit_itinerary/${viewModel.tripId}")
                    "Hymn for Him" ->
                        navController.navigate("edit_songs/${viewModel.tripId}")
                    // "Daily Bread" = halaman ini
                }
            })

            Spacer(modifier = Modifier.height(20.dp))

            // ── DAY X Dropdown ───────────────────────────────────────────────
            Box {
                OutlinedButton(
                    onClick = { showDayDropdown = true },
                    modifier = Modifier
                        .width(110.dp)
                        .height(30.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = LogoutButton,
                        contentColor = Color.White
                    ),
                    border = null,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = "DAY $selectedDay",
                        style = MaterialTheme.typography.titleLarge,
                        color = H1Color,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = H1Color
                    )
                }

                DropdownMenu(
                    expanded = showDayDropdown,
                    onDismissRequest = { showDayDropdown = false },
                    containerColor = DrawerBackground
                ) {
                    availableDays.forEach { day ->
                        DropdownMenuItem(
                            text = { Text("DAY $day", color = H1Color) },
                            onClick = {
                                viewModel.selectDay(day)
                                isEditingTitle = false
                                showDayDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── Label DAILY BREAD ────────────────────────────────────────────
            Text(
                text = "DAILY BREAD",
                style = MaterialTheme.typography.titleLarge,
                color = H1Color
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Title + Tombol Edit (Pensil) ─────────────────────────────────
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isEditingTitle) {
                    // Mode edit: TextField inline
                    OutlinedTextField(
                        value = currentItem?.title ?: "",
                        onValueChange = { viewModel.updateTitle(selectedDay, it) },
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(titleFocusRequester),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = H1Color,
                            unfocusedTextColor = H1Color,
                            focusedBorderColor = TitleColor,
                            unfocusedBorderColor = Color(0xFF6B5C4E),
                            focusedContainerColor = Color(0xFF2A2018),
                            unfocusedContainerColor = Color(0xFF2A2018)
                        ),
                        textStyle = MaterialTheme.typography.titleMedium.copy(
                            color = H1Color,
                            fontSize = 18.sp
                        )
                    )
                } else {
                    // Mode tampil: Text biasa
                    Text(
                        text = if (currentItem?.title.isNullOrEmpty()) "TITLE HERE"
                        else currentItem!!.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = H1Color,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Tombol pensil — toggle edit mode
                IconButton(
                    onClick = { isEditingTitle = !isEditingTitle },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit judul",
                        tint = TitleColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Content / Textarea ───────────────────────────────────────────
            OutlinedTextField(
                value = currentItem?.content ?: "",
                onValueChange = { viewModel.updateContent(selectedDay, it) },
                placeholder = {
                    Text(
                        text = "Tulis renungan harian di sini...",
                        color = Color(0xFF6B5C4E),
                        fontSize = 14.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 300.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = BodyColor,
                    unfocusedTextColor = BodyColor,
                    focusedBorderColor = TitleColor,
                    unfocusedBorderColor = Color(0xFF6B5C4E),
                    focusedContainerColor = Color(0xFF2A2018),
                    unfocusedContainerColor = Color(0xFF2A2018)
                ),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── SUBMIT ───────────────────────────────────────────────────────
            Button(
                onClick = {
                    viewModel.submit()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LogoutButton)
            ) {
                Text(
                    text = "SUBMIT",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}