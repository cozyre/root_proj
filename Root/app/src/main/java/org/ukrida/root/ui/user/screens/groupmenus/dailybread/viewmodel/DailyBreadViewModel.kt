package org.ukrida.root.ui.user.screens.groupmenus.dailybread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Devotion
import org.ukrida.root.data.model.DevotionDate
import org.ukrida.root.data.repository.DevotionRepository
import org.ukrida.root.utils.Resource

class DailyBreadViewModel(
    private val devotionRepository: DevotionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DailyBreadUiState())
    val uiState: StateFlow<DailyBreadUiState> = _uiState.asStateFlow()

    data class DailyBreadUiState(
        val devotions: Resource<List<DevotionDate>> = Resource.Loading(),
        val devotionDetail: Resource<Devotion> = Resource.Loading()
    )

    fun loadDevotionDetail(groupId: Int, date: String){
        viewModelScope.launch{
            _uiState.update { it.copy(devotionDetail = Resource.Loading()) }
            val devotionDetailResult = devotionRepository.getDevotion(groupId, date)

            val devotionDetailResource = if (devotionDetailResult.isSuccess){
                Resource.Success(devotionDetailResult.getOrThrow())
            } else{
                Resource.Error(devotionDetailResult.exceptionOrNull()?.message ?: "Failed to load devotion details")
            }

            _uiState.update {
                it.copy(devotionDetail = devotionDetailResource)
            }
        }
    }

    fun loadDevotions(groupId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(devotions = Resource.Loading()) }
            
            val devotionResult = devotionRepository.getDevotionDates(groupId)
            
            val devotionResource = if (devotionResult.isSuccess) {
                Resource.Success(devotionResult.getOrThrow())
            } else {
                Resource.Error(devotionResult.exceptionOrNull()?.message ?: "Failed to load devotions")
            }

            _uiState.update {
                it.copy(devotions = devotionResource)
            }
        }
    }
}
