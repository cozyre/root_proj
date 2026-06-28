package org.ukrida.root.ui.Public.screens.login.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.ui.Public.screens.login.state.LoginUiState

class LoginViewModel : ViewModel() {

    private val _uiState =
        MutableStateFlow(LoginUiState())

    val uiState =
        _uiState.asStateFlow()

    fun login(
        identifier: String,
        password: String,
        role: String
    ) {

        if(identifier.isBlank()){

            _uiState.value =
                _uiState.value.copy(
                    error = "Username atau Email wajib diisi."
                )

            return
        }

        if(password.isBlank()){

            _uiState.value =
                _uiState.value.copy(
                    error = "Password wajib diisi."
                )

            return
        }

        // ===========================
        // TODO Backend Integration
        //
        // repository.login(
        //      identifier,
        //      password,
        //      role
        // )
        // ===========================

        _uiState.value =
            _uiState.value.copy(
                loginSuccess = true
            )

    }

}