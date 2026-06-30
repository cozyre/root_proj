package org.ukrida.root.ui.Public.screens.historydetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.ukrida.root.R
import org.ukrida.root.data.model.GroupImage

@Composable
fun GalleryImage(
    image: GroupImage,
    onClick: () -> Unit = {}
) {

    Image(
        //Kalau backend udah mau disambungin
//        AsyncImage(
//            model = image.imageUrl,
//            contentDescription = image.caption,
//            ...
//        )
        painter = painterResource(R.drawable.pyramid),
        contentDescription = image.caption,
        modifier = Modifier
            .fillMaxWidth()
            .height(113.dp)
            .clip(RoundedCornerShape(14.dp)),
        contentScale = ContentScale.Crop
    )

}