package org.ukrida.root.data.fake

import kotlinx.coroutines.delay
import org.ukrida.root.data.dummy.DummySongData
import org.ukrida.root.data.model.Song
import org.ukrida.root.data.model.SongBrowseItem
import org.ukrida.root.data.model.SongSummary

class FakeSongRepository {

    suspend fun getSongs(
        groupId: Int
    ): Result<List<SongSummary>> {

        delay(500)

        return Result.success(
            DummySongData.songSummaries
        )
    }

    suspend fun getSongsByDate(
        groupId: Int,
        date: String
    ): Result<List<SongSummary>> {

        delay(500)

        return Result.success(
            DummySongData.songSummaries
        )
    }

    suspend fun getSong(
        id: Int
    ): Result<Song> {

        delay(500)

        val song = DummySongData.getSongById(id)

        return if (song != null) {
            Result.success(song)
        } else {
            Result.failure(
                Exception("Song not found")
            )
        }
    }

    suspend fun searchSongs(
        groupId: Int,
        query: String
    ): Result<List<SongSummary>> {

        delay(500)

        val results = DummySongData.songSummaries.filter {
            it.title.contains(query, ignoreCase = true) ||
                    (it.author?.contains(query, ignoreCase = true) == true)
        }

        return Result.success(results)
    }

    suspend fun browseSongs(
        query: String = ""
    ): Result<List<SongBrowseItem>> {

        delay(500)

        val results =
            if (query.isBlank()) {
                DummySongData.songBrowseItems
            } else {
                DummySongData.songBrowseItems.filter {
                    it.title.contains(query, ignoreCase = true) ||
                            (it.author?.contains(query, ignoreCase = true) == true)
                }
            }

        return Result.success(results)
    }
}