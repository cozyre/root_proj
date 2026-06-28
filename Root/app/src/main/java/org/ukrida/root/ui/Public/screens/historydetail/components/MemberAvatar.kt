package org.ukrida.root.ui.Public.screens.historydetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.ukrida.root.R
import org.ukrida.root.data.model.Member

@Composable
fun MemberAvatar(
    member: Member,
    onClick: () -> Unit = {}
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick()
        }.width(80.dp)
    ) {
        if (member.profilePhotoUrl.isNullOrBlank()) {

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = member.fullName,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                tint = Color.LightGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = member.firstName,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                maxLines = 1
            )

        } else {

            AsyncImage(
                model = member.profilePhotoUrl,
                contentDescription = member.fullName,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = member.firstName,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                maxLines = 1
            )
        }
    }

}