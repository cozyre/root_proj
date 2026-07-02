package org.ukrida.root.ui.user.screens.group.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.model.GroupDetail
import org.ukrida.root.data.model.GroupWithDetails
import org.ukrida.root.utils.GroupStatus

@Composable
fun GroupSection(
    groups: List<GroupWithDetails>?,
    onGroupClick: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            text = "TAKE A TOUR!",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFFE8D8C9)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Explore every sacred destination and discover the stories behind each place.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = .8f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        groups?.forEach { group ->
            GroupCard(
                group = group,
                onClick = {
                    println("DEBUG GroupCard: group.id=${group.id}, statusJoin=${group.statusJoin}")
                    if (group.statusJoin == GroupStatus.APPROVED) {
                        onGroupClick(group.id)
                    }
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}