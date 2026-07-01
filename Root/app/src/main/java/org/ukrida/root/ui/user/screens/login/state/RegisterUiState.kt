package org.ukrida.root.ui.user.screens.login.state

data class RegisterUiState(
    val isLoading: Boolean = false,
    val registerSuccess: Boolean = false,
    val error: String? = null
)