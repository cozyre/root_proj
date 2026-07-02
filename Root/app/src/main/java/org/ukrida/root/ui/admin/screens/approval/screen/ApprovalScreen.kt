package org.ukrida.root.ui.admin.screens.approval.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ukrida.root.ui.admin.components.ApprovalCard
import org.ukrida.root.ui.admin.components.ApprovalStatusCard
import org.ukrida.root.ui.admin.components.TopBar
import org.ukrida.root.ui.admin.screens.approval.viewmodel.ApprovalViewModel
import org.ukrida.root.ui.theme.BackgroundDark
import org.ukrida.root.ui.theme.BodyColor
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
            .background(BackgroundDark)
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
                Text(
                    text = "Loading approvals...",
                    color = BodyColor
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            viewModel.errorMessage?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

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

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "APPROVED/REJECTED",
                style = MaterialTheme.typography.titleLarge,
                color = H1Color
            )

            Spacer(modifier = Modifier.height(20.dp))

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

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}