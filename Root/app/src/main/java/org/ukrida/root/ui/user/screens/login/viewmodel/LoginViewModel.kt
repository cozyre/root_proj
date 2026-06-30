package org.ukrida.root.ui.user.screens.login.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.data.model.AuthData
import org.ukrida.root.data.repository.AuthRepository
import org.ukrida.root.utils.Resource
import org.ukrida.root.utils.SessionManager

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<AuthData>?>(null)
    val uiState = _uiState.asStateFlow()

    suspend fun login(identifier: String, password: String, selectedRole: String) {
        _uiState.value = Resource.Loading()

//        val result = authRepository.login(identifier, password)
        when (val result = authRepository.login(identifier, password)) {
            is Resource.Success -> {
                val backendRole = result.data.user.role

                // Validate role match
                if ((selectedRole == "user") && (backendRole == "admin")) {
                    _uiState.value = Resource.Error(
                        "Role mismatch. You selected '$selectedRole' but your account is '$backendRole'"
                    )
                    return
                }
                else if((selectedRole == "admin") && (backendRole != "admin")){
                    _uiState.value = Resource.Error(
                        "Role mismatch. You selected '$selectedRole' but your account is '$backendRole'"
                    )
                    return
                }

                // Save token, role, and time
                sessionManager.saveToken(result.data.token)
                sessionManager.saveRole(backendRole)
                sessionManager.saveLoginTime()

                _uiState.value = Resource.Success(result.data)
            }
            is Resource.Error -> {
                _uiState.value = Resource.Error(result.message)
            }
            is Resource.Loading -> {
                // Already set above
            }
        }
    }
}
