package org.ukrida.root.ui.user.screens.profile.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.ukrida.root.data.model.Profile

class ProfileViewModel : ViewModel() {

    // ---------------- Profile ----------------

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile = _profile.asStateFlow()

    // ---------------- Form ----------------

    private val _firstName = MutableStateFlow("")
    val firstName = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName = _lastName.asStateFlow()

    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _phone = MutableStateFlow("")
    val phone = _phone.asStateFlow()

    private val _bio = MutableStateFlow("")
    val bio = _bio.asStateFlow()

    private val _hidePhone = MutableStateFlow(false)
    val hidePhone = _hidePhone.asStateFlow()

    // ---------------- Loading ----------------

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving = _isSaving.asStateFlow()

    // ---------------- Setter ----------------

    fun onFirstNameChange(value: String) {
        _firstName.value = value
    }

    fun onLastNameChange(value: String) {
        _lastName.value = value
    }

    fun onUsernameChange(value: String) {
        _username.value = value
    }

    fun onPhoneChange(value: String) {
        _phone.value = value
    }

    fun onBioChange(value: String) {
        _bio.value = value
    }

    fun onHidePhoneChange(value: Boolean) {
        _hidePhone.value = value
    }

    // ---------------- Load ----------------

    fun loadProfile() {

        // TODO Backend Integration
        // repository.getProfile()

        loadDummy()

    }

    // ---------------- Save ----------------

    fun saveProfile() {

        // TODO Backend Integration
        // repository.updateProfile(
        //     firstName.value,
        //     lastName.value,
        //     username.value,
        //     phone.value,
        //     bio.value,
        //     hidePhone.value
        // )

    }

    // ---------------- Dummy ----------------

    private fun loadDummy() {

        val dummy = Profile(

            id = 1,

            username = "josh",

            firstName = "Josh",

            lastName = "Valentino",

            email = "josh@email.com",

            phone = "08123456789",

            profilePhotoUrl = null,

            bio = "Informatics student at UKRIDA who enjoys mobile development and UI design.",

            hidePhone = false,

            role = "Member",

            createdAt = ""

        )

        _profile.value = dummy

        _firstName.value = dummy.firstName
        _lastName.value = dummy.lastName
        _username.value = dummy.username
        _email.value = dummy.email
        _phone.value = dummy.phone ?: ""
        _bio.value = dummy.bio ?: ""
        _hidePhone.value = dummy.hidePhone

    }

}