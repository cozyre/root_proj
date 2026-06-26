package org.ukrida.root.ui.admin.screens.ongoing.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.ukrida.root.ui.admin.screens.ongoing.model.SongItem

class EditSongsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /** ID trip yang sedang diedit — diambil dari argumen navigasi */
    val tripId: String = savedStateHandle["tripId"] ?: ""

    // ── Daftar lagu yang tersedia untuk dipilih (bisa diganti dari repository) ──
    val availableSongs: List<String> = listOf(
        "Amazing Grace",
        "How Great Thou Art",
        "Blessed Assurance",
        "Great Is Thy Faithfulness",
        "In Christ Alone",
        "10,000 Reasons",
        "Cornerstone",
        "What A Beautiful Name",
        "Build My Life",
        "Good Grace"
    )

    // ── Daftar hari yang tersedia ────────────────────────────────────────────────
    private val _availableDays = MutableStateFlow(listOf(1, 2, 3, 4))
    val availableDays: StateFlow<List<Int>> = _availableDays.asStateFlow()

    // ── Hari yang sedang dipilih ─────────────────────────────────────────────────
    private val _selectedDay = MutableStateFlow(1)
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    // ── Semua item lagu (semua hari) ─────────────────────────────────────────────
    private val _songList = MutableStateFlow<List<SongItem>>(
        // Data dummy awal — ganti dengan load dari repository
        listOf(
            SongItem(id = 1, day = 1),
            SongItem(id = 2, day = 1),
            SongItem(id = 3, day = 1),
            SongItem(id = 4, day = 1),
            SongItem(id = 5, day = 2),
            SongItem(id = 6, day = 2),
        )
    )
    val songList: StateFlow<List<SongItem>> = _songList.asStateFlow()

    private var nextId = _songList.value.maxOfOrNull { it.id }?.plus(1) ?: 1

    // ── Actions ──────────────────────────────────────────────────────────────────

    /** Ganti hari yang sedang ditampilkan */
    fun selectDay(day: Int) {
        _selectedDay.update { day }
    }

    /**
     * Pilih lagu untuk item tertentu.
     *
     * @param itemId       ID item yang diperbarui
     * @param selectedSong Nama lagu yang dipilih dari dropdown
     */
    fun selectSong(itemId: Int, selectedSong: String) {
        _songList.update { list ->
            list.map { if (it.id == itemId) it.copy(selectedSong = selectedSong) else it }
        }
    }

    /** Hapus item lagu berdasarkan ID */
    fun deleteItem(itemId: Int) {
        _songList.update { list -> list.filter { it.id != itemId } }
    }

    /** Tambah baris lagu baru untuk hari yang sedang dipilih */
    fun addItem() {
        val newItem = SongItem(id = nextId, day = _selectedDay.value)
        nextId++
        _songList.update { it + newItem }
    }

    /** Simpan / submit perubahan (implementasikan sesuai repository) */
    fun submitSongs() {
        // TODO: panggil repository untuk menyimpan _songList ke server / database
    }
}