package org.ukrida.root.ui.admin.screens.ongoing.viewmodel

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
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.utils.Resource
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class DailyBreadItem(
    val id: Int,
    val day: Int,
    val date: String,
    val title: String,
    val content: String
)

class EditDailyBreadViewModel(
    val tripId: Int,
    private val devotionRepository: DevotionRepository,
    private val adminRepository: AdminRepository,
    private val groupRepository: GroupRepository
) : ViewModel() {

    private val _availableDays = MutableStateFlow<List<Int>>(emptyList())
    val availableDays: StateFlow<List<Int>> = _availableDays.asStateFlow()

    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    private val _dailyBreadList = MutableStateFlow<List<DailyBreadItem>>(emptyList())
    val dailyBreadList: StateFlow<List<DailyBreadItem>> = _dailyBreadList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _submitSuccess = MutableStateFlow(false)
    val submitSuccess: StateFlow<Boolean> = _submitSuccess.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val dayToDate = mutableMapOf<Int, String>()

    init {
        loadData()
    }

    private fun calculateDays(startDate: String, endDate: String): List<Int> {
        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)
        val totalDays = ChronoUnit.DAYS.between(start, end).toInt() + 1
        return (1..totalDays).toList()
    }

    private fun generateDayDateMap(startDate: String, endDate: String) {
        dayToDate.clear()

        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)
        val totalDays = ChronoUnit.DAYS.between(start, end).toInt() + 1

        for (i in 0 until totalDays) {
            val day = i + 1
            val date = start.plusDays(i.toLong()).toString()
            dayToDate[day] = date
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            groupRepository.getTourById(tripId)
                .onSuccess { group ->
                    val startDate = group.startDate
                    val endDate = group.endDate

                    if (startDate != null && endDate != null) {
                        _availableDays.value = calculateDays(startDate, endDate)
                        generateDayDateMap(startDate, endDate)
                        _selectedDay.value = _availableDays.value.firstOrNull() ?: 1
                    }
                }
                .onFailure {
                    _errorMessage.value = it.message
                    it.printStackTrace()
                }

            val items = mutableListOf<DailyBreadItem>()

            dayToDate.forEach { (day, date) ->
                devotionRepository.getDevotion(tripId, date)
                    .onSuccess { devotion ->
                        items.add(
                            DailyBreadItem(
                                id = devotion.id,
                                day = day,
                                date = devotion.date,
                                title = devotion.title,
                                content = devotion.content
                            )
                        )
                    }
                    .onFailure {
                        items.add(
                            DailyBreadItem(
                                id = 0,
                                day = day,
                                date = date,
                                title = "Daily Bread - Day $day",
                                content = ""
                            )
                        )
                    }
            }

            _dailyBreadList.value = items.sortedBy { it.day }
            _isLoading.value = false
        }
    }

    fun selectDay(day: Int) {
        _selectedDay.update { day }
    }

    fun updateTitle(day: Int, newTitle: String) {
        _dailyBreadList.update { list ->
            list.map {
                if (it.day == day) it.copy(title = newTitle) else it
            }
        }
    }

    fun updateContent(day: Int, newContent: String) {
        _dailyBreadList.update { list ->
            list.map {
                if (it.day == day) it.copy(content = newContent) else it
            }
        }
    }

    fun submit() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                _dailyBreadList.value.forEach { item ->
                    val title = item.title.trim()
                    val content = item.content.trim()

                    if (title.isBlank() || content.isBlank()) {
                        return@forEach
                    }

                    val result = if (item.id > 0) {
                        adminRepository.updateDevotion(
                            id = item.id,
                            groupId = tripId,
                            devotionDate = item.date,
                            title = title,
                            content = content,
                            scriptureRef = null
                        )
                    } else {
                        adminRepository.createDevotion(
                            groupId = tripId,
                            devotionDate = item.date,
                            title = title,
                            content = content,
                            scriptureRef = null
                        )
                    }

                    if (result is Resource.Error) {
                        throw Exception(result.message)
                    }
                }

                _submitSuccess.value = true
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Gagal menyimpan Daily Bread"
                e.printStackTrace()
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
            devotionRepository: DevotionRepository,
            adminRepository: AdminRepository,
            groupRepository: GroupRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return EditDailyBreadViewModel(
                        tripId,
                        devotionRepository,
                        adminRepository,
                        groupRepository
                    ) as T
                }
            }
    }
}