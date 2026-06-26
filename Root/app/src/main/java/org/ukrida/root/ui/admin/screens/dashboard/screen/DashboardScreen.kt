package org.ukrida.root.ui.admin.screens.dashboard.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.ukrida.root.ui.admin.screens.dashboard.components.AddSongCard
import org.ukrida.root.ui.admin.screens.dashboard.components.CreateTourCard
import org.ukrida.root.ui.admin.screens.dashboard.components.RecentTripSection
import org.ukrida.root.ui.theme.BackgroundDark

@Composable
fun DashboardScreen(onMenuClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
    ) {
        Row(){
            DashboardTopBar(
                onMenuClick = {
                    onMenuClick()
                }
            )

        }
        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            DashboardHeader()

            Spacer(modifier = Modifier.height(40.dp))

            ApprovalSection()

            Spacer(modifier = Modifier.height(40.dp))

            AddSongCard()

            Spacer(modifier = Modifier.height(40.dp))

            RecentTripSection()

            Spacer(modifier = Modifier.height(40.dp))

            CreateTourCard()

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}