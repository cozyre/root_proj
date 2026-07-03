package org.ukrida.root.ui.admin.screens.newtrip.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.data.repository.DevotionRepository
import org.ukrida.root.utils.Resource

data class NewDailyBreadItemUiState(
    val id: Int,
    val day: Int,
    val date: String,
    val title: String = "",
    val content: String = ""
)

class NewDailyBreadViewModel(
    val tripId: Int,
    private val adminRepository: AdminRepository,
    private val devotionRepository: DevotionRepository
) : ViewModel() {

    private val _availableDays = MutableStateFlow<List<Int>>(emptyList())
    val availableDays: StateFlow<List<Int>> = _availableDays.asStateFlow()

    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    private val _dailyBreadList = MutableStateFlow<List<NewDailyBreadItemUiState>>(emptyList())
    val dailyBreadList: StateFlow<List<NewDailyBreadItemUiState>> = _dailyBreadList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _submitSuccess = MutableStateFlow(false)
    val submitSuccess: StateFlow<Boolean> = _submitSuccess.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadExistingDevotions()
    }

    private fun loadExistingDevotions() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            devotionRepository.getDevotionDates(tripId)
                .onSuccess { dates ->
                    val dailyItems = mutableListOf<NewDailyBreadItemUiState>()

                    dates.forEachIndexed { index, devotionDate ->
                        val day = index + 1

                        devotionRepository.getDevotion(
                            groupId = tripId,
                            date = devotionDate.date
                        ).onSuccess { devotion ->
                            dailyItems.add(
                                NewDailyBreadItemUiState(
                                    id = devotion.id,
                                    day = day,
                                    date = devotion.date,
                                    title = devotion.title,
                                    content = devotion.content
                                )
                            )
                        }.onFailure {
                            dailyItems.add(
                                NewDailyBreadItemUiState(
                                    id = -day,
                                    day = day,
                                    date = devotionDate.date,
                                    title = devotionDate.title,
                                    content = ""
                                )
                            )
                        }
                    }

                    val sortedItems = dailyItems.sortedBy { it.day }

                    _dailyBreadList.value = sortedItems
                    _availableDays.value = sortedItems.map { it.day }
                    _selectedDay.value = sortedItems.firstOrNull()?.day ?: 1
                }
                .onFailure { error ->
                    _errorMessage.value = error.message ?: "Gagal memuat Daily Bread"
                }

            _isLoading.value = false
        }
    }

    fun selectDay(day: Int) {
        _selectedDay.value = day
    }

    fun updateTitle(day: Int, value: String) {
        _dailyBreadList.update { list ->
            list.map { item ->
                if (item.day == day) {
                    item.copy(title = value)
                } else {
                    item
                }
            }
        }
    }

    fun updateContent(day: Int, value: String) {
        _dailyBreadList.update { list ->
            list.map { item ->
                if (item.day == day) {
                    item.copy(content = value)
                } else {
                    item
                }
            }
        }
    }

    fun submitDailyBread() {
        val currentList = _dailyBreadList.value

        if (currentList.isEmpty()) {
            _errorMessage.value = "Daily Bread belum tersedia"
            return
        }

        val invalidItem = currentList.firstOrNull {
            it.title.isBlank() || it.content.isBlank()
        }

        if (invalidItem != null) {
            _errorMessage.value = "Title dan content Daily Bread harus diisi untuk semua day"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                currentList.forEach { item ->
                    if (item.id <= 0) {
                        throw Exception("Devotion Day ${item.day} belum punya ID dari server")
                    }

                    val result = adminRepository.updateDevotion(
                        id = item.id,
                        groupId = tripId,
                        devotionDate = item.date,
                        title = item.title.trim(),
                        content = item.content.trim(),
                        scriptureRef = null
                    )

                    if (result is Resource.Error) {
                        throw Exception(result.message)
                    }
                }

                _submitSuccess.value = true
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Gagal menyimpan Daily Bread"
            }

            _isLoading.value = false
        }
    }

    fun resetSubmitSuccess() {
        _submitSuccess.value = false
    }

    fun clearError() {
        _errorMessage.value = null
    }

    companion object {
        fun factory(
            tripId: Int,
            adminRepository: AdminRepository,
            devotionRepository: DevotionRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NewDailyBreadViewModel(
                        tripId = tripId,
                        adminRepository = adminRepository,
                        devotionRepository = devotionRepository
                    ) as T
                }
            }
        }
    }
}