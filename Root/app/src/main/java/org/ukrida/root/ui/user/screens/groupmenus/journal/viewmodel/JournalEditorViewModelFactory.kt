package org.ukrida.root.ui.user.screens.groupmenus.journal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.JournalRepository

class JournalEditorViewModelFactory(
    private val journalRepository: JournalRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(JournalEditorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JournalEditorViewModel(
                journalRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}