package org.ukrida.root.ui.user.screens.groupmenus.itinerary.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.screens.groupmenus.itinerary.components.ActivityRow
import org.ukrida.root.ui.user.screens.groupmenus.itinerary.components.ActivityTableHeader
import org.ukrida.root.ui.user.screens.groupmenus.itinerary.components.DateNavigator
import org.ukrida.root.ui.user.screens.groupmenus.itinerary.components.ItineraryHeader
import org.ukrida.root.ui.user.screens.groupmenus.itinerary.viewmodel.ItineraryViewModel
import org.ukrida.root.utils.Resource

@Composable
fun ItineraryScreen(
    viewModel: ItineraryViewModel,
    navController: NavHostController,
    groupId: Int
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(groupId) {
        viewModel.load(groupId)
    }

    Scaffold(
        containerColor = Color(0xFF2A2522),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF2A2522))
        ) {
            when (val itineraryRes = uiState.itinerary) {
                is Resource.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
                is Resource.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = itineraryRes.message, color = Color.White)
                    }
                }
                is Resource.Success -> {
                    val data = itineraryRes.data
                    if (data != null) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                        ) {
                            Spacer(modifier = Modifier.height(24.dp))
                            ItineraryHeader(
                                date = data.date,
                                description = data.desc
                            )
                            ActivityTableHeader()
                        }
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 20.dp)
                        ) {
                            items(data.items) { item ->
                                ActivityRow(item)
                            }
                        }

                        // Handle DateNavigator using the dates list from state
                        val datesRes = uiState.dates
                        if (datesRes is Resource.Success) {
                            val datesList = datesRes.data
                            val selectedIndex = uiState.selectedIndex
                            if (datesList.isNotEmpty() && selectedIndex in datesList.indices) {
                                DateNavigator(
                                    date = datesList[selectedIndex].date,
                                    canPrevious = selectedIndex > 0,
                                    canNext = selectedIndex < datesList.lastIndex,
                                    onPrevious = {
                                        viewModel.previousDay(groupId)
                                    },
                                    onNext = {
                                        viewModel.nextDay(groupId)
                                    }
                                )
                            }
                        }
                    } else {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "No itinerary available", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
