package org.ukrida.root.ui.admin.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import org.ukrida.root.ui.admin.screens.dashboard.screen.DashboardScreen
import org.ukrida.root.ui.admin.screens.approval.screen.ApprovalScreen
import org.ukrida.root.ui.admin.screens.finished.screen.FinishedTripDetailScreen
import org.ukrida.root.ui.admin.screens.hymn.screen.HymnForHimScreen
import org.ukrida.root.ui.admin.screens.newtrip.screen.NewTripScreen
import org.ukrida.root.ui.admin.screens.finished.screen.FinishedTripScreen
import org.ukrida.root.ui.admin.screens.finished.viewmodel.FinishedTripDetailViewModel
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


@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onMenuClick = onMenuClick,

                onApprovalClick = {
                    navController.navigate(Screen.Approval.route)
                },

                onSongClick = {
                    navController.navigate(Screen.HymnForHim.route)
                },

                onRecentClick = {
                    navController.navigate(Screen.OngoingTrip.route)
                }
            )
        }

        composable(Screen.Approval.route) {
            ApprovalScreen(onMenuClick = onMenuClick)
        }

        composable(Screen.HymnForHim.route) {
            HymnForHimScreen(onMenuClick = onMenuClick)
        }

        composable(Screen.OngoingTrip.route) {
            OnGoingScreen(navController = navController,  onMenuClick = onMenuClick)
        }

        composable(
            route = Screen.OngoingDetail.route,
            arguments = listOf(navArgument("tripId") { type = NavType.IntType })
        ) {
            backStackEntry ->

            val tripId =
                backStackEntry.arguments?.getInt("tripId") ?: 0

            val viewModel: OnGoingDetailViewModel = viewModel(
                factory = OnGoingDetailViewModel.factory(tripId)
            )
            OnGoingDetailScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }

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
                factory = EditItineraryViewModel.factory(tripId)
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
                factory = EditSongsViewModel.factory(tripId)
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
                factory = EditDailyBreadViewModel.factory(tripId)
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
            NewTripScreen(onMenuClick = onMenuClick)
        }

        composable(Screen.FinishedTrip.route) {
            FinishedTripScreen(navController = navController, onMenuClick = onMenuClick)
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
                factory = FinishedTripDetailViewModel.factory(tripId)
            )

            FinishedTripDetailScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }
    }
}