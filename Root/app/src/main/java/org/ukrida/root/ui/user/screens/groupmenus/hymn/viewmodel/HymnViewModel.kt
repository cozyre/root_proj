package org.ukrida.root.ui.user.screens.groupmenus.hymn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Song
import org.ukrida.root.data.model.SongSummary
import org.ukrida.root.data.repository.SongRepository
import org.ukrida.root.utils.Resource

class HymnViewModel(
    private val songRepository: SongRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HymnUiState())
    val uiState: StateFlow<HymnUiState> = _uiState.asStateFlow()

    data class HymnUiState(
        val songs: Resource<List<SongSummary>> = Resource.Loading(),
        val song: Resource<Song> = Resource.Loading()
    )

    fun loadSongs(groupId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(songs = Resource.Loading()) }
            
            val result = songRepository.getSongs(groupId)

            if (result.isSuccess) {
                _uiState.update { 
                    it.copy(songs = Resource.Success(result.getOrDefault(emptyList())))
                }
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Failed to fetch songs"
                _uiState.update { it.copy(songs = Resource.Error(errorMsg)) }
            }
        }
    }

    fun searchSongs(groupId: Int, query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(songs = Resource.Loading()) }
            val result = songRepository.searchSongs(groupId, query)
            if (result.isSuccess) {
                _uiState.update { 
                    it.copy(songs = Resource.Success(result.getOrDefault(emptyList())))
                }
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Search failed"
                _uiState.update { it.copy(songs = Resource.Error(errorMsg)) }
            }
        }
    }

    fun getSongsByDate(groupId: Int, date: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(songs = Resource.Loading()) }
            val result = songRepository.getSongsByDate(groupId, date)
            if (result.isSuccess) {
                _uiState.update { 
                    it.copy(songs = Resource.Success(result.getOrDefault(emptyList())))
                }
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Failed to fetch songs by date"
                _uiState.update { it.copy(songs = Resource.Error(errorMsg)) }
            }
        }
    }

    fun loadSongDetail(songId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(song = Resource.Loading()) }

            val result = songRepository.getSong(songId)

            if (result.isSuccess) {
                val songData = result.getOrNull()!!
                _uiState.update {
                    it.copy(song = Resource.Success(songData))
                }
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Failed to fetch song"
                _uiState.update { it.copy(song = Resource.Error(errorMsg)) }
            }
        }
    }
}
