package org.ukrida.root.ui.admin.screens.ongoing.model

/**
 * Model data untuk merepresentasikan foto dokumentasi perjalanan di bagian bawah.
 * * @property id ID unik dari MySQL untuk baris dokumentasi ini
 * @property imageRes Resource ID gambar lokal (sementara untuk kebutuhan mockup/dummy)
 * @property imageUrl URL lokasi file gambar di server hosting/MySQL Anda
 */
data class Documentation(
    val id: String,
    val imageRes: Int,
    val imageUrl: String? = null // Opsional: Untuk load foto dokumentasi asli dari server
)