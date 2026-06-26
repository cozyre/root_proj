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
import org.ukrida.root.ui.admin.screens.hymn.screen.HymnForHimScreen
import org.ukrida.root.ui.admin.screens.newtrip.screen.NewTripScreen
import org.ukrida.root.ui.admin.screens.finished.screen.FinishedTripScreen
import org.ukrida.root.ui.admin.screens.ongoing.screen.EditDailyBreadScreen
import org.ukrida.root.ui.admin.screens.ongoing.screen.EditItineraryScreen
import org.ukrida.root.ui.admin.screens.ongoing.screen.EditSongsScreen
import org.ukrida.root.ui.admin.screens.ongoing.screen.OnGoingDetailScreen
import org.ukrida.root.ui.admin.screens.ongoing.screen.OnGoingScreen
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.EditDailyBreadViewModel
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.EditItineraryViewModel
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.EditSongsViewModel
import org.ukrida.root.ui.admin.screens.ongoing.viewmodel.OnGoingDetailViewModel


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
                onMenuClick = onMenuClick
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
            arguments = listOf(
                navArgument("tripId") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            // 1. Ambil tripId dari bundle argument navigasi secara aman (default ke 1 jika gagal)
            val tripId = backStackEntry.arguments?.getInt("tripId") ?: 1

            // 2. Buat ViewModel secara manual menggunakan Factory agar argument ter-inject sempurna
            val viewModel: OnGoingDetailViewModel = viewModel(
                factory = viewModelFactory {
                    initializer {
                        OnGoingDetailViewModel(tripId = tripId)
                    }
                }
            )

            OnGoingDetailScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.EditItinerary.route,
            arguments = listOf(navArgument("tripId") { type = NavType.IntType })
        ) {
            // ✅ Tidak perlu backStackEntry, tidak perlu factory
            // SavedStateHandle otomatis menerima tripId dari navigation argument
            val viewModel: EditItineraryViewModel = viewModel()

            EditItineraryScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.EditSongs.route,
            arguments = listOf(navArgument("tripId") { type = NavType.StringType })
        ) {
            val viewModel: EditSongsViewModel = viewModel()
            EditSongsScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.EditDailyBread.route,
            arguments = listOf(navArgument("tripId") { type = NavType.StringType })
        ) {
            val viewModel: EditDailyBreadViewModel = viewModel()
            EditDailyBreadScreen(
                navController = navController,
                onMenuClick = onMenuClick,
                viewModel = viewModel
            )
        }

        composable(Screen.NewTrip.route) {
            NewTripScreen(onMenuClick = onMenuClick)
        }

        composable(Screen.FinishedTrip.route) {
            FinishedTripScreen(onMenuClick = onMenuClick)
        }
    }
}