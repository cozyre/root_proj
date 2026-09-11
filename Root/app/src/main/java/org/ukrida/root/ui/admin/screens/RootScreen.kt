package org.ukrida.root.ui.admin.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.ukrida.root.data.AppContainer
import org.ukrida.root.ui.admin.navigation.AppNavigation
import org.ukrida.root.ui.admin.components.AppDrawer
import org.ukrida.root.ui.theme.DarkBrown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(appContainer: AppContainer, onLogout: () -> Unit) {

    val drawerState =
        rememberDrawerState(
            initialValue = DrawerValue.Closed
        )

    val scope = rememberCoroutineScope()

    val navController =
        rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(

                onItemClick = {
                    navController.navigate(it)
                    scope.launch { drawerState.close() }
                },

                onCloseDrawer = {
                    scope.launch { drawerState.close() }
                },
                onLogout = onLogout
            )
        }
    ) {

        Scaffold(

            containerColor = DarkBrown

        ) { padding ->

            AppNavigation(
                navController = navController,
                modifier = Modifier.padding(padding),

                onMenuClick = {

                    scope.launch {
                        drawerState.open()
                    }
                },
                appContainer = appContainer
            )
        }
    }
}