package org.ukrida.root.ui.user.screens.historydetail.screen

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.ui.user.components.PublicBottomNavigation
import org.ukrida.root.ui.user.components.PublicDestination
import org.ukrida.root.ui.user.components.PublicTopBar
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.screens.historydetail.components.GallerySection
import org.ukrida.root.ui.user.screens.historydetail.components.HistoryHeader
import org.ukrida.root.ui.user.screens.historydetail.components.GroupMemberSection
import org.ukrida.root.ui.user.screens.historydetail.viewmodel.HistoryDetailViewModel

@Composable
fun HistoryDetailScreen(
    viewModel: HistoryDetailViewModel,
    navController: NavHostController,
    groupId: Int
) {

    val group by viewModel.group.collectAsState()
    val members by viewModel.members.collectAsState()
    val gallery by viewModel.gallery.collectAsState()

    LaunchedEffect(groupId) {
        viewModel.loadHistoryDetail(groupId)
    }

    Scaffold(
        containerColor = Color(0xFF2A2522),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF2A2522))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            group?.let {
                HistoryHeader(group = it, navController)
                Spacer(Modifier.height(30.dp))
                GroupMemberSection(
                    members = members,
                    onMemberClick={ },
                )
                Spacer(Modifier.height(30.dp))
                GallerySection(
                    gallery = gallery,
                    onImageClick = {},
                )
            }
        }
    }
}