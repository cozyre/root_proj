package org.ukrida.root.data.repository

import org.ukrida.root.data.model.AuthData
import org.ukrida.root.data.model.LoginRequest
import org.ukrida.root.data.model.RegisterRequest
import org.ukrida.root.data.model.User
import org.ukrida.root.data.remote.ApiService
import org.ukrida.root.utils.Resource

class AuthRepository(private val api: ApiService) {

    suspend fun login(identifier: String, password: String): Resource<AuthData> {
        return try {
            val response = api.login(LoginRequest(identifier, password))
            if (response.success && response.data != null)
                Resource.Success(response.data)
            else
                Resource.Error(response.message ?: "Login failed")
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }

    suspend fun register(
        firstName: String, lastName: String, username: String,
        email: String, phone: String,
        password: String, passwordConfirmation: String
    ): Resource<User> {
        return try {
            val response = api.register(
                RegisterRequest(firstName, lastName, username, email, phone, password, passwordConfirmation)
            )
            if (response.success && response.data != null)
                Resource.Success(response.data)
            else
                Resource.Error(response.message ?: "Registration failed")
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }
}