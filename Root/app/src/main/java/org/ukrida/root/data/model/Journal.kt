package org.ukrida.root.data.model

import com.google.gson.annotations.SerializedName

// ─── Response model (what the API returns) ───────────────────────────────

data class Journal(
    val id: Int,
    @SerializedName("user_id")     val userId: Int,
    @SerializedName("group_id")    val groupId: Int,
    val title: String,
    val content: String,
    @SerializedName("journal_date") val journalDate: String,
    @SerializedName("created_at")   val createdAt: String,
    @SerializedName("updated_at")   val updatedAt: String
)

// ─── Request models (what we send) ───────────────────────────────────────

data class CreateJournalRequest(
    @SerializedName("group_id")    val groupId: Int,
    val title: String,
    val content: String,
    @SerializedName("journal_date") val journalDate: String
)

data class UpdateJournalRequest(
    val id: Int,
    val title: String,
    val content: String,
    @SerializedName("journal_date") val journalDate: String
)

data class DeleteJournalRequest(val id: Int)