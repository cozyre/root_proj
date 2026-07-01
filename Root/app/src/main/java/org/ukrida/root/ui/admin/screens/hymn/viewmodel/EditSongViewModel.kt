package org.ukrida.root.ui.admin.screens.hymn.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ukrida.root.data.fake.FakeSongRepository

class EditSongViewModel(
    private val songId: Int
) : ViewModel() {

    private val repository = FakeSongRepository()

    var title by mutableStateOf("")
        private set

    var author by mutableStateOf("")
        private set

    var lyrics by mutableStateOf("")
        private set
    fun updateTitle(value: String) {
        title = value
    }

    fun updateAuthor(value: String) {
        author = value
    }

    fun updateLyrics(value: String) {
        lyrics = value
    }

    init {
        loadSong()
    }

    private fun loadSong() {
        viewModelScope.launch {

            repository.getSong(songId)
                .onSuccess { song ->

                    title = song.title
                    author = song.author ?: ""
                    lyrics = song.lyrics ?: ""
                }
        }
    }

    companion object {

        fun factory(
            songId: Int
        ): ViewModelProvider.Factory {

            return object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {

                    return EditSongViewModel(
                        songId
                    ) as T
                }
            }
        }
    }
}