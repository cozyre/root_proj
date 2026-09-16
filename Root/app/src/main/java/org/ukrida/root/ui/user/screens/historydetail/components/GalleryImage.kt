package org.ukrida.root.ui.user.screens.historydetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.ukrida.root.data.model.GroupImage
import org.ukrida.root.data.remote.ApiUrl
import coil.compose.AsyncImage
@Composable
fun GalleryImage(
    image: GroupImage,
    onClick: () -> Unit = {}
) {

    val imageUrl = ApiUrl.normalize(image.imageUrl)

    AsyncImage(
        model = imageUrl,
        contentDescription = image.caption,
        modifier = Modifier
            .fillMaxWidth()
            .height(113.dp)
            .clip(RoundedCornerShape(14.dp)),
        contentScale = ContentScale.Crop
    )
}