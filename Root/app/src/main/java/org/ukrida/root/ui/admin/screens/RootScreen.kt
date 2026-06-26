package org.ukrida.root.ui.admin.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.ukrida.root.ui.admin.navigation.AppNavigation
import org.ukrida.root.ui.admin.components.AppDrawer
import org.ukrida.root.ui.admin.navigation.AppNavigation
import org.ukrida.root.ui.theme.BackgroundDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen() {

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

                    scope.launch {
                        drawerState.close()
                    }
                },

                onCloseDrawer = {

                    scope.launch {
                        drawerState.close()
                    }
                }
            )
        }
    ) {

        Scaffold(

            containerColor = BackgroundDark

        ) { padding ->

            AppNavigation(
                navController = navController,
                modifier = Modifier.padding(padding),

                onMenuClick = {

                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    }
}