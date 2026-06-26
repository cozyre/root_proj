package org.ukrida.root.ui.admin.screens.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.theme.*

@Composable
fun AddSongCard(
    onSeeMoreClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = DrawerBackground,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(20.dp)
    ) {

        Text(
            text = "ADD SONGS",
            style = MaterialTheme.typography.titleLarge,
            color = H1Color
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi vel luctus justo. Etiam aliquet tempus felis eget imperdiet.",
            style = MaterialTheme.typography.bodyLarge,
            color = BodyColor
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {

            Text(
                text = "See More",
                color = BodyColor,
                modifier = Modifier.clickable {
                    onSeeMoreClick()
                }
            )
        }
    }
}