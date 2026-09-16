package org.ukrida.root.ui.admin.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Profile
import org.ukrida.root.data.repository.ProfileRepository
import org.ukrida.root.utils.Resource

class DrawerViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    private val _profile = MutableStateFlow<Resource<Profile>>(Resource.Loading())
    val profile: StateFlow<Resource<Profile>> = _profile.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _profile.value = Resource.Loading()
            _profile.value = profileRepository.getProfile()
        }
    }

    companion object {
        fun factory(profileRepository: ProfileRepository): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DrawerViewModel(profileRepository) as T
            }
        }
    }
}
