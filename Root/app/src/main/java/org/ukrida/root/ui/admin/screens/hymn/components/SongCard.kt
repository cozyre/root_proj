package org.ukrida.root.ui.admin.screens.hymn.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color

@Composable
fun SongCard(
    title: String,
    author: String?,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DrawerBackground)
            .clickable { onClick() }
            .padding(16.dp)
    ) {

        Text(
            text = title,
            color = H1Color,
            style = MaterialTheme.typography.titleMedium
        )

        author?.let {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = it,
                color = BodyColor,
                fontSize = 12.sp
            )
        }
    }
}