package org.ukrida.root.data.repository

import org.ukrida.root.data.model.ApiResponse
import org.ukrida.root.data.model.Devotion
import org.ukrida.root.data.model.DevotionDate
import org.ukrida.root.data.remote.ApiService
import retrofit2.Response

class DevotionRepository(private val api: ApiService) {

    suspend fun getDevotion(groupId: Int, date: String): Result<Devotion> = safeCall {
        api.getDevotion(groupId = groupId, date = date)
    }

    suspend fun getDevotionDates(groupId: Int): Result<List<DevotionDate>> = safeCallList {
        api.getDevotionDates(groupId = groupId)
    }

    // ─── Helpers ─────────────────────────────────────────────────────────

    private suspend fun <T> safeCall(call: suspend () -> Response<ApiResponse<T>>): Result<T> {
        return try {
            val res = call()
            if (res.isSuccessful && res.body()?.success == true && res.body()?.data != null) {
                Result.success(res.body()!!.data!!)
            } else {
                Result.failure(Exception(res.body()?.message ?: "Error fetching devotion"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun <T> safeCallList(call: suspend () -> Response<ApiResponse<List<T>>>): Result<List<T>> {
        return try {
            val res = call()
            if (res.isSuccessful && res.body()?.success == true) {
                Result.success(res.body()?.data ?: emptyList())
            } else {
                Result.failure(Exception(res.body()?.message ?: "Error fetching dates"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
