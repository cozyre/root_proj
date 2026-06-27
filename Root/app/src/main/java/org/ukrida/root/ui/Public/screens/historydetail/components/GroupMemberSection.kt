package org.ukrida.root.ui.Public.screens.historydetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.Member

@Composable
fun GroupMemberSection(
    members: List<Member>,
    onMemberClick: (Member) -> Unit = {},
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    val displayedMembers =
        if (expanded) members
        else members.take(6)

    val columns = 3
    val itemHeight = 110.dp
    val verticalSpacing = 20.dp

    val rows = (displayedMembers.size + 2) / columns

    val gridHeight =
        itemHeight * rows +
                20.dp * (rows - 1).coerceAtLeast(0)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "GROUP MEMBER",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .height(gridHeight),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            userScrollEnabled = false
        ) {
            items(displayedMembers) { member ->
                MemberAvatar(
                    member = member,
                    onClick = {
                        onMemberClick(member)
                    }
                )
            }
        }
        if (members.size > 6) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = if (expanded) "See Less" else "See More",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        expanded = !expanded
                    },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}