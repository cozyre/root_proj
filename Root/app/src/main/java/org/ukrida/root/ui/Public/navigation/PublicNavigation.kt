    package org.ukrida.root.ui.Public.navigation

    import androidx.compose.runtime.Composable
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import org.ukrida.root.ui.Public.screens.home.screen.HomeScreen
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
        }
    }