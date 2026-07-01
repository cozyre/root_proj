package org.ukrida.root.ui.admin.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.ukrida.root.R
import org.ukrida.root.ui.theme.*
import org.ukrida.root.data.model.Group
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment

@Composable
fun FinishedTripCard(
    group: Group,
    onClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(DrawerBackground)
            .clickable { onClick() }
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // TITLE
            Text(
                text = group.name,
                style = MaterialTheme.typography.titleLarge,
                color = H1Color
            )

            Spacer(modifier = Modifier.height(12.dp))

            // DESCRIPTION
            Text(
                text = group.description ?: "-",
                style = MaterialTheme.typography.bodyLarge,
                color = BodyColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            // LOCATION (opsional tapi bagus ditampilkan)
            if (!group.location.isNullOrBlank()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = BodyColor,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(6.dp)
                    )

                    Text(
                        text = group.location,
                        style = MaterialTheme.typography.bodyMedium,
                        color = BodyColor
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            // DURATION
            val start = group.startDate ?: "-"
            val end = group.endDate ?: "-"

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier.align(
                        Alignment.CenterEnd
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint = H1Color,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(6.dp)
                    )

                    Text(
                        text = "${group.startDate ?: "-"} - ${group.endDate ?: "-"}",
                        style = MaterialTheme.typography.titleMedium,
                        color = H1Color
                    )
                }
            }
        }
    }
}