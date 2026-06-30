package org.ukrida.root.ui.user.screens.gallery.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.GroupImage

@Composable
fun GalleryGrid(
    images: List<GroupImage>,
    modifier: Modifier = Modifier,
    onImageClick: (GroupImage) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 20.dp,
            bottom = 12.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = images,
            key = { it.id }
        ) { image ->
            GalleryImageCard(
                image = image,
                onClick = onImageClick
            )
        }
    }
    Spacer(
        modifier = Modifier.height(10.dp)
    )
    UploadButton(
        onUploadClick = {
            // TODO Backend Integration
            // Open Android Photo Picker.
        }
    )
}