package org.ukrida.root.data.repository

import org.json.JSONObject
import org.ukrida.root.data.model.ApiResponse
import org.ukrida.root.data.model.AuthData
import org.ukrida.root.data.model.LoginRequest
import org.ukrida.root.data.model.RegisterRequest
import org.ukrida.root.data.model.User
import org.ukrida.root.data.remote.ApiService
import retrofit2.Response

class AuthRepository(private val api: ApiService) {

    suspend fun login(identifier: String, password: String): Result<AuthData> = safeCall {
        api.login(LoginRequest(identifier, password))
    }

    suspend fun register(
        firstName: String, lastName: String, username: String,
        email: String, phone: String,
        password: String, passwordConfirmation: String
    ): Result<User> = safeCall {
        api.register(
            RegisterRequest(
                firstName = firstName,
                lastName = lastName,
                username = username,
                email = email,
                phone = phone,
                password = password,
                passwordConfirmation = passwordConfirmation
            )
        )
    }
}

// ─── Helpers ─────────────────────────────────────────────────────────

private suspend fun <T> safeCall(call: suspend () -> Response<ApiResponse<T>>): Result<T> {
    return try {
        val res = call()
        val body = res.body()
        if (res.isSuccessful && body?.success == true && body.data != null) {
            Result.success(body.data)
        } else if (body?.message != null) {
            // HTTP 2xx but backend reported failure (success = false)
            Result.failure(Exception(body.message))
        } else {
            // Non-2xx: extract the backend's "message" from errorBody
            Result.failure(Exception(extractErrorMessage(res)))
        }
    } catch (e: Exception) {
        Result.failure(Exception(e.message ?: "Network error. Please try again."))
    }
}

private suspend fun <T> safeCallList(call: suspend () -> Response<ApiResponse<List<T>>>): Result<List<T>> {
    return try {
        val res = call()
        val body = res.body()
        if (res.isSuccessful && body?.success == true) {
            Result.success(body.data ?: emptyList())
        } else if (body?.message != null) {
            Result.failure(Exception(body.message))
        } else {
            Result.failure(Exception(extractErrorMessage(res)))
        }
    } catch (e: Exception) {
        Result.failure(Exception(e.message ?: "Network error. Please try again."))
    }
}

/**
 * Parses the raw error body for a backend-provided "message" field.
 * Falls back to a generic HTTP error if none is found.
 */
private fun <T> extractErrorMessage(res: Response<T>): String {
    val raw = res.errorBody()?.string()
    if (!raw.isNullOrBlank()) {
        try {
            val msg = JSONObject(raw).optString("message")
            if (msg.isNotBlank()) return msg
        } catch (_: Exception) { /* body wasn't JSON */ }
    }
    return "Request failed (HTTP ${res.code()})"
}
