package org.ukrida.root.data.model

import com.google.gson.annotations.SerializedName

/** What the API returns for GET profile/get and POST profile/update */
data class Profile(
    val id: Int,
    val username: String,
    @SerializedName("first_name")        val firstName: String,
    @SerializedName("last_name")         val lastName: String,
    val email: String,
    val phone: String?,
    @SerializedName("profile_photo_url") val profilePhotoUrl: String?,
    val bio: String?,
    @SerializedName("hide_phone")        val hidePhone: Boolean,
    val role: String,
    @SerializedName("created_at")        val createdAt: String
) {
    val fullName: String get() = "$firstName $lastName"
}

/** Body for POST profile/update */
data class UpdateProfileRequest(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name")  val lastName: String,
    val username: String,
    val phone: String?,
    val bio: String?,
    @SerializedName("hide_phone") val hidePhone: Boolean
)