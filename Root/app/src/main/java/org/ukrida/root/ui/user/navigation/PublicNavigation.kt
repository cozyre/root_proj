package org.ukrida.root.ui.user.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.ukrida.root.data.AppContainer
import org.ukrida.root.ui.user.screens.group.screen.GroupScreen
import org.ukrida.root.ui.user.screens.group.viewmodel.GroupViewModelFactory
import org.ukrida.root.ui.user.screens.group.viewmodel.GroupViewModel
import org.ukrida.root.ui.user.screens.groupmenus.dashboard.screen.DashboardScreen
import org.ukrida.root.ui.user.screens.groupmenus.dashboard.viewmodel.DashboardViewModel
import org.ukrida.root.ui.user.screens.groupmenus.dashboard.viewmodel.DashboardViewModelFactory
import org.ukrida.root.ui.user.screens.groupmenus.itinerary.screen.ItineraryScreen
import org.ukrida.root.ui.user.screens.groupmenus.itinerary.viewmodel.ItineraryViewModel
import org.ukrida.root.ui.user.screens.groupmenus.hymn.screen.HymnScreen
import org.ukrida.root.ui.user.screens.groupmenus.hymn.viewmodel.HymnViewModel
import org.ukrida.root.ui.user.screens.groupmenus.hymn.viewmodel.HymnViewModelFactory
import org.ukrida.root.ui.user.screens.groupmenus.hymn.screen.HymnDetailScreen
import org.ukrida.root.ui.user.screens.groupmenus.gallery.screen.GalleryScreen
import org.ukrida.root.ui.user.screens.groupmenus.gallery.viewmodel.GalleryViewModel
import org.ukrida.root.ui.user.screens.groupmenus.journal.screen.JournalScreen
import org.ukrida.root.ui.user.screens.groupmenus.journal.viewmodel.JournalViewModel
import org.ukrida.root.ui.user.screens.groupmenus.journal.screen.JournalEditorScreen
import org.ukrida.root.ui.user.screens.groupmenus.journal.viewmodel.JournalEditorViewModel
import org.ukrida.root.ui.user.screens.groupmenus.dailybread.screen.DailyBreadScreen
import org.ukrida.root.ui.user.screens.groupmenus.dailybread.viewmodel.DailyBreadViewModel
import org.ukrida.root.ui.user.screens.groupmenus.dailybread.screen.DailyBreadDetailScreen
import org.ukrida.root.ui.user.screens.groupmenus.dailybread.viewmodel.DailyBreadViewModelFactory
import org.ukrida.root.ui.user.screens.groupmenus.itinerary.viewmodel.ItineraryViewModelFactory
import org.ukrida.root.ui.user.screens.home.screen.HomeScreen
import org.ukrida.root.ui.user.screens.home.viewmodel.HomeViewModel
import org.ukrida.root.ui.user.screens.home.viewmodel.HomeViewModelFactory
import org.ukrida.root.ui.user.screens.promisedland.screen.PromisedLandScreen
import org.ukrida.root.ui.user.screens.promisedland.viewmodel.PromisedLandViewModel
import org.ukrida.root.ui.user.screens.promisedland.viewmodel.PromisedLandViewModelFactory
import org.ukrida.root.ui.user.screens.history.screen.HistoryScreen
import org.ukrida.root.ui.user.screens.history.viewmodel.HistoryViewModel
import org.ukrida.root.ui.user.screens.history.viewmodel.HistoryViewModelFactory
import org.ukrida.root.ui.user.screens.historydetail.screen.HistoryDetailScreen
import org.ukrida.root.ui.user.screens.historydetail.viewmodel.HistoryDetailViewModel
import org.ukrida.root.ui.user.screens.historydetail.viewmodel.HistoryDetailViewModelFactory
import org.ukrida.root.ui.user.screens.members.screen.MemberDetailScreen
import org.ukrida.root.ui.user.screens.members.screen.MemberScreen
import org.ukrida.root.ui.user.screens.members.viewmodel.MemberDetailViewModel
import org.ukrida.root.ui.user.screens.members.viewmodel.MemberViewModel
import org.ukrida.root.ui.user.screens.order.screen.OrderScreen
import org.ukrida.root.ui.user.screens.order.viewmodel.OrderViewModel
import org.ukrida.root.ui.user.screens.order.viewmodel.OrderViewModelFactory
import org.ukrida.root.ui.user.screens.profile.screen.ProfileScreen
import org.ukrida.root.ui.user.screens.profile.viewmodel.ProfileViewModel
import org.ukrida.root.ui.user.screens.profile.viewmodel.ProfileViewModelFactory

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

        // ==================== ROOT-LEVEL ROUTES ====================

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

        composable(PublicScreen.Group.route) {
            val factory = remember {
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

        composable(PublicScreen.History.route) {
            val factory = remember {
                HistoryViewModelFactory(appContainer.groupRepository)
            }
            val viewModel: HistoryViewModel = viewModel(factory = factory)
            HistoryScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable(
            route = PublicScreen.HistoryDetail.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            val factory = remember{
                HistoryDetailViewModelFactory(
                    appContainer.accountRepository,
                    appContainer.galleryRepository,
                    appContainer.memberRepository
                )
            }
            val viewModel: HistoryDetailViewModel = viewModel(factory = factory)
            HistoryDetailScreen(
                viewModel = viewModel,
                navController = navController,
                groupId = groupId
            )
        }

        composable(
            route = PublicScreen.Order.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            val factory = remember{
                OrderViewModelFactory(appContainer.groupRepository, appContainer.accountRepository)
            }
            val viewModel: OrderViewModel = viewModel(factory=factory)
            OrderScreen(
                viewModel = viewModel,
                navController = navController,
                groupId = groupId
            )
        }

        composable(PublicScreen.Profile.route) {
            val factory = remember {
                ProfileViewModelFactory(appContainer.profileRepository)
            }
            val viewModel: ProfileViewModel = viewModel(factory = factory)
            ProfileScreen(
                viewModel = viewModel,
                navController = navController,
                onLogout = onLogout
            )
        }

        // ==================== GROUP-SCOPED ROUTES (group/{groupId}/*) ====================

        composable(
            route = PublicScreen.Dashboard.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            val factory = remember {
                DashboardViewModelFactory(appContainer.accountRepository, appContainer.galleryRepository)
            }
            val viewModel: DashboardViewModel = viewModel(factory = factory)
            DashboardScreen(
                viewModel = viewModel,
                navController = navController,
                groupId = groupId
            )
        }

        composable(
            route = PublicScreen.Itinerary.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            val factory = remember{
                ItineraryViewModelFactory(appContainer.itineraryRepository)
            }
            val viewModel : ItineraryViewModel = viewModel(factory = factory)
            ItineraryScreen(
                viewModel = viewModel,
                navController = navController,
                groupId = groupId
            )
        }

        composable(
            route = PublicScreen.Hymn.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            val factory = remember {
                HymnViewModelFactory(appContainer.songRepository)
            }
            val viewModel: HymnViewModel = viewModel(factory = factory)
            HymnScreen(
                viewModel = viewModel,
                navController = navController,
                groupId = groupId
            )
        }

        composable(
            route = PublicScreen.HymnDetail.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType },
                navArgument("songId") { type = NavType.IntType }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            val songId = it.arguments?.getInt("songId") ?: 0
            val factory = remember {
                HymnViewModelFactory(appContainer.songRepository)
            }
            val viewModel: HymnViewModel = viewModel(factory = factory)
            HymnDetailScreen(
                viewModel = viewModel,
                navController = navController,
                groupId = groupId,
                songId = songId
            )
        }

        composable(
            route = PublicScreen.Gallery.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            // TODO: Create GalleryViewModelFactory if not exists
            val viewModel: GalleryViewModel = viewModel()
            GalleryScreen(
                viewModel = viewModel,
                navController = navController,
                groupId = groupId
            )
        }

        composable(
            route = PublicScreen.Journal.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            // TODO: Create JournalViewModelFactory if not exists
            val viewModel: JournalViewModel = viewModel()
            JournalScreen(
                viewModel = viewModel,
                navController = navController,
                groupId = groupId
            )
        }

        composable(
            route = PublicScreen.JournalEditor.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType },
                navArgument("journalId") { type = NavType.IntType }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            val journalId = it.arguments?.getInt("journalId") ?: -1
            // TODO: Create JournalEditorViewModelFactory if not exists
            val viewModel: JournalEditorViewModel = viewModel()
            JournalEditorScreen(
                viewModel = viewModel,
                navController = navController,
                groupId = groupId,
                journalId = journalId
            )
        }

        composable(
            route = PublicScreen.DailyBread.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            val factory = remember{
                DailyBreadViewModelFactory(appContainer.devotionRepository)
            }
            val viewModel: DailyBreadViewModel = viewModel(factory = factory)
            DailyBreadScreen(
                viewModel = viewModel,
                navController = navController,
                groupId = groupId
            )
        }

        composable(
            route = PublicScreen.DailyBreadDetail.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType },
                navArgument("date") { type = NavType.StringType }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            val date = it.arguments?.getString("date") ?: ""
            val factory = remember{
                DailyBreadViewModelFactory(appContainer.devotionRepository)
            }
            val viewModel: DailyBreadViewModel = viewModel(factory = factory)
            DailyBreadDetailScreen(
                viewModel = viewModel,
                navController = navController,
                groupId = groupId,
                date = date
            )
        }

        composable(
            route = PublicScreen.Members.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            // TODO: Create MemberViewModelFactory if not exists
            val viewModel: MemberViewModel = viewModel()
            MemberScreen(
                viewModel = viewModel,
                navController = navController,
                groupId = groupId
            )
        }

        composable(
            route = PublicScreen.MemberDetail.route,
            arguments = listOf(
                navArgument("groupId") { type = NavType.IntType },
                navArgument("userId") { type = NavType.IntType }
            )
        ) {
            val groupId = it.arguments?.getInt("groupId") ?: 0
            val userId = it.arguments?.getInt("userId") ?: 0
            // TODO: Create MemberDetailViewModelFactory if not exists
            val viewModel: MemberDetailViewModel = viewModel()
            MemberDetailScreen(
                viewModel = viewModel,
                navController = navController,
                groupId = groupId,
                userId = userId
            )
        }
    }
}
