package org.ukrida.root.data.repository

import org.ukrida.root.data.model.ApiResponse
import org.ukrida.root.data.model.Itinerary
import org.ukrida.root.data.model.ItineraryDate
import org.ukrida.root.data.remote.ApiService
import retrofit2.Response

class ItineraryRepository(private val api: ApiService) {

    suspend fun getItinerary(groupId: Int, date: String): Result<Itinerary> = safeCall {
        api.getItinerary(groupId = groupId, date = date)
    }

    suspend fun getItineraryDates(groupId: Int): Result<List<ItineraryDate>> = safeCallList {
        api.getItineraryDates(groupId = groupId)
    }

    // ─── Helpers ─────────────────────────────────────────────────────────

    private suspend fun <T> safeCall(call: suspend () -> Response<ApiResponse<T>>): Result<T> {
        return try {
            val res = call()
            if (res.isSuccessful && res.body()?.success == true && res.body()?.data != null) {
                Result.success(res.body()!!.data!!)
            } else {
                Result.failure(Exception(res.body()?.message ?: "Error loading itinerary"))
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
                Result.failure(Exception(res.body()?.message ?: "Error loading itinerary dates"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
