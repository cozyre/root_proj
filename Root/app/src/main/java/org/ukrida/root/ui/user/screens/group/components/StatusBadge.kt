package org.ukrida.root.ui.user.screens.group.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.ukrida.root.utils.GroupStatus

@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {

    val backgroundColor = when (status) {
        GroupStatus.APPROVED -> Color(0xFF7A8A4A)
        GroupStatus.REJECTED -> Color(0xFFC45A5A)
        GroupStatus.PENDING -> Color(0xFF5C6BC0)
        else -> Color.Gray
    }

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(50)
            )
            .padding(
                horizontal = 14.dp,
                vertical = 6.dp
            )
    ) {

        Text(
            text = status.uppercase(),
            color = Color.White,
            style = MaterialTheme.typography.labelMedium
        )

    }

}