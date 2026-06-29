package org.ukrida.root.ui.admin.screens.ongoing.model

data class ItineraryItem(
    val id: Int,
    val day: Int,
    val startTime: String,  // "XX:XX"
    val endTime: String,    // "XX:XX"
    val activity: String
)