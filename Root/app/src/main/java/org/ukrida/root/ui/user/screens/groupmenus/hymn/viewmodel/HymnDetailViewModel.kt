package org.ukrida.root.ui.user.screens.groupmenus.hymn.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.data.model.Song

class HymnDetailViewModel : ViewModel() {

    private val _song = MutableStateFlow<Song?>(null)
    val song = _song.asStateFlow()

    fun loadSong(songId: Int) {
        // TODO Backend Integration
        // repository.getSong(songId)
        loadDummy(songId)
    }
    private fun loadDummy(songId: Int) {
        val songs = listOf(
            Song(
                id = 1,
                title = "Amazing Grace",
                author = "Opening Hymn",
                lyrics = """
                Verse 1
                Amazing grace! How sweet the sound...
                
                Verse 2
                'Twas grace that taught my heart to fear...
                
                Chorus
                Praise the Lord forever.
                """.trimIndent()
            ),

            Song(
                id = 2,
                title = "How Great Thou Art",
                author = "Worship",
                lyrics = """
                Verse 1
                O Lord my God, when I in awesome wonder...
                
                Chorus
                Then sings my soul,
                My Saviour God, to Thee.
                """.trimIndent()
            ),

            Song(
                id = 3,
                title = "Here I Am Lord",
                author = "Communion",
                lyrics = """
                Verse 1
                I, the Lord of sea and sky...
                
                Chorus
                Here I am Lord,
                Is it I Lord?
                """.trimIndent()
            ),

            Song(
                id = 4,
                title = "Be Thou My Vision",
                author = "Closing Hymn",
                lyrics = """
                Verse 1
                Be Thou my vision,
                O Lord of my heart...
                
                Chorus
                High King of Heaven.
                """.trimIndent()
            )
        )
        _song.value = songs.find { it.id == songId } ?: songs.first()
    }
}