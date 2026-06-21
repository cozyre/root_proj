package org.ukrida.root.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val message: String?
)

data class AuthData(
    val token: String,
    val user: User
)

data class User(
    val id: Int,
    val username: String,
    val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("profile_photo_url") val profilePhotoUrl: String?,
    val phone: String?,
    val role: String
)

data class LoginRequest(
    val identifier: String,
    val password: String
)

data class RegisterRequest(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
    @SerializedName("password_confirmation") val passwordConfirmation: String
)