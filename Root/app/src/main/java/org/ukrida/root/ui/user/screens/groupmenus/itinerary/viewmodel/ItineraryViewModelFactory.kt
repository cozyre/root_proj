package org.ukrida.root.ui.user.screens.groupmenus.itinerary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.ItineraryRepository

class ItineraryViewModelFactory(
    private val itineraryRepository: ItineraryRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ItineraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItineraryViewModel(
                itineraryRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}