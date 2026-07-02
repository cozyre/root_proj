package org.ukrida.root.ui.user.screens.groupmenus.journal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ukrida.root.data.repository.JournalRepository
import org.ukrida.root.utils.Resource
import java.time.LocalDate

class JournalEditorViewModel(
    private val journalRepository: JournalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JournalEditorUiState())
    val uiState: StateFlow<JournalEditorUiState> = _uiState.asStateFlow()

    private var journalId: Int? = null

    data class JournalEditorUiState(
        val title: String = "",
        val content: String = "",
        val journalDate: String = LocalDate.now().toString(),
        val isEditMode: Boolean = false,
        val saveResult: Resource<Unit>? = null,
        val titleError: String? = null,
        val contentError: String? = null
    )

    fun loadJournal(groupId: Int, journalId: Int?) {
        // Handle -1 (default from navigation for new journal) as null
        val effectiveId = if (journalId == -1 || journalId == null) null else journalId
        this.journalId = effectiveId
        
        viewModelScope.launch {
            if (effectiveId == null) {
                _uiState.update {
                    it.copy(
                        isEditMode = false,
                        title = "",
                        content = "",
                        journalDate = LocalDate.now().toString(),
                        titleError = null,
                        contentError = null,
                        saveResult = null
                    )
                }
            } else {
                _uiState.update { it.copy(isEditMode = true, saveResult = null) }
                val result = journalRepository.listJournals(groupId)
                if (result is Resource.Success) {
                    val journal = result.data.find { it.id == effectiveId }
                    if (journal != null) {
                        _uiState.update {
                            it.copy(
                                title = journal.title,
                                content = journal.content,
                                journalDate = journal.journalDate
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateTitle(value: String) {
        _uiState.update { it.copy(title = value) }
    }

    fun updateContent(value: String) {
        _uiState.update { it.copy(content = value) }
    }

    fun updateDate(value: String) {
        _uiState.update { it.copy(journalDate = value) }
    }

    fun saveJournal(groupId: Int) {
        if (!validate()) return

        viewModelScope.launch {
            _uiState.update { it.copy(saveResult = Resource.Loading()) }
            val currentState = _uiState.value
            val result = if (currentState.isEditMode) {
                journalRepository.updateJournal(
                    id = journalId!!,
                    title = currentState.title,
                    content = currentState.content,
                    journalDate = currentState.journalDate
                )
            } else {
                journalRepository.createJournal(
                    groupId = groupId,
                    title = currentState.title,
                    content = currentState.content,
                    journalDate = currentState.journalDate
                )
            }

            _uiState.update {
                it.copy(
                    saveResult = when (result) {
                        is Resource.Success -> Resource.Success(Unit)
                        is Resource.Error -> Resource.Error(result.message)
                        else -> Resource.Loading()
                    }
                )
            }
        }
    }

    fun deleteJournal() {
        val id = journalId ?: return
        viewModelScope.launch {
            _uiState.update { it.copy(saveResult = Resource.Loading()) }
            val result = journalRepository.deleteJournal(id)
            _uiState.update {
                it.copy(
                    saveResult = when (result) {
                        is Resource.Success -> Resource.Success(Unit)
                        is Resource.Error -> Resource.Error(result.message)
                        else -> Resource.Loading()
                    }
                )
            }
        }
    }

    fun resetSaveResult() {
        _uiState.update { it.copy(saveResult = null) }
    }

    private fun validate(): Boolean {
        var valid = true
        var titleErr: String? = null
        var contentErr: String? = null

        if (_uiState.value.title.isBlank()) {
            titleErr = "Title cannot be empty."
            valid = false
        }
        if (_uiState.value.content.isBlank()) {
            contentErr = "Content cannot be empty."
            valid = false
        }

        _uiState.update { it.copy(titleError = titleErr, contentError = contentErr) }
        return valid
    }
}
