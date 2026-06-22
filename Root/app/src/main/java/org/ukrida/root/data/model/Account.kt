package org.ukrida.root.data.model

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("group_id") val groupId: Int
)

data class OrderResult(
    @SerializedName("account_id") val accountId: Int,
    @SerializedName("status_join") val statusJoin: String,
    @SerializedName("is_paid") val isPaid: Boolean
)

data class AccountStatus(
    @SerializedName("account_id") val accountId: Int,
    @SerializedName("group_name") val groupName: String,
    @SerializedName("status_join") val statusJoin: String,   // "pending" | "approved" | "rejected"
    @SerializedName("join_date") val joinDate: String?,
    @SerializedName("approved_date") val approvedDate: String?,
    @SerializedName("is_paid") val isPaid: Boolean
)

data class GroupPerson(
    val name: String,
    val photo: String?
)

data class GroupDetail(
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
    val mentor: GroupPerson,
    val coordinator: GroupPerson
)

// Reuse the standard envelope from ApiResponse.kt
// ApiResponse<T> { status: String, data: T?, message: String? }
