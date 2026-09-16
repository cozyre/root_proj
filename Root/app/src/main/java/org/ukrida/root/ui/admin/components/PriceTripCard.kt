package org.ukrida.root.ui.admin.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun PriceTripCard(
    group: Group,
    imageUrl: String? = null,
    onClick: () -> Unit = {},
    showRemoveButton: Boolean = false,
    onRemoveClick: () -> Unit = {}
) {

    val formattedPrice = "Rp " + NumberFormat
        .getNumberInstance(Locale("id", "ID"))
        .format(group.price ?: 0)

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(DrawerBackground)
                .clickable { onClick() }
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {

                if (!imageUrl.isNullOrBlank()) {

                    AsyncImage(
                        model = imageUrl,
                        contentDescription = group.name,
                        modifier = Modifier.fillMaxSize(),
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
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = group.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = H1Color
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = group.description.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = BodyColor
                )

                Spacer(modifier = Modifier.height(20.dp))

                if (!group.location.isNullOrBlank()) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = BodyColor,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = group.location,
                            style = MaterialTheme.typography.bodyMedium,
                            color = BodyColor
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint = H1Color,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "${group.startDate ?: "-"} - ${group.endDate ?: "-"}",
                        style = MaterialTheme.typography.titleMedium,
                        color = H1Color
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {

                    Text(
                        text = formattedPrice,
                        style = MaterialTheme.typography.titleLarge,
                        color = H1Color
                    )
                }
            }
        }

        if (showRemoveButton) {

            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .size(28.dp)
                    .align(Alignment.TopEnd)
                    .background(
                        color = Color(0xFFD9534F),
                        shape = RoundedCornerShape(50)
                    )
                    .clickable {
                        onRemoveClick()
                    },
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "-",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}