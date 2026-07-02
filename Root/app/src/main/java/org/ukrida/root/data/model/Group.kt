package org.ukrida.root.data.model

import com.google.gson.annotations.SerializedName

data class Group(
    val id: Int,
    val name: String,
    val description: String?,
    val location: String?,
    val dresscode: String?,
    val price: Int,
    val status: String,
    @SerializedName("start_date") val startDate: String?,
    @SerializedName("end_date") val endDate: String?,
    @SerializedName("meetup_time") val meetupTime: String?,
    @SerializedName("meetup_address") val meetupAddress: String?,
    // Only present in history response
    @SerializedName("join_date") val joinDate: String? = null,
    @SerializedName("status_join") val statusJoin: String? = null
)

data class GroupWithDetails(
    val id: Int,
    val name: String,
    val description: String?,
    @SerializedName("start_date") val startDate: String?,
    @SerializedName("end_date") val endDate: String?,
    val location: String?,
    val dresscode: String?,
    @SerializedName("meetup_time") val meetupTime: String?,
    @SerializedName("meetup_address") val meetupAddress: String?,
    val status: String,
    @SerializedName("status_join") val statusJoin: String,  // from Group (pending/approved/rejected)
    @SerializedName("join_date") val joinDate: String?
)

data class GroupResponse(
    val success: Boolean,
    val data: List<Group>?,
    val message: String?
)

data class GroupSingleResponse(
    val success: Boolean,
    val data: Group?,
    val message: String?
)