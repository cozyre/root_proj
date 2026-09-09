package org.ukrida.root.ui.user.screens.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.ukrida.root.data.model.User
import org.ukrida.root.data.repository.AuthRepository
import org.ukrida.root.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<User>?>(null)
    val uiState = _uiState.asStateFlow()

    fun register(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        phone: String,
        password: String,
        passwordConfirmation: String
    ) {
        if (firstName.isBlank()) {
            _uiState.value = Resource.Error("First name cannot be blank")
            return
        }
        if (username.isBlank()) {
            _uiState.value = Resource.Error("Username cannot be blank")
            return
        }
        if (email.isBlank()) {
            _uiState.value = Resource.Error("Email cannot be blank")
            return
        }
        if (password.isBlank()) {
            _uiState.value = Resource.Error("Password cannot be blank")
            return
        }

        if (password != passwordConfirmation) {
            _uiState.value = Resource.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            _uiState.value = Resource.Loading()
            val result = authRepository.register(
                firstName, lastName, username, email, phone, password, passwordConfirmation
            )
            
            if (result.isSuccess) {
                val user = result.getOrNull()
                if (user != null) {
                    _uiState.value = Resource.Success(user)
                } else {
                    _uiState.value = Resource.Error("Registration failed: Empty response data")
                }
            } else {
                val error = result.exceptionOrNull()
                _uiState.value = Resource.Error(error?.message ?: "Registration failed")
            }
        }
    }
}
