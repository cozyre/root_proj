package org.ukrida.root.data.repository

import org.ukrida.root.data.model.BroadcastRequest
import org.ukrida.root.data.model.BroadcastResult
import org.ukrida.root.data.model.MarkReadRequest
import org.ukrida.root.data.model.NotificationItem
import org.ukrida.root.data.remote.ApiService

class NotificationRepository(private val api: ApiService) {
    suspend fun getNotifications(): Result<List<NotificationItem>> = try {
        val res = api.listNotifications()
        if (res.isSuccessful && res.body()?.status == "success") {
            Result.success(res.body()?.data ?: emptyList())
        } else {
            Result.failure(Exception("Failed to load notifications"))
        }
    } catch (e: Exception) { Result.failure(e) }

    suspend fun broadcast(groupId: Int, message: String): Result<BroadcastResult> = try {
        val res = api.broadcast(body = BroadcastRequest(groupId, message))
        val data = res.body()?.data
        if (res.isSuccessful && res.body()?.status == "success" && data != null) {
            Result.success(data)
        } else {
            Result.failure(Exception("Broadcast failed"))
        }
    } catch (e: Exception) { Result.failure(e) }

    suspend fun markRead(notificationId: Int): Result<Unit> = try {
        val res = api.markRead(body = MarkReadRequest(notificationId))
        if (res.isSuccessful) Result.success(Unit) else Result.failure(Exception("Failed"))
    } catch (e: Exception) { Result.failure(e) }
}