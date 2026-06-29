package org.ukrida.root.ui.admin.screens.ongoing.model

/**
 * Representasi satu entri Daily Bread per hari.
 *
 * @param id      ID unik entri
 * @param day     Hari ke-berapa entri ini
 * @param title   Judul renungan
 * @param content Isi konten renungan
 */
data class DailyBreadItem(
    val id: Int,
    val day: Int,
    val title: String = "",
    val content: String = ""
)