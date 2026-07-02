package org.ukrida.root.ui.user.screens.history.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.screens.history.components.HistorySection
import org.ukrida.root.ui.user.screens.history.viewmodel.HistoryViewModel
import org.ukrida.root.utils.Resource

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel(),
    navController: NavHostController
) {
    val historyGroupState by viewModel.historyGroups.collectAsState()
    val historyGroups = (historyGroupState as? Resource.Success)?.data
    Scaffold(
        containerColor = Color(0xFF2A2522),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2A2522))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            HistorySection(
                navController = navController,
                historyGroups = historyGroups,
                onHistoryClick = { groupId ->
                    navController.navigate(
                        PublicScreen.HistoryDetail.createRoute(groupId)
                    )
                }
            )

        }

    }

}