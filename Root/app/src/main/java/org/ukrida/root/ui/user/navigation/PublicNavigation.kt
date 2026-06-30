package org.ukrida.root.ui.public.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.ukrida.root.ui.user.screens.dailybread.screen.DailyBreadDetailScreen
import org.ukrida.root.ui.user.screens.dailybread.screen.DailyBreadScreen
import org.ukrida.root.ui.user.screens.dashboard.screen.DashboardScreen
import org.ukrida.root.ui.user.screens.gallery.screen.GalleryScreen
import org.ukrida.root.ui.user.screens.group.screen.GroupScreen
import org.ukrida.root.ui.user.screens.history.screen.HistoryScreen
import org.ukrida.root.ui.user.screens.historydetail.screen.HistoryDetailScreen
import org.ukrida.root.ui.user.screens.home.screen.HomeScreen
import org.ukrida.root.ui.user.screens.hymn.screen.HymnDetailScreen
import org.ukrida.root.ui.user.screens.hymn.screen.HymnScreen
import org.ukrida.root.ui.user.screens.itinerary.screen.ItineraryScreen
import org.ukrida.root.ui.user.screens.journal.screen.JournalEditorScreen
import org.ukrida.root.ui.user.screens.journal.screen.JournalScreen
import org.ukrida.root.ui.user.screens.members.screen.MemberDetailScreen
import org.ukrida.root.ui.user.screens.members.screen.MemberScreen
import org.ukrida.root.ui.user.screens.order.screen.OrderScreen
import org.ukrida.root.ui.user.screens.promisedland.screen.PromisedLandScreen
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.ukrida.root.data.AppContainer
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.screens.group.viewmodel.GroupViewModel
import org.ukrida.root.ui.user.screens.group.viewmodel.GroupViewModelFactory
import org.ukrida.root.ui.user.screens.history.screen.HistoryViewModelFactory
import org.ukrida.root.ui.user.screens.history.viewmodel.HistoryViewModel
import org.ukrida.root.ui.user.screens.home.viewmodel.HomeViewModel
import org.ukrida.root.ui.user.screens.home.viewmodel.HomeViewModelFactory
import org.ukrida.root.ui.user.screens.profile.screen.ProfileScreen
import org.ukrida.root.ui.user.screens.profile.viewmodel.ProfileViewModel
import org.ukrida.root.ui.user.screens.profile.viewmodel.ProfileViewModelFactory
import org.ukrida.root.ui.user.screens.promisedland.viewmodel.PromisedLandViewModel
import org.ukrida.root.ui.user.screens.promisedland.viewmodel.PromisedLandViewModelFactory
import org.ukrida.root.utils.SessionManager

//import org.ukrida.root.ui.user.screens.profile.screen.ProfileScreen

@Composable
fun PublicNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    appContainer: AppContainer,
    onLogout: () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = PublicScreen.Home.route,
        modifier = modifier
    ) {

        composable(PublicScreen.Home.route) {
            val factory = remember {
                HomeViewModelFactory(appContainer.groupRepository)
            }
            val viewModel: HomeViewModel = viewModel(factory = factory)
            HomeScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(PublicScreen.PromisedLand.route) {
            val factory = remember {
                PromisedLandViewModelFactory(appContainer.groupRepository)
            }
            val viewModel: PromisedLandViewModel = viewModel(factory = factory)
            PromisedLandScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(PublicScreen.Group.route){
            val factory = remember{
                GroupViewModelFactory(
                    groupRepository = appContainer.groupRepository,
                    accountRepository = appContainer.accountRepository
                )
            }
            val viewModel: GroupViewModel = viewModel(factory = factory)
            GroupScreen(
                viewModel = viewModel,
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

        composable(PublicScreen.History.route) {
            val factory = remember{
                HistoryViewModelFactory(appContainer.groupRepository)
            }
            val viewModel: HistoryViewModel = viewModel(factory = factory)
            HistoryScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(PublicScreen.Profile.route) {
            val factory = remember{
                ProfileViewModelFactory(appContainer.profileRepository)
            }
            val viewModel: ProfileViewModel = viewModel(factory = factory)
            ProfileScreen(
                viewModel = viewModel,
                navController = navController,
                onLogout = onLogout
            )
        }

//                val groupId =
//                    backStackEntry.arguments
//                        ?.getString("groupId")
//                        ?.toInt() ?: 0
//                HistoryDetailScreen(
//                    navController,
//                    groupId
//                )
//            }
//            composable(
//                route = PublicScreen.Order.route,
//                arguments = listOf(
//                    navArgument("groupId") {
//                        type = NavType.IntType
//                    }
//                )
//            ) { backStackEntry ->
//                val groupId =
//                    backStackEntry.arguments?.getInt("groupId") ?: 0
//                OrderScreen(
//                    navController = navController,
//                    groupId = groupId
//                )
//            }

//            composable(
//                route = PublicScreen.Hymn.route,
//                arguments = listOf(
//                    navArgument("groupId") {
//                        type = NavType.IntType
//                    }
//                )
//            ) { backStackEntry ->
//                val groupId =
//                    backStackEntry.arguments?.getInt("groupId") ?: 0
//                HymnScreen(
//                    navController = navController,
//                    groupId = groupId
//                )
//            }
//            composable(
//                route = PublicScreen.HymnDetail.route,
//                arguments = listOf(
//                    navArgument("groupId") {
//                        type = NavType.IntType
//                    },
//                    navArgument("songId") {
//                        type = NavType.IntType
//                    }
//                )
//            ) { backStackEntry ->
//                val groupId =
//                    backStackEntry.arguments?.getInt("groupId") ?: 0
//                val songId =
//                    backStackEntry.arguments?.getInt("songId") ?: 0
//                HymnDetailScreen(
//                    navController = navController,
//                    groupId = groupId,
//                    songId = songId
//                )
//            }
//            composable(
//                route = PublicScreen.Gallery.route,
//                arguments = listOf(
//                    navArgument("groupId") {
//                        type = NavType.IntType
//                    }
//                )
//            ) { backStackEntry ->
//                val groupId =
//                    backStackEntry.arguments?.getInt("groupId") ?: 0
//                GalleryScreen(
//                    navController = navController,
//                    groupId = groupId
//                )
//            }
//            composable(
//                route = PublicScreen.Journal.route,
//                arguments = listOf(
//                    navArgument("groupId") {
//                        type = NavType.IntType
//                    }
//                )
//            ) { backStackEntry ->
//                val groupId =
//                    backStackEntry.arguments?.getInt("groupId") ?: 0
//                JournalScreen(
//                    navController = navController,
//                    groupId = groupId
//                )
//            }
//            composable(
//                route = PublicScreen.JournalEditor.route,
//                arguments = listOf(
//                    navArgument("groupId") {
//                        type = NavType.IntType
//                    },
//                    navArgument("journalId") {
//                        type = NavType.IntType
//                    }
//                )
//            ) { backStackEntry ->
//                val groupId =
//                    backStackEntry.arguments?.getInt("groupId") ?: 0
//                val journalId =
//                    backStackEntry.arguments?.getInt("journalId") ?: -1
//                JournalEditorScreen(
//                    navController = navController,
//                    groupId = groupId,
//                    journalId =
//                        if (journalId == -1) null else journalId
//                )
//            }
//            composable(
//                route = PublicScreen.Itinerary.route,
//                arguments = listOf(
//                    navArgument("groupId") {
//                        type = NavType.IntType
//                    }
//                )
//            ) { backStackEntry ->
//                val groupId =
//                    backStackEntry.arguments?.getInt("groupId") ?: 0
//                ItineraryScreen(
//                    navController = navController,
//                    groupId = groupId
//                )
//            }
//            composable(
//                route = PublicScreen.DailyBread.route,
//                arguments = listOf(
//                    navArgument("groupId") {
//                        type = NavType.IntType
//                    }
//                )
//            ) { backStackEntry ->
//                val groupId =
//                    backStackEntry.arguments?.getInt("groupId") ?: 0
//                DailyBreadScreen(
//                    navController = navController,
//                    groupId = groupId
//                )
//            }
//            composable(
//                route = PublicScreen.DailyBreadDetail.route,
//                arguments = listOf(
//                    navArgument("groupId") {
//                        type = NavType.IntType
//                    },
//                    navArgument("date") {
//                        type = NavType.StringType
//                    }
//                )
//            ) { backStackEntry ->
//                val groupId =
//                    backStackEntry.arguments?.getInt("groupId") ?: 0
//                val date =
//                    backStackEntry.arguments?.getString("date") ?: ""
//                DailyBreadDetailScreen(
//                    navController = navController,
//                    groupId = groupId,
//                    date = date
//                )
//            }
//            composable(
//                route = PublicScreen.Members.route,
//                arguments = listOf(
//                    navArgument("groupId") {
//                        type = NavType.IntType
//                    }
//                )
//            ) { backStackEntry ->
//                val groupId =
//                    backStackEntry.arguments?.getInt("groupId") ?: 0
//                MemberScreen(
//                    navController = navController,
//                    groupId = groupId
//                )
//            }
//            composable(
//                route = PublicScreen.MemberDetail.route,
//                arguments = listOf(
//                    navArgument("groupId") {
//                        type = NavType.IntType
//                    },
//                    navArgument("userId") {
//                        type = NavType.IntType
//                    }
//                )
//            ) { backStackEntry ->
//                val groupId =
//                    backStackEntry.arguments?.getInt("groupId") ?: 0
//                val userId =
//                    backStackEntry.arguments?.getInt("userId") ?: 0
//                MemberDetailScreen(
//                    navController = navController,
//                    groupId = groupId,
//                    userId = userId
//                )
//            }
//        }

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
    }
}
