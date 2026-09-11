package org.ukrida.root.ui.user.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.ukrida.root.R
import org.ukrida.root.data.model.Group
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import java.text.NumberFormat
import java.util.Locale

@Composable
fun RecommendationCard(
    group: Group,
    imageUrl: String? = null,
    onClick: () -> Unit = {}
) {

    val formattedPrice = NumberFormat
        .getNumberInstance(Locale("id", "ID"))
        .format(group.price ?: 0)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = DrawerBackground
        ),
        shape = RoundedCornerShape(18.dp)
    ) {

        if (!imageUrl.isNullOrBlank()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = group.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(R.drawable.no_image),
                contentDescription = group.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = group.name,
                style = MaterialTheme.typography.titleLarge,
                color = H1Color
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = group.description.orEmpty(),
                style = MaterialTheme.typography.bodyLarge,
                color = BodyColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Rp $formattedPrice",
                style = MaterialTheme.typography.titleLarge,
                color = H1Color
            )
        }
    }
}