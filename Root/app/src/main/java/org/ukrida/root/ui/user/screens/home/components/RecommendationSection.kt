package org.ukrida.root.ui.user.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.Group
import org.ukrida.root.utils.Resource

@Composable
fun RecommendationSection(
    groups: List<Group>?,
    onTripClick: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = "RECOMMENDATION TRIP",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFFE8D8C9)
        )
        Spacer(modifier = Modifier.height(18.dp))
        groups?.forEach { group ->
            RecommendationCard(
                group = group,
                onClick = {
                    onTripClick(group.id)
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}