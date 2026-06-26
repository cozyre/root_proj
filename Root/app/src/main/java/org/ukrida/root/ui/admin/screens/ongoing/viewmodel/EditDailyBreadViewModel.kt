package org.ukrida.root.ui.admin.screens.ongoing.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.ukrida.root.ui.admin.screens.ongoing.model.DailyBreadItem

class EditDailyBreadViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /** ID trip dari argumen navigasi */
    val tripId: String = savedStateHandle["tripId"] ?: ""

    // ── Daftar hari yang tersedia ────────────────────────────────────────────
    private val _availableDays = MutableStateFlow(listOf(1, 2, 3, 4))
    val availableDays: StateFlow<List<Int>> = _availableDays.asStateFlow()

    // ── Hari yang sedang dipilih ─────────────────────────────────────────────
    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    // ── Data Daily Bread per hari ────────────────────────────────────────────
    // Dummy data — ganti dengan load dari repository saat database siap
    private val _dailyBreadList = MutableStateFlow(
        listOf(
            DailyBreadItem(
                id = 1,
                day = 1,
                title = "TITLE HERE",
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                        "Morbi vel luctus justo. Etiam aliquet tempus felis eget imperdiet."
            ),
            DailyBreadItem(
                id = 2,
                day = 2,
                title = "TITLE HERE",
                content = ""
            ),
            DailyBreadItem(
                id = 3,
                day = 3,
                title = "TITLE HERE",
                content = ""
            ),
            DailyBreadItem(
                id = 4,
                day = 4,
                title = "TITLE HERE",
                content = ""
            ),
        )
    )
    val dailyBreadList: StateFlow<List<DailyBreadItem>> = _dailyBreadList.asStateFlow()

    // ── Actions ──────────────────────────────────────────────────────────────

    fun selectDay(day: Int) {
        _selectedDay.update { day }
    }

    fun updateTitle(day: Int, newTitle: String) {
        _dailyBreadList.update { list ->
            list.map { if (it.day == day) it.copy(title = newTitle) else it }
        }
    }

    fun updateContent(day: Int, newContent: String) {
        _dailyBreadList.update { list ->
            list.map { if (it.day == day) it.copy(content = newContent) else it }
        }
    }

    /** Simpan perubahan — TODO: kirim ke backend saat database siap */
    fun submit() {
        // TODO: repository.saveDailyBread(_dailyBreadList.value)
    }
}