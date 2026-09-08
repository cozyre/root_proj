package org.ukrida.root.ui.admin.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import org.ukrida.root.data.AppContainer
import org.ukrida.root.ui.admin.screens.dashboard.screen.DashboardScreen
import org.ukrida.root.ui.admin.screens.approval.screen.ApprovalScreen
import org.ukrida.root.ui.admin.screens.dashboard.viewmodel.DashboardViewModel
import org.ukrida.root.ui.admin.screens.dashboard.viewmodel.DashboardViewModelFactory
import org.ukrida.root.ui.admin.screens.finished.screen.FinishedTripDetailScreen
import org.ukrida.root.ui.admin.screens.hymn.screen.HymnForHimScreen
import org.ukrida.root.ui.admin.screens.newtrip.screen.NewTripScreen
import org.ukrida.root.ui.admin.screens.finished.screen.FinishedTripScreen
import org.ukrida.root.ui.admin.screens.finished.viewmodel.FinishedTripDetailViewModel
import org.ukrida.root.ui.admin.screens.hymn.screen.AddSongScreen
import org.ukrida.root.ui.admin.screens.hymn.screen.EditSongScreen
import org.ukrida.root.ui.admin.screens.hymn.viewmodel.EditSongViewModel
import org.ukrida.root.ui.admin.screens.hymn.viewmodel.HymnForHimViewModel
import org.ukrida.root.ui.admin.screens.ongoing.screen.EditDailyBreadScreen
import org.ukrida.root.ui.admin.screens.ongoing.screen.EditItineraryScreen
import org.ukrida.root.ui.admin.screens.ongoing.screen.EditSongsScreen
import org.ukrida.root.ui.admin.screens.ongoing.screen.OnGoingDetailScreen
import org.ukrida.root.ui.admin.screens.ongoing.screen.OnGoingScreen
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.EditDailyBreadViewModel
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.EditItineraryViewModel
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.EditSongsViewModel
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.OnGoingDetailViewModel
import org.ukrida.root.ui.admin.screens.trip.screen.TripScreen
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.ukrida.root.ui.admin.screens.approval.viewmodel.ApprovalViewModel
import org.ukrida.root.ui.admin.screens.finished.viewmodel.FinishedTripViewModel
import org.ukrida.root.ui.admin.screens.hymn.viewmodel.AddSongViewModel
import org.ukrida.root.ui.admin.screens.newtrip.screen.NewDailyBreadScreen
import org.ukrida.root.ui.admin.screens.newtrip.viewmodel.NewTripViewModel
import org.ukrida.root.ui.admin.screens.newtrip.screen.NewItineraryScreen
import org.ukrida.root.ui.admin.screens.newtrip.screen.NewSongsScreen
import org.ukrida.root.ui.admin.screens.newtrip.viewmodel.NewDailyBreadViewModel
import org.ukrida.root.ui.admin.screens.newtrip.viewmodel.NewItineraryViewModel
import org.ukrida.root.ui.admin.screens.newtrip.viewmodel.NewSongsViewModel
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.OnGoingViewModel


@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit,
    appContainer: AppContainer
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {

        // Dashboard >>>>>>>>>>>>>>>>>>>>>>>>>>
        composable(Screen.Dashboard.route) {
            val factory = remember {
                DashboardViewModelFactory(
                    appContainer.adminRepository,
                    appContainer.groupRepository
                )
            }

            val viewModel: DashboardViewModel = viewModel(factory = factory)
            DashboardScreen(
                viewModel = viewModel,
                onMenuClick = onMenuClick,
                onApprovalClick = { navController.navigate(Screen.Approval.route) },
                onSongClick = { navController.navigate(Screen.HymnForHim.route) },
                onRecentClick = { navController.navigate(Screen.OngoingTrip.route) }
            )
        }

        // Approval >>>>>>>>>>>>>>>>>>>>>>>>>
        composable(Screen.Approval.route) {
            val viewModel: ApprovalViewModel = viewModel(
                factory = ApprovalViewModel.factory(appContainer.adminRepository)
            )
            ApprovalScreen(
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }

        // HymnForHim >>>>>>>>>>>>>>>>>>>>>>>
        composable(Screen.HymnForHim.route) { backStackEntry ->

            val viewModel: HymnForHimViewModel = viewModel(
                factory = HymnForHimViewModel.factory(
                    appContainer.songRepository
                )
            )

            val refreshSongs by backStackEntry
                .savedStateHandle
                .getStateFlow("refreshSongs", false)
                .collectAsState()

            LaunchedEffect(refreshSongs) {
                if (refreshSongs) {
                    viewModel.loadSongs()
                    backStackEntry.savedStateHandle["refreshSongs"] = false
                }
            }

            HymnForHimScreen(
                onMenuClick = onMenuClick,
                onCreateSongClick = {
                    navController.navigate(Screen.AddSong.route)
                },
                onSongClick = { songId ->
                    navController.navigate("edit_song/$songId")
                },
                viewModel = viewModel
            )
        }

        // AddSong >>>>>>>>>>>>>>>>>>>>>>>>>
        composable(Screen.AddSong.route) {
            val viewModel: AddSongViewModel = viewModel(
                factory = AddSongViewModel.factory(
                    appContainer.adminRepository
                )
            )

            AddSongScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSongCreated = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("refreshSongs", true)

                    navController.popBackStack()
                },
                viewModel = viewModel
            )
        }

        // OngoingTrip >>>>>>>>>>>>>>>>>>>>>>>>>
        composable(Screen.OngoingTrip.route) {
            val viewModel: OnGoingViewModel = viewModel(
                factory = OnGoingViewModel.factory(
                    appContainer.groupRepository,
                    appContainer.adminRepository,
                    appContainer.galleryRepository
                )
            )
            OnGoingScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }

        // OngoingDetail >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        composable(
            route = Screen.OngoingDetail.route,
            arguments = listOf(navArgument("tripId") { type = NavType.IntType })
        ) {
            backStackEntry ->

            val tripId =
                backStackEntry.arguments?.getInt("tripId") ?: 0

            val viewModel: OnGoingDetailViewModel = viewModel(
                factory = OnGoingDetailViewModel.factory(
                    tripId,
                    appContainer.groupRepository,
                    appContainer.memberRepository,
                    appContainer.accountRepository,
                    appContainer.galleryRepository,
                    appContainer.adminRepository
                )
            )
            OnGoingDetailScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }

        // Edit Itinerary >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        composable(
            route = Screen.EditItinerary.route,
            arguments = listOf(
                navArgument("tripId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val tripId =
                backStackEntry.arguments?.getInt("tripId") ?: 0

            val viewModel: EditItineraryViewModel = viewModel(
                factory = EditItineraryViewModel.factory(
                    tripId,
                    appContainer.itineraryRepository,
                    appContainer.groupRepository
                )
            )

            EditItineraryScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.EditSongs.route,
            arguments = listOf(
                navArgument("tripId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val tripId =
                backStackEntry.arguments?.getInt("tripId") ?: 0

            val viewModel: EditSongsViewModel = viewModel(
                factory = EditSongsViewModel.factory(
                    tripId,
                    appContainer.songRepository,
                    appContainer.groupRepository,
                    appContainer.itineraryRepository
                )
            )

            EditSongsScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.EditDailyBread.route,
            arguments = listOf(
                navArgument("tripId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val tripId =
                backStackEntry.arguments?.getInt("tripId") ?: 0

            val viewModel: EditDailyBreadViewModel = viewModel(
                factory = EditDailyBreadViewModel.factory(
                    tripId,
                    appContainer.devotionRepository,
                    appContainer.adminRepository,
                    appContainer.groupRepository
                )
            )

            EditDailyBreadScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }

        composable(Screen.Trip.route) {
            TripScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                onNewTripClick ={
                    navController.navigate(Screen.NewTrip.route)}
            )
        }

        composable(Screen.NewTrip.route) {
            val viewModel: NewTripViewModel = viewModel(
                factory = NewTripViewModel.factory(
                    adminRepository = appContainer.adminRepository,
                    galleryRepository = appContainer.galleryRepository,
                    memberRepository = appContainer.memberRepository
                )
            )

            NewTripScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.NewItinerary.route,
            arguments = listOf(
                navArgument("tripId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val tripId = backStackEntry.arguments?.getInt("tripId") ?: 0

            val viewModel: NewItineraryViewModel = viewModel(
                factory = NewItineraryViewModel.factory(
                    tripId = tripId,
                    itineraryRepository = appContainer.itineraryRepository
                )
            )

            NewItineraryScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }
        composable(
            route = Screen.NewSongs.route,
            arguments = listOf(
                navArgument("tripId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val tripId = backStackEntry.arguments?.getInt("tripId") ?: 0

            val viewModel: NewSongsViewModel = viewModel(
                factory = NewSongsViewModel.factory(
                    tripId = tripId,
                    songRepository = appContainer.songRepository,
                    itineraryRepository = appContainer.itineraryRepository
                )
            )

            NewSongsScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }
        composable(
            route = Screen.NewDailyBread.route,
            arguments = listOf(
                navArgument("tripId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val tripId = backStackEntry.arguments?.getInt("tripId") ?: 0

            val viewModel: NewDailyBreadViewModel = viewModel(
                factory = NewDailyBreadViewModel.factory(
                    tripId = tripId,
                    adminRepository = appContainer.adminRepository,
                    devotionRepository = appContainer.devotionRepository
                )
            )

            NewDailyBreadScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }

        composable(Screen.FinishedTrip.route) {
            val viewModel: FinishedTripViewModel = viewModel(
                factory = FinishedTripViewModel.factory(appContainer.adminRepository)
            )
            FinishedTripScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.FinishedDetail.route,
            arguments = listOf(
                navArgument("tripId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val tripId =
                backStackEntry.arguments?.getInt("tripId") ?: 0

            val viewModel: FinishedTripDetailViewModel = viewModel(
                factory = FinishedTripDetailViewModel.factory(
                    tripId,
                    appContainer.groupRepository,
                    appContainer.memberRepository,
                    appContainer.galleryRepository
                )
            )

            FinishedTripDetailScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }
        composable(
            route = Screen.EditSong.route,
            arguments = listOf(
                navArgument("songId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val songId =
                backStackEntry.arguments?.getInt("songId") ?: 0

            val viewModel: EditSongViewModel = viewModel(
                factory = EditSongViewModel.factory(
                    songId = songId,
                    songRepository = appContainer.songRepository,
                    adminRepository = appContainer.adminRepository
                )
            )

            EditSongScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }
    }
}