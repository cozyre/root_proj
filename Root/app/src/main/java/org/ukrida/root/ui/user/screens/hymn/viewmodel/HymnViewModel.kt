package org.ukrida.root.ui.user.screens.hymn.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.data.model.SongSummary

class HymnViewModel : ViewModel() {

    private val _songs = MutableStateFlow<List<SongSummary>>(emptyList())
    val songs = _songs.asStateFlow()

    fun loadSongs(groupId: Int) {

        // TODO Backend Integration
        // repository.getSongs(groupId)

        loadDummy(groupId)
    }

    private fun loadDummy(groupId: Int) {

        _songs.value = listOf(

            SongSummary(
                id = 1,
                title = "Amazing Grace",
                author = "Opening Hymn",
                sortOrder = 1
            ),

            SongSummary(
                id = 2,
                title = "How Great Thou Art",
                author = "Worship",
                sortOrder = 2
            ),

            SongSummary(
                id = 3,
                title = "Here I Am Lord",
                author = "Communion",
                sortOrder = 3
            ),

            SongSummary(
                id = 4,
                title = "Be Thou My Vision",
                author = "Closing Hymn",
                sortOrder = 4
            )

        )

    }

}