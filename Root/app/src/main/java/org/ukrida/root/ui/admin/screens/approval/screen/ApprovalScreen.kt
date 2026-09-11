package org.ukrida.root.ui.admin.screens.approval.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ukrida.root.ui.admin.components.ApprovalCard
import org.ukrida.root.ui.admin.components.ApprovalStatusCard
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.screens.approval.viewmodel.ApprovalViewModel
import org.ukrida.root.ui.theme.DarkBrown
import org.ukrida.root.ui.theme.BodyColor
import org.ukrida.root.ui.theme.DrawerBackground
import org.ukrida.root.ui.theme.H1Color

@Composable
fun ApprovalScreen(
    onMenuClick: () -> Unit,
    viewModel: ApprovalViewModel = viewModel()
) {

    val pendingRequests = viewModel.pendingRequests
    val processedRequests = viewModel.processedRequests

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBrown)
            .verticalScroll(rememberScrollState())
    ) {

        TopBar(
            title = "APPROVAL",
            onMenuClick = onMenuClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {

            Text(
                text = "APPROVAL REQUEST",
                style = MaterialTheme.typography.titleLarge,
                color = H1Color
            )

            if (viewModel.isLoading) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Loading approvals...",
                    color = BodyColor
                )
            }

            viewModel.errorMessage?.let { message ->
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (!viewModel.isLoading && pendingRequests.isEmpty()) {
                ApprovalPlaceholder(
                    title = "No approval requests",
                    description = "All participant requests have been approved or rejected."
                )
            } else {
                pendingRequests.take(
                    if (viewModel.showAllPending) Int.MAX_VALUE else 4
                ).forEach { approval ->

                    ApprovalCard(
                        userName = approval.userName,
                        groupName = approval.groupName,
                        enabled = viewModel.processingAccountId != approval.accountId,
                        onApprove = {
                            viewModel.approve(approval.accountId)
                        },
                        onReject = {
                            viewModel.reject(approval.accountId)
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }

                if (pendingRequests.size > 4) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {

                        Text(
                            text = if (viewModel.showAllPending)
                                "Show Less"
                            else
                                "See More",
                            color = BodyColor,
                            modifier = Modifier.clickable {
                                viewModel.togglePending()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "APPROVED/REJECTED",
                style = MaterialTheme.typography.titleLarge,
                color = H1Color
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (!viewModel.isLoading && processedRequests.isEmpty()) {
                ApprovalPlaceholder(
                    title = "No processed requests",
                    description = "Approved and rejected requests will appear here."
                )
            } else {
                processedRequests.take(
                    if (viewModel.showAllProcessed) Int.MAX_VALUE else 4
                ).forEach { approval ->

                    ApprovalStatusCard(
                        userName = approval.userName,
                        groupName = approval.groupName,
                        status = approval.status
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }

                if (processedRequests.size > 4) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {

                        Text(
                            text = if (viewModel.showAllProcessed)
                                "Show Less"
                            else
                                "See More",
                            color = BodyColor,
                            modifier = Modifier.clickable {
                                viewModel.toggleProcessed()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun ApprovalPlaceholder(
    title: String,
    description: String
) {
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
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = H1Color,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            color = BodyColor,
            textAlign = TextAlign.Center
        )
    }
}