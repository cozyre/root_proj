package org.ukrida.root.ui.public.login.components

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.ukrida.root.data.remote.RetrofitClient
import org.ukrida.root.data.repository.AuthRepository
import org.ukrida.root.ui.user.screens.login.screen.LoginScreen
import org.ukrida.root.ui.user.screens.login.screen.RegisterScreen
import org.ukrida.root.ui.user.screens.login.viewmodel.LoginViewModel
import org.ukrida.root.ui.user.screens.login.viewmodel.LoginViewModelFactory
import org.ukrida.root.ui.user.screens.login.viewmodel.RegisterViewModel
import org.ukrida.root.ui.user.screens.login.viewmodel.RegisterViewModelFactory
import org.ukrida.root.utils.SessionManager

@Composable
fun AuthNavigation(
    navController: NavHostController,
    sessionManager: SessionManager,
    onLoginSuccess: () -> Unit = {}
) {
    val authRepository = AuthRepository(RetrofitClient.instance)

    // Separate ViewModels with factories
    val loginViewModelFactory = LoginViewModelFactory(authRepository, sessionManager)
    val loginViewModel = viewModel<LoginViewModel>(factory = loginViewModelFactory)

    val registerViewModelFactory = RegisterViewModelFactory(authRepository)
    val registerViewModel = viewModel<RegisterViewModel>(factory = registerViewModelFactory)

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = {
                    onLoginSuccess()
                    navController.popBackStack()
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                viewModel = registerViewModel,
                onRegisterSuccess = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}