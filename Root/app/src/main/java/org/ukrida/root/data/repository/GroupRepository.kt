package org.ukrida.root.data.repository

import org.ukrida.root.data.model.AdminTripDeleteRequest
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.model.GroupResponse
import org.ukrida.root.data.model.GroupSingleResponse
import org.ukrida.root.data.remote.ApiService
import retrofit2.Response

class GroupRepository(private val api: ApiService) {

    suspend fun getAllTours(): Result<List<Group>> = safeCall {
        api.getAllTours()
    }

    suspend fun getTourById(id: Int): Result<Group> = safeCall(single = true) {
        api.getTourById(id = id)
    }

    suspend fun getPastTours(limit: Int = 10): Result<List<Group>> = safeCall {
        api.getPastTours(limit = limit)
    }

    // Generic helpers
    private suspend fun safeCall(call: suspend () -> Response<GroupResponse>): Result<List<Group>> {
        return try {
            val res = call()
            if (res.isSuccessful && res.body()?.success == true) {
                Result.success(res.body()!!.data ?: emptyList())
            } else {
                Result.failure(Exception(res.body()?.message ?: "Error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun safeCall(single: Boolean, call: suspend () -> Response<GroupSingleResponse>): Result<Group> {
        return try {
            val res = call()
            if (res.isSuccessful && res.body()?.success == true && res.body()?.data != null) {
                Result.success(res.body()!!.data!!)
            } else {
                Result.failure(Exception(res.body()?.message ?: "Not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}