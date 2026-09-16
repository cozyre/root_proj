package org.ukrida.root.ui.admin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import org.ukrida.root.ui.theme.*

@Composable
fun ApprovalCard(
    userName: String,
    groupName: String,
    profilePhotoUrl: String?,
    enabled: Boolean = true,
    onApprove: () -> Unit = {},
    onReject: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (profilePhotoUrl.isNullOrBlank()) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(DrawerBackground),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = BodyColor,
                    modifier = Modifier.size(24.dp)
                )
            }

        } else {

            AsyncImage(
                model = profilePhotoUrl,
                contentDescription = userName,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = userName,
                color = H1Color
            )

            Text(
                text = "Want to join $groupName",
                color = BodyColor
            )
        }

        Button(
            onClick = onApprove,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = ApproveButton
            ),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .height(30.dp)
                .width(60.dp),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = "APPROVE",
                color = ApproveText,
                fontSize = 8.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onReject,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = RejectButton
            ),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .height(30.dp)
                .width(60.dp),
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(
                text = "REJECT",
                color = RejectText,
                fontSize = 8.sp
            )
        }
    }
}