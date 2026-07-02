package org.ukrida.root.data.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.JsonAdapter
import org.ukrida.root.data.remote.BooleanIntAdapter

// ─── Trip ────────────────────────────────────────────────────────────────────

/** Body for POST admin/trip/create */
data class AdminTripRequest(
    val name: String,
    val description: String?,
    @SerializedName("start_date")     val startDate: String,
    @SerializedName("end_date")       val endDate: String,
    val location: String?,
    val dresscode: String?,
    @SerializedName("meetup_time")    val meetupTime: String?,
    @SerializedName("meetup_address") val meetupAddress: String?,
    @SerializedName("mentor_id")      val mentorId: Int,
    @SerializedName("koordinator_id") val koordinatorId: Int
)

/** Body for POST admin/trip/update — same as create but with id */
data class AdminTripUpdateRequest(
    val id: Int,
    val name: String,
    val description: String?,
    @SerializedName("start_date")     val startDate: String,
    @SerializedName("end_date")       val endDate: String,
    val location: String?,
    val dresscode: String?,
    @SerializedName("meetup_time")    val meetupTime: String?,
    @SerializedName("meetup_address") val meetupAddress: String?,
    @SerializedName("mentor_id")      val mentorId: Int,
    @SerializedName("koordinator_id") val koordinatorId: Int
)

data class AdminTripDeleteRequest(
    val id: Int
)

/** Returned in data.group from create/update */
data class AdminTrip(
    val id: Int,
    val name: String,
    val description: String?,
    @SerializedName("start_date")     val startDate: String?,
    @SerializedName("end_date")       val endDate: String?,
    val location: String?,
    val dresscode: String?,
    @SerializedName("meetup_time")    val meetupTime: String?,
    @SerializedName("meetup_address") val meetupAddress: String?,
    @SerializedName("mentor_id")      val mentorId: Int,
    @SerializedName("koordinator_id") val koordinatorId: Int,
    val status: String,
    @SerializedName("created_at")     val createdAt: String
)

/** How many stubs were auto-generated (returned alongside the trip) */
data class StubsAdded(
    val itineraries: Int,
    val devotions: Int
)

/** Data wrapper returned by create/update trip — nested inside ApiResponse.data */
data class AdminTripResult(
    val group: AdminTrip,
    val dates: List<ItineraryDate>,
    @SerializedName("stubs_added") val stubsAdded: StubsAdded
)

/** One row from GET admin/trips */
data class CompletedTrip(
    val id: Int,
    val name: String,
    @SerializedName("start_date")       val startDate: String?,
    @SerializedName("end_date")         val endDate: String?,
    val status: String,
    @SerializedName("created_at")       val createdAt: String,
    @SerializedName("mentor_name")      val mentorName: String,
    @SerializedName("koordinator_name") val koordinatorName: String,
    @SerializedName("member_count")     val memberCount: Int
)

// ─── Orders ───────────────────────────────────────────────────────────────────

/** Body for POST admin/order/approve and admin/order/reject */
data class AdminOrderAction(
    @SerializedName("account_id") val accountId: Int
)

/** Full order row returned after approve/reject */
data class AdminOrderResult(
    val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("group_id") val groupId: Int,
    @SerializedName("status_join") val statusJoin: String,
    @SerializedName("join_date") val joinDate: String?,
    @SerializedName("approved_date") val approvedDate: String?,
    @SerializedName("approved_by") val approvedBy: Int?,

    @field:JsonAdapter(BooleanIntAdapter::class)
    @SerializedName("is_paid")
    val isPaid: Boolean,

    @SerializedName("user_name") val userName: String,
    @SerializedName("group_name") val groupName: String
)

// ─── Member ───────────────────────────────────────────────────────────────────

/** Body for POST admin/member/remove */
data class AdminMemberRemoveRequest(
    @SerializedName("user_id")  val userId: Int,
    @SerializedName("group_id") val groupId: Int
)

// ─── Gallery ──────────────────────────────────────────────────────────────────

/** Body for POST admin/gallery/removeImage */
data class AdminImageRemoveRequest(
    @SerializedName("image_id") val imageId: Int
)

// ─── Songs ────────────────────────────────────────────────────────────────────

/** Body for POST admin/song/create */
data class AdminSongRequest(
    val title: String,
    val author: String?,
    val lyrics: String?
)

/** Body for POST admin/song/update */
data class AdminSongUpdateRequest(
    val id: Int,
    val title: String,
    val author: String?,
    val lyrics: String?
)

/** Body for POST admin/song/addToGroup */
data class AdminSongAddToGroupRequest(
    @SerializedName("song_id")    val songId: Int,
    @SerializedName("group_id")   val groupId: Int,
    @SerializedName("itenary_id") val itenaryId: Int?,
    @SerializedName("sort_order") val sortOrder: Int
)

/** Data returned after addToGroup */
data class AdminSongAddResult(
    @SerializedName("song_id")    val songId: Int,
    @SerializedName("group_id")   val groupId: Int,
    @SerializedName("itenary_id") val itenaryId: Int?,
    @SerializedName("sort_order") val sortOrder: Int
)

// ─── Devotions ────────────────────────────────────────────────────────────────

/** Body for POST admin/devotion/create */
data class AdminDevotionRequest(
    @SerializedName("group_id")      val groupId: Int,
    @SerializedName("devotion_date") val devotionDate: String,
    val title: String,
    val content: String,
    @SerializedName("scripture_ref") val scriptureRef: String?
)

/** Body for POST admin/devotion/update — same but with id */
data class AdminDevotionUpdateRequest(
    val id: Int,
    @SerializedName("group_id")      val groupId: Int,
    @SerializedName("devotion_date") val devotionDate: String,
    val title: String,
    val content: String,
    @SerializedName("scripture_ref") val scriptureRef: String?
)

/** Full devotion row returned by create/update */
data class AdminDevotion(
    val id: Int,
    @SerializedName("group_id")      val groupId: Int,
    val title: String,
    val content: String,
    @SerializedName("scripture_ref") val scriptureRef: String?,
    @SerializedName("devotion_date") val devotionDate: String
)