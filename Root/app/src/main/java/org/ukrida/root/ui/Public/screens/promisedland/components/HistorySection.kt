package org.ukrida.root.ui.Public.screens.promisedland.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.Group

@Composable
fun HistorySection(
    historyGroups: List<Group>,
    onSeeMoreClick: () -> Unit,
    onHistoryClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            text = "YOUR TRIP HISTORY",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFFE8D8C9)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Your history trip you have been through before.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = .8f)
        )
        if (historyGroups.isEmpty()) {
            Text(
                text = "No trips available.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
        else {
            historyGroups
                .take(2)
                .forEach { group ->
                    Spacer(modifier = Modifier.height(20.dp))
                    HistoryCard(
                        group = group,
                        onClick = {
                            onHistoryClick?.invoke(group.id)
                        }
                    )
                }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "See More",
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    onSeeMoreClick()
                },
            color = Color.White,
            textDecoration = TextDecoration.Underline
        )
    }
}