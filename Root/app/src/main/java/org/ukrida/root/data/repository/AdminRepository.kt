package org.ukrida.root.data.repository

import org.ukrida.root.data.model.AdminDevotion
import org.ukrida.root.data.model.AdminDevotionRequest
import org.ukrida.root.data.model.AdminDevotionUpdateRequest
import org.ukrida.root.data.model.AdminImageRemoveRequest
import org.ukrida.root.data.model.AdminMemberRemoveRequest
import org.ukrida.root.data.model.AdminOrderAction
import org.ukrida.root.data.model.AdminOrderResult
import org.ukrida.root.data.model.AdminSongAddResult
import org.ukrida.root.data.model.AdminSongAddToGroupRequest
import org.ukrida.root.data.model.AdminSongRequest
import org.ukrida.root.data.model.AdminSongUpdateRequest
import org.ukrida.root.data.model.AdminTripDeleteRequest
import org.ukrida.root.data.model.AdminTripRequest
import org.ukrida.root.data.model.AdminTripResult
import org.ukrida.root.data.model.AdminTripUpdateRequest
import org.ukrida.root.data.model.ApiResponse
import org.ukrida.root.data.model.CompletedTrip
import org.ukrida.root.data.model.PendingAccount
import org.ukrida.root.data.model.Song
import org.ukrida.root.data.remote.ApiService
import org.ukrida.root.utils.Resource
import retrofit2.Response

class AdminRepository(private val api: ApiService) {

    suspend fun getPendingAccounts(groupId: Int? = null): Result<List<PendingAccount>> = safeCallList {
        api.getPendingAccounts(groupId = groupId)
    }

    suspend fun getApprovalAccounts(groupId: Int? = null): Result<List<PendingAccount>> = safeCallList {
        api.getApprovalAccounts(groupId = groupId)
    }

    // ─── Trips ───────────────────────────────────────────────────────────────

    suspend fun getCompletedTrips(): Resource<List<CompletedTrip>> = safeCall {
        val res = api.adminGetTrips()

        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data ?: emptyList())
        else
            Resource.Error(res.body()?.message ?: "Failed to load trips")
    }

    suspend fun createTrip(request: AdminTripRequest): Resource<AdminTripResult> = safeCall {
        val res = api.adminCreateTrip(body = request)
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data!!)
        else
            Resource.Error(res.body()?.message ?: "Failed to create trip")
    }

    suspend fun updateTrip(request: AdminTripUpdateRequest): Resource<AdminTripResult> = safeCall {
        val res = api.adminUpdateTrip(body = request)
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data!!)
        else
            Resource.Error(res.body()?.message ?: "Failed to update trip")
    }

    // ─── Orders ───────────────────────────────────────────────────────────────

    suspend fun approveOrder(accountId: Int): Resource<AdminOrderResult> = safeCall {
        val res = api.adminApproveOrder(body = AdminOrderAction(accountId))
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data!!)
        else
            Resource.Error(res.body()?.message ?: "Failed to approve order")
    }

    suspend fun rejectOrder(accountId: Int): Resource<AdminOrderResult> = safeCall {
        val res = api.adminRejectOrder(body = AdminOrderAction(accountId))
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data!!)
        else
            Resource.Error(res.body()?.message ?: "Failed to reject order")
    }

    // ─── Members ──────────────────────────────────────────────────────────────

    suspend fun removeMember(userId: Int, groupId: Int): Resource<Unit> = safeCall {
        val res = api.adminRemoveMember(body = AdminMemberRemoveRequest(userId, groupId))
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(Unit)
        else
            Resource.Error(res.body()?.message ?: "Failed to remove member")
    }

    // ─── Gallery ──────────────────────────────────────────────────────────────

    suspend fun removeImage(imageId: Int): Resource<Unit> = safeCall {
        val res = api.adminRemoveImage(body = AdminImageRemoveRequest(imageId))
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(Unit)
        else
            Resource.Error(res.body()?.message ?: "Failed to remove image")
    }

    // ─── Songs ────────────────────────────────────────────────────────────────

    suspend fun createSong(
        title: String,
        author: String?,
        lyrics: String?
    ): Resource<Song> = safeCall {
        val res = api.adminCreateSong(body = AdminSongRequest(title, author, lyrics))
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data!!)
        else
            Resource.Error(res.body()?.message ?: "Failed to create song")
    }
    suspend fun updateSong(
        id: Int,
        title: String,
        author: String?,
        lyrics: String?
    ): Resource<Song> = safeCall {
        val res = api.adminUpdateSong(
            body = AdminSongUpdateRequest(
                id = id,
                title = title,
                author = author,
                lyrics = lyrics
            )
        )

        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data!!)
        else
            Resource.Error(res.body()?.message ?: "Failed to update song")
    }


    suspend fun addSongToGroup(
        songId: Int,
        groupId: Int,
        itenaryId: Int?,
        sortOrder: Int
    ): Resource<AdminSongAddResult> = safeCall {
        val res = api.adminAddSongToGroup(
            body = AdminSongAddToGroupRequest(songId, groupId, itenaryId, sortOrder)
        )
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data!!)
        else
            Resource.Error(res.body()?.message ?: "Failed to assign song")
    }

    // ─── Devotions ────────────────────────────────────────────────────────────

    suspend fun createDevotion(
        groupId: Int,
        devotionDate: String,
        title: String,
        content: String,
        scriptureRef: String?
    ): Resource<AdminDevotion> = safeCall {
        val res = api.adminCreateDevotion(
            body = AdminDevotionRequest(groupId, devotionDate, title, content, scriptureRef)
        )
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data!!)
        else
            Resource.Error(res.body()?.message ?: "Failed to create devotion")
    }

    suspend fun updateDevotion(
        id: Int,
        groupId: Int,
        devotionDate: String,
        title: String,
        content: String,
        scriptureRef: String?
    ): Resource<AdminDevotion> = safeCall {
        val res = api.adminUpdateDevotion(
            body = AdminDevotionUpdateRequest(id, groupId, devotionDate, title, content, scriptureRef)
        )
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data!!)
        else
            Resource.Error(res.body()?.message ?: "Failed to update devotion")
    }

    // ─── Shared error wrapper ─────────────────────────────────────────────────

    private suspend fun <T> safeCall(block: suspend () -> Resource<T>): Resource<T> {
        return try {
            block()
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }

    private suspend fun <T> safeCallList(call: suspend () -> Response<ApiResponse<List<T>>>): Result<List<T>> {
        return try {
            val res = call()
            if (res.isSuccessful && res.body()?.success == true) {
                Result.success(res.body()?.data ?: emptyList())
            } else {
                Result.failure(Exception(res.body()?.message ?: "Error fetching pending accounts"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun deleteTrip(id: Int): Result<Unit> {
        return try {

            val response = api.adminDeleteTrip(
                route = "admin/trip/delete",
                body = AdminTripDeleteRequest(id)
            )

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Delete failed: ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}