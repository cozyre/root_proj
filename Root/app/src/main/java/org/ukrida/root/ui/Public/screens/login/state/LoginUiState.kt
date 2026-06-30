package org.ukrida.root.ui.Public.screens.login.state

data class LoginUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val loginSuccess: Boolean = false
)