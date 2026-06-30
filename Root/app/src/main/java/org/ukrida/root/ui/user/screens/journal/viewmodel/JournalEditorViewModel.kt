package org.ukrida.root.ui.user.screens.journal.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class JournalEditorViewModel : ViewModel() {
    // TODO Backend Integration
    // private val repository: JournalRepository
    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()
    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()
    private val _journalDate = MutableStateFlow("")
    val journalDate = _journalDate.asStateFlow()
    private val _isEditMode = MutableStateFlow(false)
    val isEditMode = _isEditMode.asStateFlow()
    private var journalId: Int? = null
    private val _titleError = MutableStateFlow<String?>(null)
    val titleError = _titleError.asStateFlow()

    private val _contentError = MutableStateFlow<String?>(null)
    val contentError = _contentError.asStateFlow()
    fun loadJournal(journalId: Int?) {
        if (journalId == null) {
            // Create Mode
            _isEditMode.value = false
            clearForm()
            return
        }
        // TODO Backend Integration
        // repository.getJournal(journalId)
        loadDummy(journalId)
    }
    private fun loadDummy(id: Int) {
        journalId = id
        _isEditMode.value = true
        _title.value = "Arrival in Jerusalem"
        _content.value =
            """
            Today marked the beginning of our pilgrimage.
            
            Walking through the Old City was unforgettable.
            """.trimIndent()
        _journalDate.value = "2026-10-01"
    }
    fun updateTitle(value: String) {
        _title.value = value
    }
    fun updateContent(value: String) {
        _content.value = value
    }
    fun updateDate(value: String) {
        _journalDate.value = value
    }
    fun saveJournal(): Boolean {
        if (!validate()) return false
        if (_isEditMode.value) {
            // TODO Backend Integration
            // repository.updateJournal(...)
        } else {
            // TODO Backend Integration
            // repository.createJournal(...)
        }
        return true
    }
    private fun clearForm() {
        journalId = null
        _title.value = ""
        _content.value = ""
        _journalDate.value =
            LocalDate.now().format(
                DateTimeFormatter.ofPattern("dd MMMM yyyy")
            )
    }
    private fun validate(): Boolean {
        var valid = true
        _titleError.value = null
        _contentError.value = null
        if (_title.value.isBlank()) {
            _titleError.value = "Title cannot be empty."
            valid = false
        }
        if (_content.value.isBlank()) {
            _contentError.value = "Content cannot be empty."
            valid = false
        }
        return valid
    }
}