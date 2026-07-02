package org.ukrida.root.ui.user.screens.groupmenus.hymn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.SongRepository

class HymnViewModelFactory(
    private val songRepository: SongRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HymnViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HymnViewModel(songRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
