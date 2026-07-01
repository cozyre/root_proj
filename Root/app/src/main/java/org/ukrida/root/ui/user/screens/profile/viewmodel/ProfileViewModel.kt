package org.ukrida.root.ui.user.screens.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Profile
import org.ukrida.root.data.repository.ProfileRepository
import org.ukrida.root.utils.Resource

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    // ─── Profile Data ────────────────────────────────────────────────────────

    private val _profile = MutableStateFlow<Resource<Profile>>(Resource.Loading())
    val profile: StateFlow<Resource<Profile>> = _profile.asStateFlow()

    // ─── Update State (separate tracking for update operation) ────────────────

    private val _updateState = MutableStateFlow<Resource<Unit>>(Resource.Loading())
    val updateState: StateFlow<Resource<Unit>> = _updateState.asStateFlow()

    init {
        loadProfile()
    }

    // ─── Load Profile ────────────────────────────────────────────────────────

    fun loadProfile() {
        viewModelScope.launch {
            _profile.value = Resource.Loading()
            _profile.value = profileRepository.getProfile()
        }
    }

    // ─── Update Profile ──────────────────────────────────────────────────────

    /**
     * Update user profile. On success, refreshes the profile from server
     * so the user sees the latest data immediately.
     * _updateState tracks if the operation succeeded or failed.
     */
    fun updateProfile(
        firstName: String,
        lastName: String,
        username: String,
        phone: String?,
        bio: String?,
        hidePhone: Boolean
    ) {
        viewModelScope.launch {
            _updateState.value = Resource.Loading()

            val result = profileRepository.updateProfile(
                firstName, lastName, username, phone, bio, hidePhone
            )

            when (result) {
                is Resource.Success -> {
                    // Immediately update profile with returned data
                    _profile.value = Resource.Success(result.data)
                    // Signal success
                    _updateState.value = Resource.Success(Unit)
                    // Refresh to ensure consistency with server
                    loadProfile()
                }

                is Resource.Error -> {
                    // Signal error, profile remains unchanged
                    _updateState.value = Resource.Error(result.message)
                }

                is Resource.Loading -> {
                    // Already set above, shouldn't reach here
                }
            }
        }
    }

    // ─── Reset Update State ──────────────────────────────────────────────────

    /**
     * Call this after the user has seen the update result (success/error).
     * Clears the update state so the UI stops showing the result message.
     */
    fun clearUpdateState() {
        _updateState.value = Resource.Loading()
    }
}