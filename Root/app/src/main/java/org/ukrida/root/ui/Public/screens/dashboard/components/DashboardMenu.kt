package org.ukrida.root.ui.Public.screens.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

        verticalArrangement = Arrangement.spacedBy(28.dp)

    ) {

        DashboardMenuItem(
            title = "Dashboard",
            onClick = onDashboardClick
        )

        DashboardMenuItem(
            title = "Itinerary",
            onClick = onItineraryClick
        )

        DashboardMenuItem(
            title = "Hymn for Him",
            onClick = onHymnClick
        )

        DashboardMenuItem(
            title = "Daily Bread",
            onClick = onDailyBreadClick
        )

        DashboardMenuItem(
            title = "Journal",
            onClick = onJournalClick
        )

        DashboardMenuItem(
            title = "Gallery",
            onClick = onGalleryClick
        )

        DashboardMenuItem(
            title = "Members",
            onClick = onMembersClick
        )

    }

}