package org.ukrida.root.ui.user.screens.groupmenus.journal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Journal
import org.ukrida.root.data.repository.JournalRepository
import org.ukrida.root.utils.Resource

class JournalViewModel(
    private val journalRepository: JournalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JournalUiState())
    val uiState: StateFlow<JournalUiState> = _uiState.asStateFlow()

    data class JournalUiState(
        val journals: Resource<List<Journal>> = Resource.Loading(),
        val actionResult: Resource<Unit>? = null
    )

    fun loadJournals(groupId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(journals = Resource.Loading()) }
            val result = journalRepository.listJournals(groupId)
            _uiState.update { it.copy(journals = result) }
        }
    }

    fun createJournal(groupId: Int, title: String, content: String, journalDate: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(actionResult = Resource.Loading()) }
            val result = journalRepository.createJournal(groupId, title, content, journalDate)
            when (result) {
                is Resource.Success -> {
                    _uiState.update { it.copy(actionResult = Resource.Success(Unit)) }
                    loadJournals(groupId)
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(actionResult = Resource.Error(result.message)) }
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun deleteJournal(groupId: Int, journalId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(actionResult = Resource.Loading()) }
            when (val result = journalRepository.deleteJournal(journalId)) {
                is Resource.Success -> {
                    _uiState.update { it.copy(actionResult = Resource.Success(Unit)) }
                    loadJournals(groupId)
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(actionResult = Resource.Error(result.message)) }
                }
                is Resource.Loading -> {}
            }
        }
    }

    fun resetActionResult() {
        _uiState.update { it.copy(actionResult = null) }
    }
}
