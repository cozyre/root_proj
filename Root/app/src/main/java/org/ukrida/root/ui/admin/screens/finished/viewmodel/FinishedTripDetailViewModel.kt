package org.ukrida.root.ui.admin.screens.finished.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.dummy.DummyGroupData
import org.ukrida.root.data.dummy.DummyMemberData
import org.ukrida.root.data.fake.FakeGroupRepository
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.model.Member

data class TripState(
    val title: String,
    val description: String,
    val dateRange: String,
    val imageUrl: String?,
    val location: String?,
    val dresscode: String?,
    val meetupTime: String?,
    val meetupAddress: String?
)

data class FinishedTripDetailUiState(
    val isLoading: Boolean = false,
    val tripState: TripState? = null,
    val members: List<MemberUiModel> = emptyList(),
    val documentations: List<DocumentationUiModel> = emptyList(),
    val errorMessage: String? = null
)

class FinishedTripDetailViewModel(
    val tripId: Int
) : ViewModel() {

    private val groupRepository = FakeGroupRepository()

    private val _uiState =
        MutableStateFlow(FinishedTripDetailUiState())

    val uiState: StateFlow<FinishedTripDetailUiState> =
        _uiState.asStateFlow()

    init {
        loadDetail()
    }

    private fun loadDetail() {
        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            val groupDeferred = async {
                groupRepository.getTourById(tripId)
            }

            val groupResult = groupDeferred.await()

            groupResult
                .onSuccess { group ->

                    val tripState = group.toTripState()

                    val memberList = DummyMemberData.members
                        .map { it.toUiModel() }

                    val documentationList =
                        DummyGroupData.groupImages
                            .filter { it.groupId == tripId }
                            .map {
                                DocumentationUiModel(
                                    id = it.id.toString(),
                                    imageUrl = it.imageUrl
                                )
                            }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        tripState = tripState,
                        members = memberList,
                        documentations = documentationList
                    )
                }
                .onFailure { error ->

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message
                            ?: "Failed to load trip"
                    )
                }
        }
    }

    private fun Group.toTripState() = TripState(
        title = name,
        description = description ?: "",
        dateRange = "${startDate ?: "-"} - ${endDate ?: "-"}",
        imageUrl = null,
        location = location,
        dresscode = dresscode,
        meetupTime = meetupTime,
        meetupAddress = meetupAddress
    )

    private fun Member.toUiModel() = MemberUiModel(
        id = id.toString(),
        name = fullName,
        profilePhotoUrl = profilePhotoUrl
    )

    companion object {
        fun factory(tripId: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return FinishedTripDetailViewModel(
                        tripId
                    ) as T
                }
            }
    }
}