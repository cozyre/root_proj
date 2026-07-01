package org.ukrida.root.utils

import android.content.Context
import androidx.core.content.edit
import java.time.Instant

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("pilgrimmate_prefs", Context.MODE_PRIVATE)

    // ─── Token Management ─────────────────────────────────────────────

    fun saveToken(token: String) = prefs.edit { putString(KEY_TOKEN, token) }
    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)
    fun clearToken() = prefs.edit { remove(KEY_TOKEN) }

    // ─── Role Management ─────────────────────────────────────────────

    fun saveRole(role: String) = prefs.edit { putString(KEY_ROLE, role) }
    fun getRole(): String? = prefs.getString(KEY_ROLE, null)
    fun clearRole() = prefs.edit { remove(KEY_ROLE) }

    // ─── Login Timestamp (for 7-day absolute expiry) ──────────────────

    fun saveLoginTime() {
        val now = System.currentTimeMillis()
        prefs.edit { putLong(KEY_LOGIN_TIME, now) }
    }

    fun getLoginTime(): Long = prefs.getLong(KEY_LOGIN_TIME, 0L)

    fun isLoginExpired(): Boolean {
        val loginTime = getLoginTime()
        if (loginTime == 0L) return true

        val sevenDaysMs = 7 * 24 * 60 * 60 * 1000L
        val elapsed = System.currentTimeMillis() - loginTime
        return elapsed > sevenDaysMs
    }

    fun isLoggedIn(): Boolean = getToken() != null && !isLoginExpired()

    // ─── Full Logout ──────────────────────────────────────────────────

    fun logout() {
        prefs.edit {
            remove(KEY_TOKEN)
            remove(KEY_ROLE)
            remove(KEY_LOGIN_TIME)
        }
    }

    companion object {
        private const val KEY_TOKEN       = "jwt_token"
        private const val KEY_ROLE        = "user_role"
        private const val KEY_LOGIN_TIME  = "login_timestamp"
    }
}