package org.ukrida.root.ui.Public.screens.login.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.ui.Public.screens.login.state.RegisterUiState

class RegisterViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(RegisterUiState())

    val uiState =
        _uiState.asStateFlow()

    fun register(

        firstName: String,
        lastName: String,
        username: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String

    ) {

        if(firstName.isBlank()){
            _uiState.value =
                _uiState.value.copy(
                    error = "First name wajib diisi."
                )
            return
        }

        if(lastName.isBlank()){
            _uiState.value =
                _uiState.value.copy(
                    error = "Last name wajib diisi."
                )
            return
        }

        if(username.isBlank()){
            _uiState.value =
                _uiState.value.copy(
                    error = "Username wajib diisi."
                )
            return
        }

        if(email.isBlank()){
            _uiState.value =
                _uiState.value.copy(
                    error = "Email wajib diisi."
                )
            return
        }

        if(password != confirmPassword){
            _uiState.value =
                _uiState.value.copy(
                    error = "Password tidak sama."
                )
            return
        }

        // ============================
        // TODO Backend Integration
        //
        // repository.register(...)
        //
        // ============================

        _uiState.value =
            _uiState.value.copy(
                registerSuccess = true
            )

    }

}