package org.ukrida.root.ui.user.screens.historydetail.components

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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.GroupImage

@Composable
fun GallerySection(
    gallery: List<GroupImage>,
    onImageClick: (GroupImage) -> Unit = {},
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    val displayedGallery =
        if (expanded) gallery
        else gallery.take(6)

    val columns = 2
    val imageHeight = 120.dp
    val spacing = 12.dp

    val rows = (displayedGallery.size + columns - 1) / columns

    val gridHeight =
        imageHeight * rows +
                spacing * (rows - 1).coerceAtLeast(0)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "GALLERY",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFFE8D8C9),
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(gridHeight),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            userScrollEnabled = false
        ) {
            items(displayedGallery) { image ->
                GalleryImage(
                    image = image,
                    onClick = {
                        onImageClick(image)
                    }
                )
            }
        }
        if (gallery.size > 6) {
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