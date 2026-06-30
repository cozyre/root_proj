package org.ukrida.root.ui.public.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.ukrida.root.ui.Public.screens.dailybread.screen.DailyBreadDetailScreen
import org.ukrida.root.ui.Public.screens.dailybread.screen.DailyBreadScreen
import org.ukrida.root.ui.Public.screens.dashboard.screen.DashboardScreen
import org.ukrida.root.ui.Public.screens.gallery.screen.GalleryScreen
import org.ukrida.root.ui.Public.screens.group.screen.GroupScreen
import org.ukrida.root.ui.Public.screens.history.screen.HistoryScreen
import org.ukrida.root.ui.Public.screens.historydetail.screen.HistoryDetailScreen
import org.ukrida.root.ui.Public.screens.home.screen.HomeScreen
import org.ukrida.root.ui.Public.screens.hymn.screen.HymnDetailScreen
import org.ukrida.root.ui.Public.screens.hymn.screen.HymnScreen
import org.ukrida.root.ui.Public.screens.itinerary.screen.ItineraryScreen
import org.ukrida.root.ui.Public.screens.journal.screen.JournalEditorScreen
import org.ukrida.root.ui.Public.screens.journal.screen.JournalScreen
import org.ukrida.root.ui.Public.screens.members.screen.MemberDetailScreen
import org.ukrida.root.ui.Public.screens.members.screen.MemberScreen
import org.ukrida.root.ui.Public.screens.order.screen.OrderScreen
import org.ukrida.root.ui.Public.screens.promisedland.screen.PromisedLandScreen
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
            composable(
                route = PublicScreen.Itinerary.route,
                arguments = listOf(
                    navArgument("groupId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val groupId =
                    backStackEntry.arguments?.getInt("groupId") ?: 0
                ItineraryScreen(
                    navController = navController,
                    groupId = groupId
                )
            }
            composable(
                route = PublicScreen.DailyBread.route,
                arguments = listOf(
                    navArgument("groupId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val groupId =
                    backStackEntry.arguments?.getInt("groupId") ?: 0
                DailyBreadScreen(
                    navController = navController,
                    groupId = groupId
                )
            }
            composable(
                route = PublicScreen.DailyBreadDetail.route,
                arguments = listOf(
                    navArgument("groupId") {
                        type = NavType.IntType
                    },
                    navArgument("date") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val groupId =
                    backStackEntry.arguments?.getInt("groupId") ?: 0
                val date =
                    backStackEntry.arguments?.getString("date") ?: ""
                DailyBreadDetailScreen(
                    navController = navController,
                    groupId = groupId,
                    date = date
                )
            }
            composable(
                route = PublicScreen.Members.route,
                arguments = listOf(
                    navArgument("groupId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val groupId =
                    backStackEntry.arguments?.getInt("groupId") ?: 0
                MemberScreen(
                    navController = navController,
                    groupId = groupId
                )
            }
            composable(
                route = PublicScreen.MemberDetail.route,
                arguments = listOf(
                    navArgument("groupId") {
                        type = NavType.IntType
                    },
                    navArgument("userId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val groupId =
                    backStackEntry.arguments?.getInt("groupId") ?: 0
                val userId =
                    backStackEntry.arguments?.getInt("userId") ?: 0
                MemberDetailScreen(
                    navController = navController,
                    groupId = groupId,
                    userId = userId
                )
            }
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