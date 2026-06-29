    package org.ukrida.root.ui.Public.navigation

    import androidx.compose.runtime.Composable
    import androidx.navigation.NavType
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import androidx.navigation.navArgument
    import org.ukrida.root.ui.Public.screens.dashboard.screen.DashboardScreen
    import org.ukrida.root.ui.Public.screens.gallery.screen.GalleryScreen
    import org.ukrida.root.ui.Public.screens.group.screen.GroupScreen
    import org.ukrida.root.ui.Public.screens.history.screen.HistoryScreen
    import org.ukrida.root.ui.Public.screens.historydetail.screen.HistoryDetailScreen
    import org.ukrida.root.ui.Public.screens.home.screen.HomeScreen
    import org.ukrida.root.ui.Public.screens.hymn.screen.HymnDetailScreen
    import org.ukrida.root.ui.Public.screens.hymn.screen.HymnScreen
    import org.ukrida.root.ui.Public.screens.journal.screen.JournalEditorScreen
    import org.ukrida.root.ui.Public.screens.journal.screen.JournalScreen
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
            composable(
                route = PublicScreen.Group.route
            ) {
                GroupScreen(
                    navController = navController
                )
            }
            composable(
                route = PublicScreen.Dashboard.route,
                arguments = listOf(
                    navArgument("groupId") {
                        type = NavType.IntType
                    }
                )
            ) {
                val groupId =
                    it.arguments?.getInt("groupId") ?: 0
                DashboardScreen(
                    navController = navController,
                    groupId = groupId
                )
            }
            composable(
                route = PublicScreen.Hymn.route,
                arguments = listOf(
                    navArgument("groupId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val groupId =
                    backStackEntry.arguments?.getInt("groupId") ?: 0
                HymnScreen(
                    navController = navController,
                    groupId = groupId
                )
            }
            composable(
                route = PublicScreen.HymnDetail.route,
                arguments = listOf(
                    navArgument("groupId") {
                        type = NavType.IntType
                    },
                    navArgument("songId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val groupId =
                    backStackEntry.arguments?.getInt("groupId") ?: 0
                val songId =
                    backStackEntry.arguments?.getInt("songId") ?: 0
                HymnDetailScreen(
                    navController = navController,
                    groupId = groupId,
                    songId = songId
                )
            }
            composable(
                route = PublicScreen.Gallery.route,
                arguments = listOf(
                    navArgument("groupId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val groupId =
                    backStackEntry.arguments?.getInt("groupId") ?: 0
                GalleryScreen(
                    navController = navController,
                    groupId = groupId
                )
            }
            composable(
                route = PublicScreen.Journal.route,
                arguments = listOf(
                    navArgument("groupId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val groupId =
                    backStackEntry.arguments?.getInt("groupId") ?: 0
                JournalScreen(
                    navController = navController,
                    groupId = groupId
                )
            }
            composable(
                route = PublicScreen.JournalEditor.route,
                arguments = listOf(
                    navArgument("groupId") {
                        type = NavType.IntType
                    },
                    navArgument("journalId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val groupId =
                    backStackEntry.arguments?.getInt("groupId") ?: 0
                val journalId =
                    backStackEntry.arguments?.getInt("journalId") ?: -1
                JournalEditorScreen(
                    navController = navController,
                    groupId = groupId,
                    journalId =
                        if (journalId == -1) null else journalId
                )
            }
        }
    }