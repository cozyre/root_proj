package org.ukrida.root.ui.user.screens.groupmenus.itinerary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Itinerary
import org.ukrida.root.data.model.ItineraryDate
import org.ukrida.root.data.repository.ItineraryRepository
import org.ukrida.root.utils.Resource

class ItineraryViewModel(
    private val itineraryRepository: ItineraryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItineraryUiState())
    val uiState: StateFlow<ItineraryUiState> = _uiState.asStateFlow()

    data class ItineraryUiState(
        val dates: Resource<List<ItineraryDate>> = Resource.Loading(),
        val itinerary: Resource<Itinerary?> = Resource.Loading(),
        val selectedIndex: Int = 0
    )

    /**
     * Loads the available dates for the group and then fetches the first day's itinerary.
     */
    fun load(groupId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(dates = Resource.Loading(), itinerary = Resource.Loading()) }
            
            val datesResult = itineraryRepository.getItineraryDates(groupId)
            
            if (datesResult.isSuccess) {
                val datesList = datesResult.getOrNull() ?: emptyList()
                _uiState.update { it.copy(dates = Resource.Success(datesList)) }
                
                if (datesList.isNotEmpty()) {
                    _uiState.update { it.copy(selectedIndex = 0) }
                    fetchItineraryData(groupId, datesList[0].date)
                } else {
                    _uiState.update { it.copy(itinerary = Resource.Success(null)) }
                }
            } else {
                val errorMsg = datesResult.exceptionOrNull()?.message ?: "Failed to load itinerary dates"
                _uiState.update { it.copy(dates = Resource.Error(errorMsg)) }
            }
        }
    }

    /**
     * Helper to fetch specific itinerary data for a given date.
     */
    private suspend fun fetchItineraryData(groupId: Int, date: String) {
        _uiState.update { it.copy(itinerary = Resource.Loading()) }
        
        val itineraryResult = itineraryRepository.getItinerary(groupId, date)
        
        if (itineraryResult.isSuccess) {
            _uiState.update { it.copy(itinerary = Resource.Success(itineraryResult.getOrNull())) }
        } else {
            val errorMsg = itineraryResult.exceptionOrNull()?.message ?: "Failed to load itinerary"
            _uiState.update { it.copy(itinerary = Resource.Error(errorMsg)) }
        }
    }

    fun nextDay(groupId: Int) {
        val currentState = _uiState.value
        val datesResource = currentState.dates
        if (datesResource is Resource.Success) {
            val datesList = datesResource.data
            val currentIndex = currentState.selectedIndex
            if (currentIndex < datesList.lastIndex) {
                val nextIndex = currentIndex + 1
                _uiState.update { it.copy(selectedIndex = nextIndex) }
                viewModelScope.launch {
                    fetchItineraryData(groupId, datesList[nextIndex].date)
                }
            }
        }
    }

    fun previousDay(groupId: Int) {
        val currentState = _uiState.value
        val datesResource = currentState.dates
        if (datesResource is Resource.Success) {
            val datesList = datesResource.data
            val currentIndex = currentState.selectedIndex
            if (currentIndex > 0) {
                val prevIndex = currentIndex - 1
                _uiState.update { it.copy(selectedIndex = prevIndex) }
                viewModelScope.launch {
                    fetchItineraryData(groupId, datesList[prevIndex].date)
                }
            }
        }
    }
}
