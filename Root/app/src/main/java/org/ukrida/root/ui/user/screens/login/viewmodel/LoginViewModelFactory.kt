package org.ukrida.root.ui.user.screens.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.AuthRepository
import org.ukrida.root.utils.SessionManager

class LoginViewModelFactory(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(
                authRepository,
                sessionManager
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}