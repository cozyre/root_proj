package org.ukrida.root.ui.user.screens.groupmenus.dailybread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.DevotionDate
import org.ukrida.root.data.repository.DevotionRepository
import org.ukrida.root.utils.Resource
import kotlin.collections.emptyList

class DailyBreadViewModel(
    private val devotionRepository: DevotionRepository
) : ViewModel() {

    private val _devotions = MutableStateFlow<List<DevotionDate>>(emptyList())
    val devotions = _devotions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun loadDevotions(groupId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val devotionResult = devotionRepository.getDevotionDates(groupId)
            devotionResult.onSuccess { _devotions.value = it }.onFailure {
                _errorMessage.value = it.message ?: "failed to load devotions"
            }
        }
    }
}