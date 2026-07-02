package org.ukrida.root.data.model

import com.google.gson.annotations.SerializedName

data class Devotion(
    val id: Int,
    @SerializedName("group_id") val groupId: Int,
    val title: String,
    val content: String,
    @SerializedName("devotion_date") val date: String,
    @SerializedName("created_at") val createdAt: String
)

data class DevotionDate(
    @SerializedName("devotion_date") val date: String,
    val title: String
)

data class CreateDevotionRequest(
    @SerializedName("group_id") val groupId: Int,
    @SerializedName("devotion_date") val devotionDate: String,
    val title: String,
    val content: String,
    @SerializedName("scripture_ref") val scriptureRef: String? = null
)

data class UpdateDevotionRequest(
    @SerializedName("group_id") val groupId: Int,
    @SerializedName("devotion_date") val devotionDate: String,
    val title: String,
    val content: String,
    @SerializedName("scripture_ref") val scriptureRef: String? = null
)