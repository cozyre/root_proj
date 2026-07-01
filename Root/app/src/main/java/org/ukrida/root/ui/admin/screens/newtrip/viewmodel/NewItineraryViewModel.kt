package org.ukrida.root.ui.admin.screens.trip.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class NewItineraryItem(
    val id: Int,
    val startTime: String = "",
    val endTime: String = "",
    val activity: String = ""
)

class NewItineraryViewModel : ViewModel() {

    private val _selectedDay =
        MutableStateFlow(1)

    val selectedDay: StateFlow<Int> =
        _selectedDay.asStateFlow()

    private val _availableDays =
        MutableStateFlow(listOf(1))

    val availableDays: StateFlow<List<Int>> =
        _availableDays.asStateFlow()

    private val _itineraryMap =
        MutableStateFlow(
            mutableMapOf(
                1 to mutableListOf(
                    NewItineraryItem(id = 1)
                )
            )
        )

    val itineraryMap =
        _itineraryMap.asStateFlow()

    fun setTotalDays(totalDays: Int) {

        _availableDays.value =
            (1..totalDays).toList()

        val updated =
            _itineraryMap.value.toMutableMap()

        (1..totalDays).forEach { day ->

            if (!updated.containsKey(day)) {

                updated[day] =
                    mutableListOf(
                        NewItineraryItem(id = 1)
                    )
            }
        }

        _itineraryMap.value = updated
    }

    fun selectDay(day: Int) {
        _selectedDay.value = day
    }

    fun addItem() {

        val day = _selectedDay.value

        val updated =
            _itineraryMap.value.toMutableMap()

        val list =
            updated[day]?.toMutableList()
                ?: mutableListOf()

        val newId =
            (list.maxOfOrNull { it.id } ?: 0) + 1

        list.add(
            NewItineraryItem(
                id = newId
            )
        )

        updated[day] = list

        _itineraryMap.value = updated
    }

    fun deleteItem(id: Int) {

        val day = _selectedDay.value

        val updated =
            _itineraryMap.value.toMutableMap()

        updated[day] =
            updated[day]
                ?.filterNot {
                    it.id == id
                }
                ?.toMutableList()
                ?: mutableListOf()

        _itineraryMap.value = updated
    }

    fun updateStartTime(
        id: Int,
        value: String
    ) {

        updateItem(id) {
            copy(startTime = value)
        }
    }

    fun updateEndTime(
        id: Int,
        value: String
    ) {

        updateItem(id) {
            copy(endTime = value)
        }
    }

    fun updateActivity(
        id: Int,
        value: String
    ) {

        updateItem(id) {
            copy(activity = value)
        }
    }

    private fun updateItem(
        id: Int,
        transform: NewItineraryItem.() -> NewItineraryItem
    ) {

        val day = _selectedDay.value

        val updated =
            _itineraryMap.value.toMutableMap()

        updated[day] =
            updated[day]
                ?.map {

                    if (it.id == id) {
                        it.transform()
                    } else {
                        it
                    }

                }
                ?.toMutableList()
                ?: mutableListOf()

        _itineraryMap.value = updated
    }
}