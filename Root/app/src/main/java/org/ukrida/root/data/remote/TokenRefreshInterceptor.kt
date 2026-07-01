package org.ukrida.root.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import org.ukrida.root.utils.SessionManager
import android.util.Log
import kotlinx.coroutines.runBlocking

class TokenRefreshInterceptor(private val sessionManager: SessionManager) : Interceptor {

    private val TAG = "TokenRefreshInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        var response = chain.proceed(originalRequest)

        // If 401, try refresh once. If still fails, logout.
        if (response.code == 401) {
            Log.d(TAG, "Got 401, attempting token refresh...")

            synchronized(this) {  // Prevent multiple simultaneous refresh attempts
                val refreshed = attemptRefresh()

                if (refreshed) {
                    // Retry original request with new token
                    val newToken = sessionManager.getToken()
                    if (newToken != null) {
                        val retryRequest = originalRequest.newBuilder()
                            .removeHeader("Authorization")
                            .addHeader("Authorization", "Bearer $newToken")
                            .build()
                        response.close()
                        return chain.proceed(retryRequest)
                    }
                }

                // Refresh failed or no token — logout
                Log.d(TAG, "Refresh failed or no token, clearing session")
                sessionManager.logout()
            }
        }

        return response
    }

    private fun attemptRefresh(): Boolean {
        return try {
            // Use runBlocking to call suspend function from non-suspend context
            val success = runBlocking {
                try {
                    val apiService = RetrofitClient.instance
                    val res = apiService.refreshToken()

                    if (res.isSuccessful && res.body()?.success == true) {
                        val newToken = (res.body()?.data?.get("token") as? String)
                        if (newToken != null) {
                            sessionManager.saveToken(newToken)
                            Log.d(TAG, "Token refreshed in interceptor")
                            return@runBlocking true
                        }
                    }
                    false
                } catch (e: Exception) {
                    Log.e(TAG, "Refresh error: ${e.message}")
                    false
                }
            }
            success
        } catch (e: Exception) {
            Log.e(TAG, "runBlocking error: ${e.message}")
            false
        }
    }
}