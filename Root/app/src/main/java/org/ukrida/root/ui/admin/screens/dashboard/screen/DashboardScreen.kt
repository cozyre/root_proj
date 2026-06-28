package org.ukrida.root.ui.admin.screens.dashboard.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ukrida.root.data.AppContainer
import org.ukrida.root.ui.admin.components.PriceTripCard
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.screens.dashboard.components.ApprovalCard
import org.ukrida.root.ui.admin.screens.dashboard.viewmodel.DashboardViewModel
import org.ukrida.root.ui.admin.screens.dashboard.viewmodel.DashboardViewModelFactory
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MainButton
import org.ukrida.root.ui.theme.TitleColor
import org.ukrida.root.utils.Resource

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel(),
    onMenuClick: () -> Unit,
    onApprovalClick: () -> Unit,
    onRecentClick: () -> Unit,
    onSongClick: () -> Unit,
) {
    val approvalsState by viewModel.pendingApprovals.collectAsState()
    val latestTourState by viewModel.latestTour.collectAsState()

    val approvals = (approvalsState as? Resource.Success)?.data
    val latestTour = (latestTourState as? Resource.Success)?.data

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
                    text = "ROOT is a spiritual tour ministry " +
                            "that offers faith-filled journeys " +
                            "to the Promised Land. Founded in 2026.",
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

                approvals?.take(3)?.forEach { approval ->
                    ApprovalCard(
                        userName = approval.fullName,
                        groupName = approval.groupName
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                    Text(
                        text = "See More",
                        color = BodyColor,
                        modifier = Modifier
                            .clickable {onApprovalClick()},
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
                    .padding(20.dp)
            ) {

                Text(
                    text = "ADD SONGS",
                    style = MaterialTheme.typography.titleLarge,
                    color = H1Color
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Add a selection of worship songs " +
                            "to accompany your journey of faith, " +
                            "personal reflection, and moments of " +
                            "fellowship with God.",
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
                        modifier = Modifier.clickable {onSongClick()}
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
                    text = "Manage ongoing trips, " +
                            "track progress in real time, " +
                            "and ensure all activities run " +
                            "smoothly as planned.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = BodyColor
                )

                Spacer(modifier = Modifier.height(20.dp))

                latestTour?.let { group ->
                    PriceTripCard(
                        group = group
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {

                    Text(
                        text = "See More",
                        modifier = Modifier.clickable {onRecentClick()},
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
                    text = "Create a new tour and organize " +
                            "every detail to provide a " +
                            "meaningful and well-planned " +
                            "spiritual journey.",
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
