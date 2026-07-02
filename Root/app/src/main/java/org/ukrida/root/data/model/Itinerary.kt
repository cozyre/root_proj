package org.ukrida.root.data.model

import com.google.gson.annotations.SerializedName

data class ItineraryItem(
    val id: Int,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time")   val endTime: String?,
    val type: String,             // "devotion" | "song" | "activity" | ""
    val description: String
)

data class Itinerary(
    val id: Int,
    @SerializedName("itenary_date") val date: String,   // "YYYY-MM-DD"
    @SerializedName("itenary_desc") val desc: String,
    val items: List<ItineraryItem>
)

data class ItineraryDate(
    @SerializedName("itenary_date") val date: String,
    @SerializedName("itenary_desc") val desc: String
)

data class CreateItineraryItemRequest(
    val itenary_id: Int,
    val start_time: String,
    val end_time: String,
    val type: String,
    val description: String
)

data class UpdateItineraryRequest(
    val start_time: String,
    val end_time: String,
    val type: String,
    val description: String
)

data class ItineraryItemResponse(
    val id: Int,
    val start_time: String,
    val end_time: String,
    val type: String,
    val description: String
)

data class DeleteResponse(
    val deleted: Boolean,
    val id: Int? = null
)

