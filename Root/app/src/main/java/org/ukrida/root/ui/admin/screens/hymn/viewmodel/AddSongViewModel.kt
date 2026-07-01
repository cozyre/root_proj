package org.ukrida.root.ui.admin.screens.hymn.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class SongSectionData(
    val title: String,
    val lyrics: String
)

class AddSongViewModel : ViewModel() {

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

    fun updateTitle(newTitle: String) {
        title = newTitle
    }

    fun updateAuthor(newAuthor: String) {
        author = newAuthor
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

        // sementara untuk dummy data

        println("Title : $title")
        println("Author : $author")

        sections.forEach {
            println("${it.title} : ${it.lyrics}")
        }

        // nanti ganti ke repository/API
    }
}