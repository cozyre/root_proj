package org.ukrida.root.ui.Public.screens.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.ukrida.root.ui.Public.screens.login.screen.LoginScreen
import org.ukrida.root.ui.Public.screens.login.screen.RegisterScreen

@Composable
fun AuthNavigation(){
    val navController = rememberNavController()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2A2522))
    ) {
        NavHost(
            navController,
            startDestination = "login"
        ) {
            composable("login") {
                LoginScreen(
                    onRegisterClick = {
                        navController.navigate("register")
                    }
                )
            }
            composable("register") {
                RegisterScreen(
                    onBackLogin = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}