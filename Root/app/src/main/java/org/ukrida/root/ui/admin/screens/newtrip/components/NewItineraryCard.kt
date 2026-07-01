package org.ukrida.root.ui.admin.screens.newtrip.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.admin.screens.trip.viewmodel.NewItineraryItem

@Composable
fun NewItineraryCard(
    item: NewItineraryItem,
    onStartTimeChange: (String) -> Unit,
    onEndTimeChange: (String) -> Unit,
    onActivityChange: (String) -> Unit,
    onDelete: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp)
        ) {

            Row(
                modifier = Modifier.padding(12.dp)
            ) {

                Column {

                    OutlinedTextField(
                        value = item.startTime,
                        onValueChange = onStartTimeChange,
                        modifier = Modifier.width(90.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = item.endTime,
                        onValueChange = onEndTimeChange,
                        modifier = Modifier.width(90.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                OutlinedTextField(
                    value = item.activity,
                    onValueChange = onActivityChange,
                    modifier = Modifier
                        .weight(1f)
                        .height(120.dp),
                    placeholder = {
                        Text("type here")
                    }
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = onDelete,
            modifier = Modifier
                .background(
                    Color.Red,
                    RoundedCornerShape(8.dp)
                )
        ) {

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}