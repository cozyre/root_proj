package org.ukrida.root.ui.Public.screens.journal.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.data.model.Journal

class JournalViewModel : ViewModel() {

    private val _journals = MutableStateFlow<List<Journal>>(emptyList())
    val journals = _journals.asStateFlow()
    fun loadJournals(groupId: Int) {
        // TODO Backend Integration
        // Replace loadDummy() with:
        // repository.listJournals(groupId)
        // Update _journals with API response.
        loadDummy(groupId)
    }
    private fun loadDummy(groupId: Int) {
        _journals.value = listOf(
            Journal(
                id = 1,
                userId = 1,
                groupId = groupId,
                title = "Arrival in Jerusalem",
                content = "Today I arrived in Jerusalem and visited the Old City. It was a wonderful experience.",
                journalDate = "2026-10-01",
                createdAt = "",
                updatedAt = ""
            ),
            Journal(
                id = 2,
                userId = 1,
                groupId = groupId,
                title = "Mount of Olives",
                content = "Visited the Mount of Olives and enjoyed the beautiful scenery.",
                journalDate = "2026-10-02",
                createdAt = "",
                updatedAt = ""
            ),
            Journal(
                id = 3,
                userId = 1,
                groupId = groupId,
                title = "Jordan River",
                content = "Today we went to the Jordan River for the baptism remembrance.",
                journalDate = "2026-10-03",
                createdAt = "",
                updatedAt = ""
            ),
            Journal(
                id = 4,
                userId = 1,
                groupId = groupId,
                title = "Bethlehem",
                content = "Visited Bethlehem and the Church of the Nativity.",
                journalDate = "2026-10-04",
                createdAt = "",
                updatedAt = ""
            ),
            Journal(
                id = 5,
                userId = 1,
                groupId = groupId,
                title = "Sea of Galilee",
                content = "A peaceful day around the Sea of Galilee.",
                journalDate = "2026-10-05",
                createdAt = "",
                updatedAt = ""
            )
        )
    }
}