package org.ukrida.root.ui.admin.screens.ongoing.model

/**
 * Model data untuk merepresentasikan anggota grup (Group Member).
 * * @property id ID unik dari MySQL (misal: "USR001")
 * @property name Nama user/anggota yang akan ditampilkan
 * @property avatarRes Resource ID gambar lokal (sementara untuk dummy/avatar default)
 * @property avatarUrl URL foto profil jika nanti mengambil gambar langsung dari server/MySQL
 */
data class Member(
    val id: String,
    val name: String,
    val avatarRes: Int,
    val avatarUrl: String? = null // Opsional: Untuk integrasi MySQL + library Coil nanti
)