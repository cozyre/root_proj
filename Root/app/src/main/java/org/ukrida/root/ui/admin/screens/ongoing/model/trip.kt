package org.ukrida.root.ui.admin.screens.ongoing.model

data class Trip(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val dateRange: String
)