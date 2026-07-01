package org.ukrida.root.ui.admin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ukrida.root.ui.theme.ApproveButton
import org.ukrida.root.ui.theme.ApproveText
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.RejectButton
import org.ukrida.root.ui.theme.RejectText

@Composable
fun ApprovalStatusCard(
    userName: String,
    groupName: String,
    status: String
) {

    val backgroundColor = when (status.lowercase()) {
        "approved" -> ApproveButton
        "rejected" -> RejectButton
        else -> DrawerBackground
    }

    val textColor = when (status.lowercase()) {
        "approved" -> ApproveText
        "rejected" -> RejectText
        else -> H1Color
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

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

        Box(
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(50.dp)
                )
                .padding(
                    horizontal = 12.dp,
                    vertical = 6.dp
                )
        ) {

            Text(
                text = status.uppercase(),
                color = textColor,
                fontSize = 10.sp
            )
        }
    }
}