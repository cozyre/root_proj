package org.ukrida.root.ui.admin.screens.ongoing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.dummy.DummyMemberData
import org.ukrida.root.data.fake.FakeGroupRepository
import org.ukrida.root.data.fake.FakeMemberRepository
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.model.Member
import org.ukrida.root.utils.Resource

data class MemberUiModel(
    val id: String,
    val name: String,
    val profilePhotoUrl: String?
)

data class DocumentationUiModel(
    val id: String,
    val imageUrl: String?
)
// Data class untuk state layar detail
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

data class OnGoingDetailUiState(
    val isLoading: Boolean = false,
    val tripState: TripState? = null,
    val members: List<MemberUiModel> = emptyList(),
    val documentations: List<DocumentationUiModel> = emptyList(),
    val errorMessage: String? = null
)

class OnGoingDetailViewModel(
    val tripId: Int
) : ViewModel() {

    private val groupRepository = FakeGroupRepository()
    private val memberRepository = FakeMemberRepository()

    private val _uiState = MutableStateFlow(OnGoingDetailUiState())
    val uiState: StateFlow<OnGoingDetailUiState> = _uiState.asStateFlow()

    // Tetap expose tripState langsung untuk kompatibilitas dengan screen yang sudah ada
    val tripState: StateFlow<TripState?> = MutableStateFlow<TripState?>(null).also { flow ->
        viewModelScope.launch {
            uiState.collect { flow.value = it.tripState }
        }
    }
    fun loadDetail() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            // Load group
            val groupDeferred = async {
                groupRepository.getTourById(tripId)
            }

            val groupResult = groupDeferred.await()

            // Ambil member yang sudah approved dan sudah bayar
            val memberUiList = DummyMemberData.members
                .map { it.toUiModel() }

            // Handle group result
            groupResult
                .onSuccess { group ->
                    val tripState = group.toTripState()

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        tripState = tripState,
                        members = memberUiList
                    )
                }
                .onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Gagal memuat data trip",
                        members = emptyList()
                    )
                }

            // Dokumentasi berdasarkan tripId
            val docList = org.ukrida.root.data.dummy.DummyGroupData.groupImages
                .filter { it.groupId == tripId }
                .map {
                    DocumentationUiModel(
                        id = it.id.toString(),
                        imageUrl = it.imageUrl
                    )
                }

            _uiState.value = _uiState.value.copy(
                documentations = docList
            )
        }
    }

    init {
        loadDetail()
    }

    fun removeMember(member: MemberUiModel) {
        val updated = _uiState.value.members.filter { it.id != member.id }
        _uiState.value = _uiState.value.copy(members = updated)
    }

    fun removeDocumentation(doc: DocumentationUiModel) {
        val updated = _uiState.value.documentations.filter { it.id != doc.id }
        _uiState.value = _uiState.value.copy(documentations = updated)
    }

    // Extension functions mapper
    private fun Group.toTripState() = TripState(
        title = name,
        description = description ?: "",
        dateRange = "${startDate ?: "-"} - ${endDate ?: "-"}",
        imageUrl = null, // Group belum punya imageUrl, nanti dari API
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

    // Factory untuk passing tripId ke ViewModel
    companion object {
        fun factory(tripId: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return OnGoingDetailViewModel(tripId) as T
                }
            }
    }
}