package org.ukrida.root.ui.user.screens.groupmenus.gallery.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.data.model.GroupImage
import org.ukrida.root.ui.user.components.PublicBottomNavigation
import org.ukrida.root.ui.user.components.PublicDestination
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.components.DashboardMenu
import org.ukrida.root.ui.user.components.DashboardTopBar
import org.ukrida.root.ui.user.screens.groupmenus.gallery.components.GalleryGrid
import org.ukrida.root.ui.user.screens.groupmenus.gallery.viewmodel.GalleryViewModel

// TODO Backend Integration
// 1. Open Android Photo Picker
// 2. Receive selected image URI
// 3. Convert URI to File
// 4. Call GalleryRepository.uploadImage(groupId, imageFile, caption)
// 5. Refresh gallery after successful upload

@Composable
fun GalleryScreen(
    viewModel: GalleryViewModel = viewModel(),
    navController: NavHostController,
    groupId: Int
) {
    val images by viewModel.images.collectAsState()
    LaunchedEffect(groupId) {
        viewModel.loadGallery(groupId)
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    Scaffold(
        containerColor = Color(0xFF2A2522),
        bottomBar = {
            PublicBottomNavigation(
                currentDestination = PublicDestination.GROUP,
                onNavigate = { destination ->
                    when(destination){
                        PublicDestination.HOME ->
                            navController.navigate(PublicScreen.Home.route)
                        PublicDestination.PROMISED_LAND ->
                            navController.navigate(PublicScreen.PromisedLand.route)
                        PublicDestination.GROUP ->
                            navController.popBackStack()
                        PublicDestination.PROFILE ->
                            navController.navigate(PublicScreen.Profile.route)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            DashboardTopBar(
                title = "Gallery",
                expanded = expanded,
                onExpandClick = {
                    expanded = !expanded
                }
            )
            GalleryGrid(
                images = images,
                modifier = Modifier.weight(1f),
                onImageClick = { image: GroupImage ->
                    // TODO Backend Integration
                    // Open full screen image preview.
                }
            )
        }
        if (expanded) {
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            expanded = false
                        }
                )
            }
            DashboardMenu(
                onDashboardClick = {
                    expanded = false
                    navController.navigate(
                        PublicScreen.Dashboard.createRoute(groupId)
                    )
                },
                onItineraryClick = {
                    expanded = false
                    navController.navigate(
                        PublicScreen.Itinerary.createRoute(groupId)
                    )
                },
                onHymnClick = {
                    expanded = false
                    navController.navigate(
                        PublicScreen.Hymn.createRoute(groupId)
                    )
                },
                onDailyBreadClick = {
                    expanded = false
                    navController.navigate(
                        PublicScreen.DailyBread.createRoute(groupId)
                    )
                },
                onJournalClick = {
                    expanded = false
                    navController.navigate(
                        PublicScreen.Journal.createRoute(groupId)
                    )
                },
                onGalleryClick = {
                    expanded = false
                },
                onMembersClick = {
                    expanded = false
                    navController.navigate(
                        PublicScreen.Members.createRoute(groupId)
                    )
                }
            )
        }
    }
}