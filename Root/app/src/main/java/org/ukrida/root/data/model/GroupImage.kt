package org.ukrida.root.data.model

import com.google.gson.annotations.SerializedName

data class GroupImage(
    val id: Int,
    @SerializedName("group_id")   val groupId: Int,
    @SerializedName("image_url")  val imageUrl: String,
    val caption: String?,
    @SerializedName("image_type") val imageType: String,
    @SerializedName("sort_order") val sortOrder: Int,
    @SerializedName("created_at") val createdAt: String
)