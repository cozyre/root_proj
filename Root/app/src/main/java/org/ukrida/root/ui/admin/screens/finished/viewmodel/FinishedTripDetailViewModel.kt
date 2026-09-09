package org.ukrida.root.ui.admin.screens.finished.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.model.GroupImage
import org.ukrida.root.data.model.Member
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.data.repository.MemberRepository
import org.ukrida.root.utils.Resource

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
    val tripId: Int,
    private val groupRepository: GroupRepository,
    private val memberRepository: MemberRepository,
    private val galleryRepository: GalleryRepository
) : ViewModel() {

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

            val groupDeferred = async { groupRepository.getTourById(tripId) }
            val membersDeferred = async { memberRepository.listMembers(tripId) }
            val imagesDeferred = async { galleryRepository.listImages(tripId) }

            val groupResult = groupDeferred.await()
            val membersResult = membersDeferred.await()
            val imagesResult = imagesDeferred.await()

            groupResult
                .onSuccess { group ->

                    val tripState = group.toTripState()

                    val memberList = when (membersResult) {
                        is Resource.Success -> membersResult.data.map { it.toUiModel() }
                        else -> emptyList()
                    }

                    val documentationList = when (imagesResult) {
                        is Resource.Success -> imagesResult.data.map { it.toUiModel() }
                        else -> emptyList()
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

    private fun GroupImage.toUiModel() = DocumentationUiModel(
        id = id.toString(),
        imageUrl = imageUrl
    )

    companion object {
        fun factory(
            tripId: Int,
            groupRepository: GroupRepository,
            memberRepository: MemberRepository,
            galleryRepository: GalleryRepository
        ): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {
                    return FinishedTripDetailViewModel(
                        tripId,
                        groupRepository,
                        memberRepository,
                        galleryRepository
                    ) as T
                }
            }
    }
}