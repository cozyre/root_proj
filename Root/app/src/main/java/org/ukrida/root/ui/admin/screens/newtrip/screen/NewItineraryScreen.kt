package org.ukrida.root.ui.admin.screens.newtrip.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.admin.components.TimelineBar
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.screens.trip.viewmodel.NewItineraryItem
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MainButton

@Composable
fun NewItineraryScreen(
    onMenuClick: () -> Unit,
    onBackClick: () -> Unit
) {

    var itineraries by remember {
        mutableStateOf(
            listOf(
                NewItineraryItem(
                    id = 1,
                    startTime = "",
                    endTime = "",
                    activity = ""
                )
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        TopBar(
            title = "NEW ITINERARY",
            onMenuClick = onMenuClick
        )

        TimelineBar(
            onStageClick = {},
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "ITINERARY",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            itemsIndexed(itineraries) { index, item ->

                NewItineraryCard(
                    item = item,

                    onStartTimeChange = { value ->

                        itineraries =
                            itineraries.toMutableList().apply {
                                this[index] =
                                    this[index].copy(
                                        startTime = value
                                    )
                            }
                    },

                    onEndTimeChange = { value ->

                        itineraries =
                            itineraries.toMutableList().apply {
                                this[index] =
                                    this[index].copy(
                                        endTime = value
                                    )
                            }
                    },

                    onActivityChange = { value ->

                        itineraries =
                            itineraries.toMutableList().apply {
                                this[index] =
                                    this[index].copy(
                                        activity = value
                                    )
                            }
                    },

                    onDelete = {

                        if (itineraries.size > 1) {

                            itineraries =
                                itineraries.toMutableList().apply {
                                    removeAt(index)
                                }
                        }
                    }
                )
            }
        }

        Column(
            modifier = Modifier.padding(24.dp)
        ) {

            Button(
                onClick = {

                    itineraries =
                        itineraries + NewItineraryItem(
                            id = 1,
                            startTime = "",
                            endTime = "",
                            activity = ""
                        )
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("ADD ACTIVITY")
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Button(
                onClick = {
                    // nanti ke Hymn For Him
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainButton
                )
            ) {

                Text(
                    text = "NEXT",
                    color = H1Color
                )
            }
        }
    }
}