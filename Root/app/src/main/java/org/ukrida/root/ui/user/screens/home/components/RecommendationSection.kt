package org.ukrida.root.ui.user.screens.home.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.Group

@Composable
fun RecommendationSection(
    groups: List<Group>?,
    coverImages: Map<Int, String?>,
    onTripClick: (Int) -> Unit = {}
) {

    var showAll by remember {
        mutableStateOf(false)
    }

    val displayedGroups =
        if (showAll) {
            groups ?: emptyList()
        } else {
            groups?.take(2) ?: emptyList()
        }

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

        displayedGroups.forEach { group ->

            Log.d(
                "HOME_IMAGE",
                "groupId=${group.id}, imageUrl=${coverImages[group.id]}"
            )

            RecommendationCard(
                group = group,
                imageUrl = coverImages[group.id],
                onClick = {
                    onTripClick(group.id)
                }
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )
        }

        if ((groups?.size ?: 0) > 2) {

            Text(
                text = if (showAll) "See Less" else "See More",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFFE8D8C9),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End)
                    .clickable {
                        showAll = !showAll
                    }
            )
        }
    }
}