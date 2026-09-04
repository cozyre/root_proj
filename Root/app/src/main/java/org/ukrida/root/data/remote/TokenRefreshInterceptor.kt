package org.ukrida.root.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import org.ukrida.root.utils.SessionManager
import android.util.Log
import kotlinx.coroutines.runBlocking

class TokenRefreshInterceptor(private val sessionManager: SessionManager) : Interceptor {

    private val TAG = "TokenRefreshInterceptor"

    // Routes that should NEVER trigger a refresh-retry — they either have no
    // token yet (login/register) or ARE the refresh call itself (avoids recursion).
    private val excludedRoutes = setOf(
        "auth/login",
        "auth/register",
        "auth/refresh"
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        val route = originalRequest.url.queryParameter("route")

        // Bail out early for auth endpoints — a 401 here means bad credentials
        // or an invalid/missing refresh token, not an expired session.
        if (route in excludedRoutes) {
            return response
        }

        if (response.code == 401) {
            Log.d(TAG, "Got 401, attempting token refresh...")

            synchronized(this) {
                val refreshed = attemptRefresh()

                if (refreshed) {
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

                Log.d(TAG, "Refresh failed or no token, clearing session")
                sessionManager.logout()
            }
        }

        return response
    }

    private fun attemptRefresh(): Boolean {
        return try {
            runBlocking {
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
        } catch (e: Exception) {
            Log.e(TAG, "runBlocking error: ${e.message}")
            false
        }
    }
}