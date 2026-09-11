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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ukrida.root.ui.admin.components.ApprovalCard
import org.ukrida.root.ui.admin.components.PriceTripCard
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.screens.dashboard.viewmodel.DashboardViewModel
import org.ukrida.root.ui.theme.DarkBrown
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MicroElement
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

    val latestTour = (latestTourState as? Resource.Success)?.data

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBrown)
            .verticalScroll(rememberScrollState())
    ) {
        Row {
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

                when (val state = approvalsState) {
                    is Resource.Loading -> {
                        Text(
                            text = "Loading approval requests...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = BodyColor
                        )
                    }

                    is Resource.Error -> {
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    is Resource.Success -> {
                        val pendingApprovals = state.data

                        if (pendingApprovals.isEmpty()) {
                            ApprovalRequestPlaceholder()
                        } else {
                            pendingApprovals.take(3).forEach { approval ->
                                ApprovalCard(
                                    userName = approval.fullName,
                                    groupName = approval.groupName,
                                    enabled = viewModel.processingAccountId != approval.id,
                                    onApprove = {
                                        viewModel.approve(approval.id)
                                    },
                                    onReject = {
                                        viewModel.reject(approval.id)
                                    }
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = "See More",
                                    color = BodyColor,
                                    modifier = Modifier.clickable {
                                        onApprovalClick()
                                    }
                                )
                            }
                        }
                    }
                }

                viewModel.approvalActionError?.let { message ->
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
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
                        modifier = Modifier.clickable {
                            onSongClick()
                        }
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
                        modifier = Modifier.clickable {
                            onRecentClick()
                        },
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
                        containerColor = MicroElement
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

@Composable
private fun ApprovalRequestPlaceholder() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = DrawerBackground,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No approval requests",
            style = MaterialTheme.typography.titleMedium,
            color = H1Color
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "All participant requests have been approved or rejected.",
            style = MaterialTheme.typography.bodyLarge,
            color = BodyColor,
            textAlign = TextAlign.Center
        )
    }
}