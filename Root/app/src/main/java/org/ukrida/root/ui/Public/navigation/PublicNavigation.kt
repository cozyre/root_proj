    package org.ukrida.root.ui.Public.navigation

    import androidx.compose.runtime.Composable
    import androidx.navigation.NavType
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import androidx.navigation.navArgument
    import org.ukrida.root.ui.Public.screens.history.screen.HistoryScreen
    import org.ukrida.root.ui.Public.screens.historydetail.screen.HistoryDetailScreen
    import org.ukrida.root.ui.Public.screens.home.screen.HomeScreen
    import org.ukrida.root.ui.Public.screens.order.screen.OrderScreen
    import org.ukrida.root.ui.Public.screens.promisedland.screen.PromisedLandScreen

    @Composable
    fun PublicNavigation() {

        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = PublicScreen.Home.route
        ) {

            composable(PublicScreen.Home.route) {
                HomeScreen(navController)
            }
            composable(PublicScreen.PromisedLand.route) {
                PromisedLandScreen(navController = navController)
            }
            composable(PublicScreen.History.route) {
                HistoryScreen(
                    navController = navController
                )
            }
            composable(
                route = PublicScreen.HistoryDetail.route
            ){ backStackEntry ->

                val groupId =
                    backStackEntry.arguments
                        ?.getString("groupId")
                        ?.toInt() ?: 0
                HistoryDetailScreen(
                    navController,
                    groupId
                )
            }
            composable(
                route = PublicScreen.Order.route,
                arguments = listOf(
                    navArgument("groupId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val groupId =
                    backStackEntry.arguments?.getInt("groupId") ?: 0
                OrderScreen(
                    navController = navController,
                    groupId = groupId
                )
            }
        }
    }