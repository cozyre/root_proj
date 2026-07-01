package org.ukrida.root.ui.public.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.ukrida.root.ui.user.screens.groupmenus.dashboard.screen.DashboardScreen
import org.ukrida.root.ui.user.screens.group.screen.GroupScreen
import org.ukrida.root.ui.user.screens.history.screen.HistoryScreen
import org.ukrida.root.ui.user.screens.home.screen.HomeScreen
import org.ukrida.root.ui.user.screens.promisedland.screen.PromisedLandScreen
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.ukrida.root.data.AppContainer
import org.ukrida.root.ui.user.navigation.PublicScreen
import org.ukrida.root.ui.user.screens.group.viewmodel.GroupViewModel
import org.ukrida.root.ui.user.screens.group.viewmodel.GroupViewModelFactory
import org.ukrida.root.ui.user.screens.history.viewmodel.HistoryViewModelFactory
import org.ukrida.root.ui.user.screens.history.viewmodel.HistoryViewModel
import org.ukrida.root.ui.user.screens.home.viewmodel.HomeViewModel
import org.ukrida.root.ui.user.screens.home.viewmodel.HomeViewModelFactory
import org.ukrida.root.ui.user.screens.profile.screen.ProfileScreen
import org.ukrida.root.ui.user.screens.profile.viewmodel.ProfileViewModel
import org.ukrida.root.ui.user.screens.profile.viewmodel.ProfileViewModelFactory
import org.ukrida.root.ui.user.screens.promisedland.viewmodel.PromisedLandViewModel
import org.ukrida.root.ui.user.screens.promisedland.viewmodel.PromisedLandViewModelFactory

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
    }
}
