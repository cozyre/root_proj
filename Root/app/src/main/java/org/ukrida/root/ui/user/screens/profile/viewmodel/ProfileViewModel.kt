package org.ukrida.root.ui.user.screens.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
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

    private val _updateState = MutableStateFlow<Resource<Unit>>(Resource.Success(Unit))
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
                    _profile.value = Resource.Success(result.data)
                    _updateState.value = Resource.Success(Unit)
                    loadProfile()
                }

                is Resource.Error -> {
                    _updateState.value = Resource.Error(result.message)
                }

                is Resource.Loading -> {}
            }
        }
    }

    // ─── Upload Profile Photo ────────────────────────────────────────────────

    fun uploadProfilePhoto(image: MultipartBody.Part) {
        viewModelScope.launch {
            _updateState.value = Resource.Loading()
            val result = profileRepository.uploadProfilePhoto(image)
            when (result) {
                is Resource.Success -> {
                    _profile.value = Resource.Success(result.data)
                    _updateState.value = Resource.Success(Unit)
                    loadProfile()
                }
                is Resource.Error -> {
                    _updateState.value = Resource.Error(result.message)
                }
                is Resource.Loading -> {}
            }
        }
    }

    // ─── Reset Update State ──────────────────────────────────────────────────

    fun clearUpdateState() {
        _updateState.value = Resource.Success(Unit)
    }
}
