package org.ukrida.root.ui.user.screens.history.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.navigation.NavController
import org.ukrida.root.data.model.Group
import org.ukrida.root.ui.user.screens.promisedland.components.HistoryCard

@Composable
fun HistorySection(
    navController: NavController,
    historyGroups: List<Group>?,
    onHistoryClick: ((Int) -> Unit)? = null
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Breadcrumb(navController = navController)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "YOUR TRIP HISTORY",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFFE8D8C9)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi vel luctus justo. Etiam aliquet tempus felis eget imperdiet.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = .8f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        historyGroups?.forEach { group ->
            HistoryCard(
                group = group,
                onClick = {
                    onHistoryClick?.invoke(group.id)
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
        }

    }

}