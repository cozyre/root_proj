package org.ukrida.root.ui.Public.screens.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.ukrida.root.data.model.Group

@Composable
fun OrganizerSection(
    group: Group
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO Backend Integration
        // Replace dummy organizer data with API response.
        // Example:
        // OrganizerItem(
        //     name = group.coordinator.name,
        //     photoUrl = group.coordinator.photo
        // )
        Text(
            text = "Organizer",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFFE8D8C9)
        )
        Spacer(modifier = Modifier.height(24.dp))
        OrganizerItem(
            "COORDINATOR NAME",
            photoUrl = null
        )
        Spacer(modifier = Modifier.height(18.dp))
        OrganizerItem(
            "LEADER NAME",
            photoUrl = null
        )
    }
}

@Composable
private fun OrganizerItem(
    name: String,
    photoUrl: String?
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (photoUrl.isNullOrBlank()) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = Color(0xFF9A775B),
                modifier = Modifier.size(44.dp)
            )
        }
        else {
        AsyncImage(
            model = photoUrl,
            contentDescription = name,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFFE8D8C9)
        )
    }

}