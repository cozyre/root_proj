package org.ukrida.root.ui.admin.screens.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.H1Color

@Composable
fun RecentTripSection(
    onSeeMoreClick: () -> Unit = {}
) {

    Column {

        Text(
            text = "RECENT ONGOING TRIP",
            style = MaterialTheme.typography.titleLarge,
            color = H1Color
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi vel luctus justo.",
            style = MaterialTheme.typography.bodyLarge,
            color = BodyColor
        )

        Spacer(modifier = Modifier.height(20.dp))

        TripCard()

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {

            Text(
                text = "See More",
                modifier = Modifier.clickable {
                    onSeeMoreClick()
                },
                color = BodyColor
            )
        }
    }
}