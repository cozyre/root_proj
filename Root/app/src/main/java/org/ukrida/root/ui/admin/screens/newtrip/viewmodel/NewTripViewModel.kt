package org.ukrida.root.ui.admin.screens.newtrip.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.AdminTripRequest
import org.ukrida.root.data.model.Member
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.data.repository.MemberRepository
import org.ukrida.root.utils.Resource

data class LeaderOptionUiState(
    val id: Int,
    val name: String
)

data class NewTripFormState(
    val title: String = "",
    val description: String = "",
    val startDate: String = "",
    val endDate: String = "",

    val pricePlaceholder: String = "xxx.xxx.xxx.xxx",

    val location: String = "",
    val dresscode: String = "",
    val meetupTime: String = "",
    val meetupAddress: String = "",

    val selectedMentorId: Int? = null,
    val selectedMentorName: String = "",

    val selectedCoordinatorId: Int? = null,
    val selectedCoordinatorName: String = "",

    val mentorOptions: List<LeaderOptionUiState> = emptyList(),
    val coordinatorOptions: List<LeaderOptionUiState> = emptyList()
)

class NewTripViewModel(
    private val adminRepository: AdminRepository,
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _form = MutableStateFlow(NewTripFormState())
    val form: StateFlow<NewTripFormState> = _form.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _createdTripId = MutableStateFlow<Int?>(null)
    val createdTripId: StateFlow<Int?> = _createdTripId.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadLeaderOptions()
    }

    private fun loadLeaderOptions() {
        viewModelScope.launch {
            val mentorResult = async {
                memberRepository.listByRole("mentor")
            }

            val coordinatorResult = async {
                memberRepository.listByRole("koordinator")
            }

            val mentors = when (val result = mentorResult.await()) {
                is Resource.Success -> result.data.map { it.toLeaderOption() }
                is Resource.Error -> emptyList()
                is Resource.Loading -> emptyList()
            }

            val coordinators = when (val result = coordinatorResult.await()) {
                is Resource.Success -> result.data.map { it.toLeaderOption() }
                is Resource.Error -> emptyList()
                is Resource.Loading -> emptyList()
            }

            _form.value = _form.value.copy(
                mentorOptions = mentors,
                coordinatorOptions = coordinators
            )
        }
    }

    private fun Member.toLeaderOption(): LeaderOptionUiState {
        val fullName = listOfNotNull(
            firstName,
            lastName
        ).joinToString(" ").trim()

        return LeaderOptionUiState(
            id = id,
            name = fullName.ifBlank { username }
        )
    }

    fun updateTitle(value: String) {
        _form.value = _form.value.copy(title = value)
    }

    fun updateDescription(value: String) {
        _form.value = _form.value.copy(description = value)
    }

    fun updateStartDate(value: String) {
        _form.value = _form.value.copy(startDate = value)
    }

    fun updateEndDate(value: String) {
        _form.value = _form.value.copy(endDate = value)
    }

    fun updateLocation(value: String) {
        _form.value = _form.value.copy(location = value)
    }

    fun updateDresscode(value: String) {
        _form.value = _form.value.copy(dresscode = value)
    }

    fun updateMeetupTime(value: String) {
        _form.value = _form.value.copy(meetupTime = value)
    }

    fun updateMeetupAddress(value: String) {
        _form.value = _form.value.copy(meetupAddress = value)
    }

    fun updateSelectedMentor(id: Int, name: String) {
        _form.value = _form.value.copy(
            selectedMentorId = id,
            selectedMentorName = name
        )
    }

    fun updateSelectedCoordinator(id: Int, name: String) {
        _form.value = _form.value.copy(
            selectedCoordinatorId = id,
            selectedCoordinatorName = name
        )
    }

    fun submitGeneralInformation() {
        val currentForm = _form.value

        if (currentForm.title.isBlank()) {
            _errorMessage.value = "Title tidak boleh kosong"
            return
        }

        if (currentForm.description.isBlank()) {
            _errorMessage.value = "Description tidak boleh kosong"
            return
        }

        if (currentForm.startDate.isBlank()) {
            _errorMessage.value = "Start date tidak boleh kosong"
            return
        }

        if (currentForm.endDate.isBlank()) {
            _errorMessage.value = "End date tidak boleh kosong"
            return
        }

        val mentorId = currentForm.selectedMentorId
        if (mentorId == null) {
            _errorMessage.value = "Mentor harus dipilih"
            return
        }

        val coordinatorId = currentForm.selectedCoordinatorId
        if (coordinatorId == null) {
            _errorMessage.value = "Coordinator harus dipilih"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val request = AdminTripRequest(
                name = currentForm.title,
                description = currentForm.description,
                startDate = currentForm.startDate,
                endDate = currentForm.endDate,
                location = currentForm.location.ifBlank { null },
                dresscode = currentForm.dresscode.ifBlank { null },
                meetupTime = currentForm.meetupTime.ifBlank { null },
                meetupAddress = currentForm.meetupAddress.ifBlank { null },
                mentorId = mentorId,
                koordinatorId = coordinatorId
            )

            when (val result = adminRepository.createTrip(request)) {
                is Resource.Success -> {
                    _createdTripId.value = result.data.group.id
                }

                is Resource.Error -> {
                    _errorMessage.value = result.message
                }

                is Resource.Loading -> Unit
            }

            _isLoading.value = false
        }
    }

    fun clearCreatedTripId() {
        _createdTripId.value = null
    }

    fun clearError() {
        _errorMessage.value = null
    }

    companion object {
        fun factory(
            adminRepository: AdminRepository,
            memberRepository: MemberRepository
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NewTripViewModel(
                        adminRepository = adminRepository,
                        memberRepository = memberRepository
                    ) as T
                }
            }
        }
    }
}