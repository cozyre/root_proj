package org.ukrida.root.ui.user.screens.groupmenus.gallery.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.ukrida.root.R
import org.ukrida.root.data.model.GroupImage

@Composable
fun GalleryImageCard(
    image: GroupImage,
    onClick: (GroupImage) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(116.dp)
            .clickable {
                onClick(image)
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        if (image.imageUrl.isBlank()) {
            // TODO Backend Integration
            // Replace placeholder with image.imageUrl from API.
            Image(
                painter = painterResource(R.drawable.pyramid),
                contentDescription = image.caption,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = image.imageUrl,
                contentDescription = image.caption,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}