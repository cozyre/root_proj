package org.ukrida.root.ui.admin.screens.hymn.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.utils.Resource

data class SongSectionData(
    val title: String,
    val lyrics: String
)

class AddSongViewModel(
    private val adminRepository: AdminRepository
) : ViewModel() {

    var title by mutableStateOf("")
        private set

    var author by mutableStateOf("")
        private set

    var sections by mutableStateOf(
        listOf(
            SongSectionData(
                title = "Verse 1",
                lyrics = ""
            )
        )
    )
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isSuccess by mutableStateOf(false)
        private set

    fun updateTitle(newTitle: String) {
        title = newTitle
    }

    fun updateAuthor(newAuthor: String) {
        author = newAuthor
    }

    fun updateSectionTitle(
        index: Int,
        newTitle: String
    ) {
        sections = sections.toMutableList().apply {
            this[index] = this[index].copy(
                title = newTitle
            )
        }
    }

    fun updateLyrics(
        index: Int,
        lyrics: String
    ) {
        sections = sections.toMutableList().apply {
            this[index] = this[index].copy(
                lyrics = lyrics
            )
        }
    }

    fun addSection() {
        val nextNumber =
            sections.count {
                it.title.startsWith("Verse")
            } + 1

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

    fun submitSong() {
        if (title.isBlank()) {
            errorMessage = "Song title cannot be empty"
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            isSuccess = false

            val finalLyrics = sections
                .filter {
                    it.title.isNotBlank() || it.lyrics.isNotBlank()
                }
                .joinToString(separator = "\n\n") { section ->
                    "[${section.title}]\n${section.lyrics}"
                }

            when (
                val result = adminRepository.createSong(
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

            isLoading = false
        }
    }

    fun resetSuccessState() {
        isSuccess = false
    }

    companion object {
        fun factory(
            adminRepository: AdminRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return AddSongViewModel(adminRepository) as T
                }
            }
        }
    }
}