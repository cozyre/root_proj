package org.ukrida.root.ui.Public.screens.home.model

data class HomeUiState(
    val heroTitle: String = "",
    val heroDescription: String = "",
    val vision: String = "",
    val missions: List<Mission> = emptyList(),
    val recommendationTrips: List<Trip> = emptyList()
)