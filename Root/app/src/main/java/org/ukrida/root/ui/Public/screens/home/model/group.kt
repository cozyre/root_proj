package org.ukrida.root.ui.Public.screens.home.model

data class Group(
    val id: Long,
    val name: String,
    val description: String,
    val location: String,
    val startDate: String,
    val endDate: String,
    val image: Int
)