package org.ukrida.root.data.model

import com.google.gson.annotations.SerializedName

/** List / search within a group — includes sort_order from group_songs pivot. */
data class SongSummary(
    val id: Int,
    val title: String,
    val author: String?,
    @SerializedName("sort_order") val sortOrder: Int
)

/** Full song detail with lyrics. */
data class Song(
    val id: Int,
    val title: String,
    val author: String?,
    val lyrics: String?
)

/** Global library browse — no sort_order (not group-specific). */
data class SongBrowseItem(
    val id: Int,
    val title: String,
    val author: String?
)

data class AddSongToGroupRequest(
    val group_id: Int,
    val song_id: Int,
    val itenary_id: Int? = null,
    val sort_order: Int? = null
)

data class RemoveSongFromGroupRequest(
    val group_id: Int,
    val song_id: Int,
    val itenary_id: Int? = null
)