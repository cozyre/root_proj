package org.ukrida.root.ui.admin.screens.dashboard.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.admin.components.PriceTripCard
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.screens.dashboard.components.ApprovalCard
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MainButton
import org.ukrida.root.ui.theme.TitleColor

@Composable
fun DashboardScreen(onMenuClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
    ) {
        Row(){
            TopBar(
                title = "DASHBOARD",
                onMenuClick = {
                    onMenuClick()
                }
            )

        }
        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "R",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TitleColor
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "ROOT ADMIN",
                    style = MaterialTheme.typography.titleLarge,
                    color = H1Color
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "ROOT adalah pelayanan tour rohani yang menghadirkan " +
                            "perjalanan iman menuju Tanah Perjanjian. Didirikan " +
                            "pada tahun 2026",
                    style = MaterialTheme.typography.bodyLarge,
                    color = BodyColor,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column {

                Text(
                    text = "APPROVAL REQUEST",
                    style = MaterialTheme.typography.titleLarge,
                    color = H1Color
                )

                Spacer(modifier = Modifier.height(20.dp))

                ApprovalCard()

                Spacer(modifier = Modifier.height(16.dp))

                ApprovalCard()
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = DrawerBackground,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(20.dp)
            ) {

                Text(
                    text = "ADD SONGS",
                    style = MaterialTheme.typography.titleLarge,
                    color = H1Color
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi vel luctus justo. Etiam aliquet tempus felis eget imperdiet.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = BodyColor
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {

                    Text(
                        text = "See More",
                        color = BodyColor,
                        modifier = Modifier.clickable {}
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column {

                Text(
                    text = "RECENT ONGOING TRIP",
                    style = MaterialTheme.typography.titleLarge,
                    color = H1Color
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi vel luctus justo.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = BodyColor
                )

                Spacer(modifier = Modifier.height(20.dp))

                PriceTripCard()

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {

                    Text(
                        text = "See More",
                        modifier = Modifier.clickable {},
                        color = BodyColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = DrawerBackground,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "CREATE NEW TOUR!",
                    style = MaterialTheme.typography.titleLarge,
                    color = H1Color
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi vel luctus justo.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = BodyColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainButton
                    ),
                    shape = RoundedCornerShape(50)
                ) {

                    Text(
                        text = "CREATE NEW TOUR",
                        color = H1Color
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}