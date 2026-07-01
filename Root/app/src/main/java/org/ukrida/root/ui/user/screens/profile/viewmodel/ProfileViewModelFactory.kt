package org.ukrida.root.ui.user.screens.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ukrida.root.data.repository.ProfileRepository

class ProfileViewModelFactory(
    private val profileRepository: ProfileRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(
                profileRepository
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}