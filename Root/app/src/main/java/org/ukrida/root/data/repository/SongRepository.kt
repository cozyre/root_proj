package org.ukrida.root.data.repository

import org.ukrida.root.data.model.AddSongToGroupRequest
import org.ukrida.root.data.model.ApiResponse
import org.ukrida.root.data.model.RemoveSongFromGroupRequest
import org.ukrida.root.data.model.Song
import org.ukrida.root.data.model.SongBrowseItem
import org.ukrida.root.data.model.SongSummary
import org.ukrida.root.data.remote.ApiService
import retrofit2.Response

class SongRepository(private val api: ApiService) {

    suspend fun getSongs(groupId: Int): Result<List<SongSummary>> = safeCallList {
        api.getSongs(groupId = groupId)
    }

    suspend fun getSongsByDate(groupId: Int, date: String): Result<List<SongSummary>> = safeCallList {
        api.getSongsByDate(groupId = groupId, date = date)
    }

    suspend fun getSong(id: Int): Result<Song> = safeCall {
        api.getSong(id = id)
    }

    suspend fun searchSongs(groupId: Int, query: String): Result<List<SongSummary>> = safeCallList {
        api.searchSongs(groupId = groupId, query = query)
    }

    suspend fun browseSongs(query: String = ""): Result<List<SongBrowseItem>> = safeCallList {
        api.browseSongs(query = query)
    }

    suspend fun addSongToGroup(
        request: AddSongToGroupRequest
    ): Result<Unit> = safeCall {
        api.addSongToGroup(body = request)
    }

    suspend fun removeSongFromGroup(
        request: RemoveSongFromGroupRequest
    ): Result<Unit> = safeCall {
        api.removeSongFromGroup(body = request)
    }

    // ─── Helpers ─────────────────────────────────────────────────────────

    private suspend fun <T> safeCall(call: suspend () -> Response<ApiResponse<T>>): Result<T> {
        return try {
            val res = call()
            if (res.isSuccessful && res.body()?.success == true && res.body()?.data != null) {
                Result.success(res.body()!!.data!!)
            } else {
                Result.failure(Exception(res.body()?.message ?: "Error loading song"))
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
                Result.failure(Exception(res.body()?.message ?: "Error getting song list"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
