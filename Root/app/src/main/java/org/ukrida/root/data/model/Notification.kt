package org.ukrida.root.data.model

import com.google.gson.annotations.SerializedName

// Data classes
data class NotificationItem(
    val id: Int,
    val message: String,
    @SerializedName("group_id") val groupId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("is_read") val isRead: Int
)
data class NotificationListResponse(val status: String, val data: List<NotificationItem>?)

data class BroadcastRequest(val groupId: Int, val message: String)
data class BroadcastResult(val notificationId: Int, val recipientCount: Int)
data class BroadcastResponse(val status: String, val data: BroadcastResult?)

data class MarkReadRequest(val notificationId: Int)
data class GenericResponse(val status: String, val message: String? = null)