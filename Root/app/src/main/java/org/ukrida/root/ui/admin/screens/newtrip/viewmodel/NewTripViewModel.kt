package org.ukrida.root.ui.admin.screens.newtrip.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.AdminTripRequest
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.data.repository.MemberRepository
import org.ukrida.root.utils.Resource
import java.io.File

data class LeaderOptionUiState(
    val id: Int,
    val name: String
)

data class NewTripFormState(
    val title: String = "",
    val description: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val location: String = "",
    val dresscode: String = "",
    val meetupTime: String = "",
    val meetupAddress: String = "",
    val mentorId: Int? = null,
    val coordinatorId: Int? = null,
    val price: String = ""
)

data class NewTripUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val createdTripId: Int? = null,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val form: NewTripFormState = NewTripFormState(),
    val mentorOptions: List<LeaderOptionUiState> = emptyList(),
    val coordinatorOptions: List<LeaderOptionUiState> = emptyList()
)

class NewTripViewModel(
    private val adminRepository: AdminRepository,
    private val galleryRepository: GalleryRepository,
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewTripUiState())
    val uiState: StateFlow<NewTripUiState> = _uiState.asStateFlow()

    init {
        loadLeaderOptions()
    }

    private fun loadLeaderOptions() {
        viewModelScope.launch {
            val mentorResult = memberRepository.listByRole("mentor")
            val coordinatorResult = memberRepository.listByRole("koordinator")

            val mentors =
                if (mentorResult is Resource.Success) {
                    mentorResult.data.map {
                        LeaderOptionUiState(
                            id = it.id,
                            name = it.fullName
                        )
                    }
                } else {
                    emptyList()
                }

            val coordinators =
                if (coordinatorResult is Resource.Success) {
                    coordinatorResult.data.map {
                        LeaderOptionUiState(
                            id = it.id,
                            name = it.fullName
                        )
                    }
                } else {
                    emptyList()
                }

            _uiState.value =
                _uiState.value.copy(
                    mentorOptions = mentors,
                    coordinatorOptions = coordinators
                )
        }
    }

    fun updateTitle(value: String) {
        updateForm { it.copy(title = value) }
    }

    fun updateDescription(value: String) {
        updateForm { it.copy(description = value) }
    }

    fun updateStartDate(value: String) {
        updateForm { it.copy(startDate = value) }
    }

    fun updateEndDate(value: String) {
        updateForm { it.copy(endDate = value) }
    }

    fun updateLocation(value: String) {
        updateForm { it.copy(location = value) }
    }

    fun updateDresscode(value: String) {
        updateForm { it.copy(dresscode = value) }
    }

    fun updateMeetupTime(value: String) {
        updateForm { it.copy(meetupTime = value) }
    }

    fun updateMeetupAddress(value: String) {
        updateForm { it.copy(meetupAddress = value) }
    }

    fun updateMentorId(value: Int) {
        updateForm { it.copy(mentorId = value) }
    }

    fun updateCoordinatorId(value: Int) {
        updateForm { it.copy(coordinatorId = value) }
    }

    fun updatePrice(value: String) {
        updateForm { it.copy(price = value) }
    }

    private fun updateForm(
        updater: (NewTripFormState) -> NewTripFormState
    ) {
        _uiState.value =
            _uiState.value.copy(
                form = updater(_uiState.value.form),
                errorMessage = null,
                successMessage = null,
                isSuccess = false,
                createdTripId = null
            )
    }

    fun createTrip(
        imageFile: File?
    ) {
        val form = _uiState.value.form

        if (form.title.isBlank()) {
            setError("Trip title cannot be empty")
            return
        }

        if (form.startDate.isBlank()) {
            setError("Start date cannot be empty")
            return
        }

        if (form.endDate.isBlank()) {
            setError("End date cannot be empty")
            return
        }

        if (form.mentorId == null) {
            setError("Please select mentor")
            return
        }

        if (form.coordinatorId == null) {
            setError("Please select coordinator")
            return
        }

        val priceInt = form.price.toIntOrNull()
        if (priceInt == null) {
            setError("Invalid price format")
            return
        }

        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null,
                    successMessage = null,
                    isSuccess = false,
                    createdTripId = null
                )

            when (
                val createResult =
                    adminRepository.createTrip(
                        AdminTripRequest(
                            name = form.title,
                            description = form.description.ifBlank { null },
                            startDate = form.startDate,
                            endDate = form.endDate,
                            location = form.location.ifBlank { null },
                            dresscode = form.dresscode.ifBlank { null },
                            meetupTime = form.meetupTime.ifBlank { null },
                            meetupAddress = form.meetupAddress.ifBlank { null },
                            mentorId = form.mentorId,
                            koordinatorId = form.coordinatorId,
                            price = priceInt
                        )
                    )
            ) {
                is Resource.Success -> {
                    val newGroupId = createResult.data.group.id

                    if (imageFile != null) {
                        when (
                            val uploadResult =
                                galleryRepository.uploadImage(
                                    groupId = newGroupId,
                                    imageFile = imageFile,
                                    caption = "Main trip photo"
                                )
                        ) {
                            is Resource.Success -> {
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        isSuccess = true,
                                        createdTripId = newGroupId,
                                        successMessage = "Trip and photo created successfully",
                                        errorMessage = null
                                    )
                            }

                            is Resource.Error -> {
                                _uiState.value =
                                    _uiState.value.copy(
                                        isLoading = false,
                                        isSuccess = true,
                                        createdTripId = newGroupId,
                                        successMessage = "Trip created, but photo upload failed",
                                        errorMessage = uploadResult.message
                                    )
                            }

                            is Resource.Loading -> Unit
                        }
                    } else {
                        _uiState.value =
                            _uiState.value.copy(
                                isLoading = false,
                                isSuccess = true,
                                createdTripId = newGroupId,
                                successMessage = "Trip created successfully",
                                errorMessage = null
                            )
                    }
                }

                is Resource.Error -> {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            isSuccess = false,
                            createdTripId = null,
                            errorMessage = createResult.message
                        )
                }

                is Resource.Loading -> Unit
            }
        }
    }

    private fun setError(message: String) {
        _uiState.value =
            _uiState.value.copy(
                isLoading = false,
                errorMessage = message,
                isSuccess = false,
                successMessage = null,
                createdTripId = null
            )
    }

    companion object {
        fun factory(
            adminRepository: AdminRepository,
            galleryRepository: GalleryRepository,
            memberRepository: MemberRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return NewTripViewModel(
                        adminRepository = adminRepository,
                        galleryRepository = galleryRepository,
                        memberRepository = memberRepository
                    ) as T
                }
            }
    }
}