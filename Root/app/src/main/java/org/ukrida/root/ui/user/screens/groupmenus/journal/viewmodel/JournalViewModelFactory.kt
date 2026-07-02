package org.ukrida.root.ui.user.screens.groupmenus.journal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.JournalRepository

class JournalViewModelFactory(
    private val journalRepository: JournalRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JournalViewModel(
                journalRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}