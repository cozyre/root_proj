package org.ukrida.root.ui.user.screens.order.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.ukrida.root.R
import org.ukrida.root.data.model.Group

@Composable
fun OrderHeader(
    group: Group
) {

    Column {

        Image(
            painter = painterResource(R.drawable.pyramid),
            contentDescription = group.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(18.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = group.name,
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFFE8D8C9)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = group.description ?: "",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = .85f)
        )

    }

}
