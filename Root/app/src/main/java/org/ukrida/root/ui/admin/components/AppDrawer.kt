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
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.theme.*
import kotlin.Unit

@Composable
fun AppDrawer(
    onItemClick: (String) -> Unit,
    onCloseDrawer: () -> Unit,
    onLogout: () -> Unit
) {

    val menuItems = listOf(
        DrawerItem("DASHBOARD", "dashboard"),
        DrawerItem("TRIP", "trip"),
        DrawerItem("NEW TOUR", "new_trip"),
        DrawerItem("FINISHED TOUR", "finished_trip"),
        DrawerItem("ONGOING TOUR", "ongoing_trip"),
        DrawerItem("HYMN FOR HIM", "hymn_for_him"),
        DrawerItem("APPROVAL", "approval")
    )

    Box {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(280.dp)
                .background(BackgroundDark)
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
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {

                    Text(
                        text = "ADMIN NAME",
                        style = MaterialTheme.typography.titleMedium,
                        color = TitleColor
                    )

                    Text(
                        text = "admin@gmail.com",
                        color = H1Color
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
                    containerColor = MainButton
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
            color = BackgroundDark,
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