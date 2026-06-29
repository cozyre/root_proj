package org.ukrida.root.ui.public.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.ukrida.root.data.AppContainer
import org.ukrida.root.ui.Public.navigation.PublicScreen
import org.ukrida.root.ui.Public.screens.history.screen.HistoryScreen
import org.ukrida.root.ui.Public.screens.historydetail.screen.HistoryDetailScreen
import org.ukrida.root.ui.Public.screens.home.screen.HomeScreen
import org.ukrida.root.ui.Public.screens.order.screen.OrderScreen
//import org.ukrida.root.ui.Public.screens.profile.screen.ProfileScreen
import org.ukrida.root.ui.Public.screens.promisedland.screen.PromisedLandScreen

@Composable
fun PublicNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    appContainer: AppContainer
) {

    NavHost(
        navController = navController,
        startDestination = PublicScreen.Home.route,
        modifier = modifier
    ) {

        composable(PublicScreen.Home.route) {
            HomeScreen(
                navController = navController,
//                appContainer = appContainer
            )
        }

        composable(PublicScreen.PromisedLand.route) {
            PromisedLandScreen(
                navController = navController,
//                appContainer = appContainer
            )
        }

        composable(PublicScreen.History.route) {
            HistoryScreen(
                navController = navController,
//                appContainer = appContainer
            )
        }

        composable(
            route = PublicScreen.HistoryDetail.route,
            arguments = listOf(
                navArgument("groupId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getInt("groupId") ?: 0
            HistoryDetailScreen(
                navController = navController,
                groupId = groupId,
//                appContainer = appContainer
            )
        }

//        composable(
//            route = PublicScreen.Order.route,
//            arguments = listOf(
//                navArgument("groupId") {
//                    type = NavType.IntType
//                }
//            )
//        ) { backStackEntry ->
//            val groupId = backStackEntry.arguments?.getInt("groupId") ?: 0
//            OrderScreen(
//                navController = navController,
//                groupId = groupId,
//                appContainer = appContainer
//            )
//        }

//        composable(PublicScreen.Profile.route) {
//            ProfileScreen(
//                navController = navController,
//                appContainer = appContainer
//            )
//        }
    }
}