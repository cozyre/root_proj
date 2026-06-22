package org.ukrida.root.data.repository

import org.ukrida.root.data.model.CreateJournalRequest
import org.ukrida.root.data.model.DeleteJournalRequest
import org.ukrida.root.data.model.Journal
import org.ukrida.root.data.model.UpdateJournalRequest
import org.ukrida.root.data.remote.ApiService
import org.ukrida.root.utils.Resource

class JournalRepository(private val api: ApiService) {

    suspend fun createJournal(
        groupId: Int,
        title: String,
        content: String,
        journalDate: String
    ): Resource<Journal> = safeCall {
        val res = api.createJournal(
            body = CreateJournalRequest(groupId, title, content, journalDate)
        )
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data!!)
        else
            Resource.Error(res.body()?.message ?: "Failed to create journal")
    }

    suspend fun listJournals(groupId: Int): Resource<List<Journal>> = safeCall {
        val res = api.listJournals(groupId = groupId)
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data ?: emptyList())
        else
            Resource.Error(res.body()?.message ?: "Failed to load journals")
    }

    suspend fun updateJournal(
        id: Int,
        title: String,
        content: String,
        journalDate: String
    ): Resource<Journal> = safeCall {
        val res = api.updateJournal(
            body = UpdateJournalRequest(id, title, content, journalDate)
        )
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(res.body()!!.data!!)
        else
            Resource.Error(res.body()?.message ?: "Failed to update journal")
    }

    suspend fun deleteJournal(id: Int): Resource<Unit> = safeCall {
        val res = api.deleteJournal(body = DeleteJournalRequest(id))
        if (res.isSuccessful && res.body()?.success == true)
            Resource.Success(Unit)
        else
            Resource.Error(res.body()?.message ?: "Failed to delete journal")
    }

    private suspend fun <T> safeCall(block: suspend () -> Resource<T>): Resource<T> {
        return try {
            block()
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.message}")
        }
    }
}