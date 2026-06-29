package org.ukrida.root.ui.Public.screens.dailybread.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.data.model.Devotion
import org.ukrida.root.data.model.DevotionDate

class DailyBreadViewModel : ViewModel() {

    private val _devotions = MutableStateFlow<List<DevotionDate>>(emptyList())
    val devotions = _devotions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun loadDevotions(groupId: Int) {
        // TODO Backend Integration
        // repository.getDevotionDates(groupId)
        loadDummy()
    }

    private fun loadDummy() {
        _devotions.value = listOf(
            DevotionDate(
                date = "2026-06-29",
                title = "Day 01"
            ),
            DevotionDate(
                date = "2026-06-30",
                title = "Day 02"
            ),
            DevotionDate(
                date = "2026-07-01",
                title = "Day 03"
            )
        )

    }

}