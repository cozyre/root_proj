package org.ukrida.root.ui.admin.screens.ongoing.model

/**
 * Representasi satu baris lagu dalam daftar Hymn for Him.
 *
 * @param id           ID unik baris (digunakan sebagai key di LazyColumn)
 * @param day          Hari ke-berapa lagu ini masuk
 * @param selectedSong Nama lagu yang sudah dipilih; kosong = belum dipilih
 */
data class SongItem(
    val id: Int,
    val day: Int,
    val selectedSong: String = ""
)