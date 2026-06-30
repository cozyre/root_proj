package org.ukrida.root.ui.user.screens.dailybread.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.data.model.Devotion

class DailyBreadDetailViewModel : ViewModel() {
    private val _devotion = MutableStateFlow<Devotion?>(null)
    val devotion = _devotion.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    fun loadDevotion(
        groupId: Int,
        date: String
    ) {
        // TODO Backend Integration
        // repository.getDevotion(groupId, date)
        loadDummy(date)
    }
    private fun loadDummy(date: String) {
        _devotion.value = Devotion(
            id = 1,
            groupId = 1,
            title = "Daily Bread",
            content =
                "Today's devotion reminds us to walk faithfully with God in every circumstance. Trust His guidance, remain steadfast in prayer, and let His Word strengthen your heart throughout the day.",
            date = date,
            createdAt = ""
        )
    }
}