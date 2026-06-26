package org.ukrida.root.ui.admin.screens.trip.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.ukrida.root.data.model.Group

class TripViewModel : ViewModel() {

    private val _groups = MutableStateFlow(
        listOf(
            Group(
                id = 1,
                name = "Egypt Tour",
                description = "Explore the pyramids and ancient Egypt.",
                location = "Egypt",
                dresscode = "Casual",
                status = "ONGOING",
                startDate = "2026-07-01",
                endDate = "2026-07-10",
                meetupTime = "08:00",
                meetupAddress = "Cairo Airport"
            ),
            Group(
                id = 2,
                name = "Holy Land",
                description = "Visit historical biblical sites.",
                location = "Jerusalem",
                dresscode = "Modest",
                status = "ONGOING",
                startDate = "2026-08-01",
                endDate = "2026-08-10",
                meetupTime = "09:00",
                meetupAddress = "Jerusalem Center"
            ),
            Group(
                id = 3,
                name = "Jordan Journey",
                description = "Petra and Wadi Rum adventure.",
                location = "Jordan",
                dresscode = "Outdoor",
                status = "FINISHED",
                startDate = "2026-04-01",
                endDate = "2026-04-08",
                meetupTime = "07:00",
                meetupAddress = "Amman Airport"
            ),
            Group(
                id = 4,
                name = "Turkey Heritage",
                description = "Explore Istanbul and Cappadocia.",
                location = "Turkey",
                dresscode = "Casual",
                status = "FINISHED",
                startDate = "2026-03-01",
                endDate = "2026-03-09",
                meetupTime = "08:30",
                meetupAddress = "Istanbul Airport"
            )
        )
    )

    val groups: StateFlow<List<Group>> = _groups
}