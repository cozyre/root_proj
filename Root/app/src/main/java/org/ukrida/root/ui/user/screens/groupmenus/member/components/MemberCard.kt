package org.ukrida.root.ui.user.screens.groupmenus.member.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.ukrida.root.data.model.Member

@Composable
fun MemberCard(
    member: Member,
    onClick: (Member) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clickable {
                onClick(member)
            }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (member.profilePhotoUrl.isNullOrBlank()) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        Color(0xFFA67D5B),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color(0xFF2A2522)
                )
            }
        } else {
            AsyncImage(
                model = member.profilePhotoUrl,
                contentDescription = member.fullName,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = member.fullName,
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFFE8D8C9)
        )
    }
}