package org.ukrida.root.ui.Public.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.ukrida.root.data.model.Profile

@Composable
fun ProfileHeader(
    profile: Profile,
    onChangePhotoClick: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (profile.profilePhotoUrl.isNullOrBlank()) {

            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        Color(0xFFA67D5B),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color(0xFF2A2522),
                    modifier = Modifier.size(80.dp)
                )

            }

        } else {

            AsyncImage(
                model = profile.profilePhotoUrl,
                contentDescription = profile.fullName,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

        }

        Spacer(
            modifier = Modifier.width(20.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Text(
                text = profile.fullName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = profile.username,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = .7f)
            )

            Button(
                // TODO Backend Integration
                // Open Android Photo Picker
                // Upload selected image to backend
                // Refresh profile after successful upload
                onClick = onChangePhotoClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7A8A4A)
                ),
                shape = CircleShape
            ) {

                Text(
                    text = "Change Profile",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium
                )

            }

        }

    }

}