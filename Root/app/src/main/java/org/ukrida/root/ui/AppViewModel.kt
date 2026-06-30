package org.ukrida.root.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.ukrida.root.data.remote.RetrofitClient
import org.ukrida.root.utils.SessionManager
import android.util.Log

class AppViewModel(private val sessionManager: SessionManager) : ViewModel() {

    private val TAG = "AppViewModel"

    init {
        startTokenRefreshSchedule()
    }

    /**
     * Every 15 minutes, attempt to refresh the token.
     * Stops when ViewModel is cleared (app closes or user logs out).
     */
    private fun startTokenRefreshSchedule() {
        viewModelScope.launch {
            Log.d(TAG, "Token refresh schedule started")

            while (isActive) {
                delay(15 * 60 * 1000)  // 15 minutes

                if (!sessionManager.isLoggedIn()) {
                    Log.d(TAG, "Not logged in, stopping refresh schedule")
                    break
                }

                if (sessionManager.isLoginExpired()) {
                    Log.d(TAG, "Login expired (7 days), logging out")
                    sessionManager.logout()
                    break
                }

                refreshToken()
            }
        }
    }

    private suspend fun refreshToken() {
        try {
            val currentToken = sessionManager.getToken() ?: return

            Log.d(TAG, "Proactive token refresh attempt...")
            val apiService = RetrofitClient.instance
            val response = apiService.refreshToken()

            if (response.isSuccessful && response.body()?.success == true) {
                val newToken = (response.body()?.data?.get("token") as? String)
                if (newToken != null) {
                    sessionManager.saveToken(newToken)
                    Log.d(TAG, "Token refreshed successfully (proactive)")
                }
            } else {
                Log.w(TAG, "Proactive refresh failed: ${response.body()?.message}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Token refresh error: ${e.message}")
        }
    }
}