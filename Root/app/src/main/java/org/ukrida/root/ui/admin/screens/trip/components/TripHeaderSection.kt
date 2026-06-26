package org.ukrida.root.ui.admin.screens.trip.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.theme.*
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun TripHeaderSection(
    onNewTripClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "MANAGE TRIP HERE",
            style = MaterialTheme.typography.headlineSmall,
            color = H1Color
        )

        Text(
            text = "Create, manage, and monitor all tour activities.",
            style = MaterialTheme.typography.bodyMedium,
            color = BodyColor,
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = onNewTripClick,
            modifier = Modifier
                .padding(top = 20.dp)
                .height(32.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MainButton
            )
        ) {

            Text(
                text = "NEW TRIP",
                color = H1Color
            )
        }
    }
}