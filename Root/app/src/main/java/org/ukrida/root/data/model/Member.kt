package org.ukrida.root.data.model

import com.google.gson.annotations.SerializedName

/** Used for the member directory list */
data class Member(
    val id: Int,
    val username: String,
    @SerializedName("first_name")        val firstName: String,
    @SerializedName("last_name")         val lastName: String,
    @SerializedName("profile_photo_url") val profilePhotoUrl: String?,
    val role: String
) {
    val fullName: String get() = "$firstName $lastName"
}

/** Used for the member detail screen */
data class MemberDetail(
    val id: Int,
    val username: String,
    @SerializedName("first_name")        val firstName: String,
    @SerializedName("last_name")         val lastName: String,
    val email: String?,
    val phone: String?,
    @SerializedName("profile_photo_url") val profilePhotoUrl: String?,
    val role: String,
    @SerializedName("status_join")       val statusJoin: String,
    @SerializedName("join_date")         val joinDate: String,
    val bio: String?
) {
    val fullName: String get() = "$firstName $lastName"
}