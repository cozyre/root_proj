package org.ukrida.root.ui.user.screens.groupmenus.dashboard.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.ukrida.root.R
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.model.GroupDetail
import coil.compose.AsyncImage
@Composable
fun DashboardHeader(
    group: GroupDetail,
    imageUrl: String? = null
) {

    Log.d("DASHBOARD_IMAGE", "imageUrl=$imageUrl")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = group.name,
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFFE8D8C9)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = group.description ?: "",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = .8f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "${group.startDate} - ${group.endDate}",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFFE8D8C9)
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (!imageUrl.isNullOrBlank()) {

            AsyncImage(
                model = imageUrl,
                contentDescription = group.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )

        } else {

            Image(
                painter = painterResource(R.drawable.pyramid),
                contentDescription = group.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )

        }

    }

}