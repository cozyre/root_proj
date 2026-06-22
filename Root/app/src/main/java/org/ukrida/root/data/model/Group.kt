package org.ukrida.root.data.model

import com.google.gson.annotations.SerializedName
data class Group(
    val id: Int,
    val name: String,
    val description: String?,
    val location: String?,
    val dresscode: String?,
    val status: String,
    @SerializedName("start_date") val startDate: String?,
    @SerializedName("end_date") val endDate: String?,
    @SerializedName("meetup_time") val meetupTime: String?,
    @SerializedName("meetup_address") val meetupAddress: String?,
    // Only present in history response
    @SerializedName("join_date") val joinDate: String? = null,
    @SerializedName("status_join") val statusJoin: String? = null
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