package org.ukrida.root.ui.Public.screens.members.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import org.ukrida.root.data.model.MemberDetail

@Composable
fun MemberDetailHeader(
    member: MemberDetail
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (member.profilePhotoUrl.isNullOrBlank()) {

            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(
                        Color(0xFFA67D5B),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )

            }

        } else {

            AsyncImage(
                model = member.profilePhotoUrl,
                contentDescription = member.fullName,
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = member.fullName,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE8D8C9)
        )

        Text(
            text = "@${member.username}",
            color = Color.White.copy(alpha = .7f)
        )

    }

}