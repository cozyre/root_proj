package org.ukrida.root.ui.admin.screens.hymn.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ukrida.root.data.fake.FakeSongRepository
import org.ukrida.root.data.model.SongSummary

class HymnForHimViewModel : ViewModel() {

    private val repository = FakeSongRepository()

    var songs by mutableStateOf<List<SongSummary>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        loadSongs()
    }

    private fun loadSongs() {
        viewModelScope.launch {

            isLoading = true

            repository
                .getSongs(groupId = 1)
                .onSuccess { result ->
                    songs = result.sortedBy { it.sortOrder }
                }

            isLoading = false
        }
    }
}