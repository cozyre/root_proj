package org.ukrida.root.data.repository

import org.ukrida.root.data.model.Member
import org.ukrida.root.data.model.MemberDetail
import org.ukrida.root.data.remote.ApiService
import org.ukrida.root.utils.Resource

class MemberRepository(private val api: ApiService) {

    suspend fun listMembers(groupId: Int): Resource<List<Member>> = safeCall {
        val res = api.listMembers(groupId = groupId)
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data ?: emptyList())
        else
            Resource.Error(res.body()?.message ?: "Failed to load members")
    }

    suspend fun getMemberDetail(userId: Int, groupId: Int): Resource<MemberDetail> = safeCall {
        val res = api.getMemberDetail(userId = userId, groupId = groupId)
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data!!)
        else
            Resource.Error(res.body()?.message ?: "Member not found")
    }

    private suspend fun <T> safeCall(block: suspend () -> Resource<T>): Resource<T> {
        return try {
            block()
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }
}