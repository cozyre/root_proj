package org.ukrida.root.ui.admin.screens.hymn.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.SongBrowseItem
import org.ukrida.root.data.repository.SongRepository

class HymnForHimViewModel(
    private val repository: SongRepository
) : ViewModel() {

    var songs by mutableStateOf<List<SongBrowseItem>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadSongs()
    }

    fun loadSongs() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            repository
                .browseSongs()
                .onSuccess { result ->
                    songs = result.sortedBy { it.title }
                }
                .onFailure { error ->
                    errorMessage = error.message ?: "Failed to load songs"
                }

            isLoading = false
        }
    }

    companion object {
        fun factory(
            repository: SongRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return HymnForHimViewModel(repository) as T
                }
            }
        }
    }
}