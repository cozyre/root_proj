package org.ukrida.root.ui.admin.screens.hymn.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.data.repository.SongRepository
import org.ukrida.root.utils.Resource

class EditSongViewModel(
    private val songId: Int,
    private val songRepository: SongRepository,
    private val adminRepository: AdminRepository
) : ViewModel() {

    var title by mutableStateOf("")
        private set

    var author by mutableStateOf("")
        private set

    var sections by mutableStateOf<List<SongSectionData>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isSaving by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isSuccess by mutableStateOf(false)
        private set

    init {
        loadSong()
    }

    fun updateTitle(value: String) {
        title = value
    }

    fun updateAuthor(value: String) {
        author = value
    }

    fun updateSectionTitle(index: Int, value: String) {
        sections = sections.toMutableList().apply {
            this[index] = this[index].copy(
                title = value
            )
        }
    }

    fun updateSectionLyrics(index: Int, value: String) {
        sections = sections.toMutableList().apply {
            this[index] = this[index].copy(
                lyrics = value
            )
        }
    }

    fun addSection() {
        val nextNumber = sections.size + 1

        sections = sections + SongSectionData(
            title = "Verse $nextNumber",
            lyrics = ""
        )
    }

    fun removeSection(index: Int) {
        if (sections.size <= 1) return

        sections = sections.toMutableList().apply {
            removeAt(index)
        }
    }

    fun loadSong() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            songRepository
                .getSong(songId)
                .onSuccess { song ->
                    title = song.title
                    author = song.author ?: ""

                    sections = parseLyricsToSections(
                        song.lyrics ?: ""
                    )
                }
                .onFailure { error ->
                    errorMessage = error.message ?: "Failed to load song"
                }

            isLoading = false
        }
    }

    fun saveSong() {
        if (title.isBlank()) {
            errorMessage = "Song title cannot be empty"
            return
        }

        viewModelScope.launch {
            isSaving = true
            errorMessage = null
            isSuccess = false

            val finalLyrics = sections
                .filter {
                    it.title.isNotBlank() || it.lyrics.isNotBlank()
                }
                .joinToString(separator = "\n\n") { section ->
                    "[${section.title.trim()}]\n${section.lyrics.trim()}"
                }

            when (
                val result = adminRepository.updateSong(
                    id = songId,
                    title = title.trim(),
                    author = author.trim().ifBlank { null },
                    lyrics = finalLyrics.ifBlank { null }
                )
            ) {
                is Resource.Success -> {
                    isSuccess = true
                }

                is Resource.Error -> {
                    errorMessage = result.message
                }

                is Resource.Loading -> Unit
            }

            isSaving = false
        }
    }

    private fun parseLyricsToSections(rawLyrics: String): List<SongSectionData> {
        if (rawLyrics.isBlank()) {
            return listOf(
                SongSectionData(
                    title = "Verse 1",
                    lyrics = ""
                )
            )
        }

        val regex = Regex("""\[(.*?)]\s*([\s\S]*?)(?=\n\s*\[|$)""")

        val result = regex
            .findAll(rawLyrics)
            .map { match ->
                val sectionTitle = match.groupValues[1].trim()
                val sectionLyrics = match.groupValues[2].trim()

                SongSectionData(
                    title = sectionTitle,
                    lyrics = sectionLyrics
                )
            }
            .toList()

        return result.ifEmpty {
            listOf(
                SongSectionData(
                    title = "Verse 1",
                    lyrics = rawLyrics
                )
            )
        }
    }

    fun resetSuccessState() {
        isSuccess = false
    }

    companion object {
        fun factory(
            songId: Int,
            songRepository: SongRepository,
            adminRepository: AdminRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return EditSongViewModel(
                        songId = songId,
                        songRepository = songRepository,
                        adminRepository = adminRepository
                    ) as T
                }
            }
        }
    }
}