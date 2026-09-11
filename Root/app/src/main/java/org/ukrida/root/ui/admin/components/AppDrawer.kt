package org.ukrida.root.ui.admin.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.ukrida.root.ui.theme.*
import kotlin.Unit

@Composable
fun AppDrawer(
    adminName: String,
    adminEmail: String,
    adminPhotoUrl: String? = null,
    onItemClick: (String) -> Unit,
    onCloseDrawer: () -> Unit,
    onLogout: () -> Unit
) {

    val menuItems = listOf(
        DrawerItem("DASHBOARD", "dashboard"),
        DrawerItem("TRIP", "trip"),
        DrawerItem("NEW TRIP", "new_trip"),
        DrawerItem("FINISHED TRIP", "finished_trip"),
        DrawerItem("ONGOING TRIP", "ongoing_trip"),
        DrawerItem("HYMN FOR HIM", "hymn_for_him"),
        DrawerItem("APPROVAL", "approval"),
        DrawerItem("BROADCAST", "broadcast")
    )

    Box {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(280.dp)
                .background(DarkBrown)
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    modifier = Modifier.size(56.dp),
                    shape = CircleShape,
                    color = TitleColor
                ) {
                    if (adminPhotoUrl != null) {
                        AsyncImage(
                            model = adminPhotoUrl,
                            contentDescription = "Profile Photo",
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {

                    Text(
                        text = adminName.uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = TitleColor
                    )

                    Text(
                        text = adminEmail,
                        color = H1Color,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider(color = H1Color)

            Spacer(modifier = Modifier.height(32.dp))

            menuItems.forEach {

                Text(
                    text = it.title,
                    color = H1Color,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onItemClick(it.route)
                        }
                        .padding(vertical = 16.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onLogout()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MicroElement
                ),
                shape = RoundedCornerShape(50)
            ) {
                Text("LOG OUT")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Surface(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = 24.dp)
                .size(60.dp),
            shape = CircleShape,
            color = DarkBrown,
            onClick = onCloseDrawer
        ) {

            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "<",
                    color = BodyColor
                )
            }
        }
    }
}
