package org.ukrida.root.ui.Public.screens.home.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.R
import org.ukrida.root.ui.Public.screens.home.model.HomeUiState
import org.ukrida.root.ui.Public.screens.home.model.Mission
import org.ukrida.root.ui.Public.screens.home.model.Trip

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(createDummyData())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private fun createDummyData(): HomeUiState {
        return HomeUiState(
            heroTitle = "ROOT",
            heroDescription =
                "Walk together in faith and experience unforgettable spiritual journeys with PilgrimMate.",
            vision =
                "To become a trusted Christian pilgrimage community that strengthens faith and fellowship through meaningful journeys.",
            missions = listOf(
                Mission(
                    id = 1,
                    title = "Faith",
                    description = "Growing closer to God through every pilgrimage."
                ),
                Mission(
                    id = 2,
                    title = "Community",
                    description = "Building strong Christian fellowship."
                ),
                Mission(
                    id = 3,
                    title = "Service",
                    description = "Serving others with love and compassion."
                ),
                Mission(
                    id = 4,
                    title = "Integrity",
                    description = "Providing trustworthy and meaningful journeys."
                )
            ),
            recommendationTrips = listOf(
                Trip(
                    id = 1,
                    title = "Holy Land Pilgrimage",
                    description = "Experience the places where Jesus walked.",
                    imageRes = R.drawable.pyramid,
                    price = "Rp 45.000.000"
                ),
                Trip(
                    id = 2,
                    title = "Turkey Seven Churches",
                    description = "Visit the Seven Churches in Revelation.",
                    imageRes = R.drawable.pyramid,
                    price = "Rp 38.000.000"
                )
            )
        )
    }
}