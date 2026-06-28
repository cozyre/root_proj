package org.ukrida.root.ui.Public.screens.group.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.ukrida.root.R
import org.ukrida.root.data.model.Group
import org.ukrida.root.utils.GroupStatus

@Composable
fun GroupCard(
    group: Group,
    onClick: () -> Unit = {}
) {
    val enabled = group.statusJoin == GroupStatus.APPROVED
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = enabled
            ) {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF9A775B)
        ),
        shape = RoundedCornerShape(18.dp)
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.pyramid),
                contentDescription = group.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .alpha(
                        if (enabled) 1f else 0.45f
                    )
                    .clip(
                        RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )
            StatusBadge(
                status = group.statusJoin ?: "Pending",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(14.dp)
            )
        }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = group.name,
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFE8D8C9)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = group.description ?: "",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = .85f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${group.startDate ?: "-"} - ${group.endDate ?: "-"}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFE8D8C9),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}