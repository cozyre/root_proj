package org.ukrida.root.ui.user.screens.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DashboardMenu(

    onDashboardClick: () -> Unit,

    onItineraryClick: () -> Unit,

    onHymnClick: () -> Unit,

    onDailyBreadClick: () -> Unit,

    onJournalClick: () -> Unit,

    onGalleryClick: () -> Unit,

    onMembersClick: () -> Unit

) {

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2A2522))
            .statusBarsPadding()
            .padding(vertical = 24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.spacedBy(14.dp)

    ) {

        DashboardMenuItem(
            title = "DASHBOARD",
            onClick = onDashboardClick
        )

        DashboardMenuItem(
            title = "ITINERARY",
            onClick = onItineraryClick
        )

        DashboardMenuItem(
            title = "HYMN FOR HIM",
            onClick = onHymnClick
        )

        DashboardMenuItem(
            title = "DAILY BREAD",
            onClick = onDailyBreadClick
        )

        DashboardMenuItem(
            title = "JOURNAL",
            onClick = onJournalClick
        )

        DashboardMenuItem(
            title = "GALLERY",
            onClick = onGalleryClick
        )

        DashboardMenuItem(
            title = "MEMBERS",
            onClick = onMembersClick
        )

    }

}