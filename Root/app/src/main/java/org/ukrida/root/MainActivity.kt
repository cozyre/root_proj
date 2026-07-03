package org.ukrida.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.ukrida.root.data.remote.RetrofitClient
import org.ukrida.root.ui.theme.RootTheme
import org.ukrida.root.ui.AppViewModel
import org.ukrida.root.utils.SessionManager
import kotlinx.coroutines.delay
import android.util.Log
import org.ukrida.root.data.AppContainer
import org.ukrida.root.ui.admin.screens.RootScreen
import org.ukrida.root.ui.user.login.components.AuthNavigation
import org.ukrida.root.ui.user.screens.PublicRootScreen
import androidx.compose.ui.platform.LocalContext
import org.ukrida.root.ui.admin.components.LoadingScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize RetrofitClient BEFORE setContent
        RetrofitClient.initialize(this)

        enableEdgeToEdge()
        setContent {
            RootTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(activity = this@MainActivity)
                }
            }
        }
    }
}

@Composable
fun MainScreen(activity: MainActivity) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val appContainer = remember { AppContainer() }
    val navController = rememberNavController()

    // AppViewModel keeps token refresh running while screen exists
    val appViewModel = remember {
        AppViewModel(sessionManager = sessionManager)
    }

    var currentRoute by remember { mutableStateOf<String?>(null) }
    var isLogoutTriggered by remember { mutableStateOf(false) }
    val onLogout = {
        appViewModel.logout()
        currentRoute = "auth"
    }

    // ─── Initial Navigation ──────────────────────────────────────────

    LaunchedEffect(Unit) {
        if (sessionManager.isLoggedIn()) {
            val role = sessionManager.getRole()
            currentRoute = when (role) {
                "admin" -> "admin_root"
                else -> "public_root"
            }
            Log.d("MainScreen", "User logged in with role: $role")
        } else {
            currentRoute = "auth"
            Log.d("MainScreen", "User not logged in, showing auth")
        }
    }

    // ─── Monitor Session (Detect Logout from Interceptor) ────────────

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)  // Check every second

            // If session becomes invalid and we're not already showing auth
            if (!sessionManager.isLoggedIn() && currentRoute != "auth" && !isLogoutTriggered) {
                Log.d("MainScreen", "Session expired, logging out")
                isLogoutTriggered = true
                currentRoute = "auth"
                break
            }
        }
    }

    // ─── Navigation State ────────────────────────────────────────────

    when (currentRoute) {
        "auth" -> {
            AuthNavigation(
                navController = navController,
                sessionManager = sessionManager,
                onLoginSuccess = {
                    val role = sessionManager.getRole()
                    isLogoutTriggered = false
                    currentRoute = when (role) {
                        "admin" -> "admin_root"
                        else -> "public_root"
                    }
                    Log.d("MainScreen", "Login successful, navigating to: $currentRoute")
                }
            )
        }

        "admin_root" -> {
            RootScreen(appContainer = appContainer, onLogout)
        }

        "public_root" -> {
            PublicRootScreen(appContainer = appContainer, onLogout)
        }

        null -> {
            LoadingScreen()
        }
    }

    // Keep AppViewModel reference alive
    LaunchedEffect(appViewModel) {
        // ViewModel exists and keeps refresh schedule running
    }
}