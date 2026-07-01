package org.ukrida.root.ui.admin.screens.trip.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewTripViewModel : ViewModel() {

    companion object {

        val Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>
            ): T {

                return NewTripViewModel() as T
            }
        }
    }

    var title by mutableStateOf("")
        private set

    var startDate by mutableStateOf("")
        private set

    var endDate by mutableStateOf("")
        private set

    var price by mutableStateOf("")
        private set

    var imageUri by mutableStateOf<Uri?>(null)
        private set

    fun updateTitle(newTitle: String) {
        title = newTitle
    }

    fun updateStartDate(newDate: String) {
        startDate = newDate
    }

    fun updateEndDate(newDate: String) {
        endDate = newDate
    }

    fun updatePrice(newPrice: String) {
        price = newPrice
    }

    fun updateImage(uri: Uri?) {
        imageUri = uri
    }

    fun clearForm() {
        title = ""
        startDate = ""
        endDate = ""
        price = ""
        imageUri = null
    }
}