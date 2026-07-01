package org.ukrida.root.ui.admin.screens.finished.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import org.ukrida.root.ui.admin.components.MemberGridItem
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.navigation.Screen
import org.ukrida.root.ui.admin.screens.finished.viewmodel.FinishedTripDetailViewModel
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.DrawerBackground

@Composable
fun FinishedTripDetailScreen(
    navController: NavController,
    onMenuClick: () -> Unit,
    viewModel: FinishedTripDetailViewModel
) {

    var showAllMembers by remember { mutableStateOf(false) }
    var showAllDocs by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()

    val tripData = uiState.tripState

    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            TopBar(
                title = "FINISHED TRIP",
                onMenuClick = onMenuClick
            )
        }
    ) { innerPadding ->

        when {

            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFFC49A6C)
                    )
                }
            }

            uiState.errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.errorMessage!!,
                        color = Color.White
                    )
                }
            }

            else -> {

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .verticalScroll(
                            rememberScrollState()
                        )
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Promised Land > Finished Trip > ${tripData?.title}",
                        color = DrawerBackground,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.FinishedTrip.route) {
                                    popUpTo(Screen.FinishedTrip.route)
                                    launchSingleTop = true
                                }
                            }

                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = tripData?.title ?: "",
                        color = Color(0xFFD4C5B9),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = tripData?.description ?: "",
                        color = Color.White,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = tripData?.dateRange ?: "",
                        color = Color(0xFFD4C5B9),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    ImagePlaceholder(
                        imageUrl = tripData?.imageUrl,
                        onClick = {}
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "GROUP MEMBER",
                        color = Color(0xFFD4C5B9),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    val members =
                        if (showAllMembers)
                            uiState.members
                        else
                            uiState.members.take(3)

                    val rows =
                        (members.size + 2) / 3

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((rows * 100).dp)
                    ) {

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            userScrollEnabled = false,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            items(members) { member ->

                                MemberGridItem(
                                    member = member,
                                    showRemoveButton = false
                                )
                            }
                        }
                    }

                    Text(
                        text =
                            if (showAllMembers)
                                "See Less"
                            else
                                "See More",
                        color = Color.White,
                        modifier = Modifier.clickable {
                            showAllMembers =
                                !showAllMembers
                        }
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "GALLERY",
                        color = Color(0xFFD4C5B9),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    val docs =
                        if (showAllDocs)
                            uiState.documentations
                        else
                            uiState.documentations.take(6)

                    val docRows =
                        (docs.size + 1) / 2

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((docRows * 120).dp)
                    ) {

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            userScrollEnabled = false,
                            horizontalArrangement =
                                Arrangement.spacedBy(12.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(12.dp)
                        ) {

                            items(docs) { doc ->

                                DocumentationGridItem(
                                    documentation = doc,
                                    showRemoveButton = false
                                )
                            }
                        }
                    }

                    if (uiState.documentations.size > 6) {

                        Text(
                            text =
                                if (showAllDocs)
                                    "See Less"
                                else
                                    "See More",
                            color = Color.White,
                            modifier = Modifier.clickable {
                                showAllDocs =
                                    !showAllDocs
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}