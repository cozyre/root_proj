package org.ukrida.root.ui.admin.screens.ongoing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.AdminTripUpdateRequest
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.model.Member
import org.ukrida.root.data.remote.RetrofitClient
import org.ukrida.root.data.repository.AccountRepository
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.data.repository.MemberRepository
import org.ukrida.root.ui.admin.screens.finished.viewmodel.DocumentationUiModel
import org.ukrida.root.ui.admin.screens.finished.viewmodel.LeaderUiModel
import org.ukrida.root.ui.admin.screens.finished.viewmodel.MemberUiModel
import org.ukrida.root.utils.Resource
import java.io.File

data class TripState(
    val title: String,
    val description: String,
    val dateRange: String,

    val startDate: String?,
    val endDate: String?,

    val mentorId: Int?,
    val coordinatorId: Int?,

    val imageUrl: String?,
    val location: String?,
    val dresscode: String?,
    val meetupTime: String?,
    val meetupAddress: String?,
    val price: Int
)

data class LeaderOption(
    val id: Int,
    val name: String
)

data class OnGoingDetailUiState(
    val isLoading: Boolean = false,

    val isUploadingMainPhoto: Boolean = false,
    val mainPhotoMessage: String? = null,

    val tripState: TripState? = null,

    val members: List<MemberUiModel> = emptyList(),

    val documentations: List<DocumentationUiModel> = emptyList(),

    val mentorOptions: List<LeaderOption> = emptyList(),
    val coordinatorOptions: List<LeaderOption> = emptyList(),

    val mentor: LeaderUiModel? = null,
    val coordinator: LeaderUiModel? = null,

    val errorMessage: String? = null
)

class OnGoingDetailViewModel(
    val tripId: Int
) : ViewModel() {

    fun uploadMainPhoto(imageFile: File) {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(
                    isUploadingMainPhoto = true,
                    mainPhotoMessage = null,
                    errorMessage = null
                )

            when (
                val result =
                    galleryRepository.uploadImage(
                        groupId = tripId,
                        imageFile = imageFile,
                        caption = "cover"
                    )
            ) {
                is Resource.Success -> {
                    val uploadedImage = result.data

                    val currentTrip =
                        _uiState.value.tripState

                    _uiState.value =
                        _uiState.value.copy(
                            isUploadingMainPhoto = false,
                            mainPhotoMessage = "Foto utama berhasil disimpan",

                            // PENTING:
                            // Foto utama hanya update bagian atas,
                            // tidak dimasukkan ke grid dokumentasi.
                            tripState =
                                currentTrip?.copy(
                                    imageUrl = uploadedImage.imageUrl
                                )
                        )
                }

                is Resource.Error -> {
                    _uiState.value =
                        _uiState.value.copy(
                            isUploadingMainPhoto = false,
                            mainPhotoMessage =
                                result.message ?: "Gagal menyimpan foto utama"
                        )
                }

                is Resource.Loading -> {
                    _uiState.value =
                        _uiState.value.copy(
                            isUploadingMainPhoto = true
                        )
                }
            }
        }
    }
    private val groupRepository =
        GroupRepository(RetrofitClient.instance)

    private val memberRepository =
        MemberRepository(RetrofitClient.instance)

    private val accountRepository =
        AccountRepository(RetrofitClient.instance)

    private val galleryRepository =
        GalleryRepository(RetrofitClient.instance)

    private val adminRepository =
        AdminRepository(RetrofitClient.instance)

    private val _uiState =
        MutableStateFlow(OnGoingDetailUiState())

    val uiState: StateFlow<OnGoingDetailUiState> =
        _uiState.asStateFlow()

    init {
        loadDetail()
    }

    fun loadDetail() {

        viewModelScope.launch {

            _uiState.value =
                _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null
                )

            try {

                val groupDeferred = async {
                    groupRepository.getTourById(tripId)
                }

                val detailDeferred = async {
                    accountRepository.getGroupDetail(tripId)
                }

                val memberDeferred = async {
                    memberRepository.listMembers(tripId)
                }

                val imageDeferred = async {
                    galleryRepository.listImages(tripId)
                }

                val mentorListDeferred = async {
                    memberRepository.listByRole("mentor")
                }

                val coordinatorListDeferred = async {
                    memberRepository.listByRole("koordinator")
                }

                val groupResult = groupDeferred.await()
                val detailResult = detailDeferred.await()
                val memberResult = memberDeferred.await()
                val imageResult = imageDeferred.await()
                val mentorListResult = mentorListDeferred.await()
                val coordinatorListResult = coordinatorListDeferred.await()

                groupResult.onSuccess { group ->

                    val detail =
                        detailResult.getOrNull()

                    val memberUiList =
                        when (memberResult) {

                            is Resource.Success -> {
                                memberResult.data.map { member ->
                                    MemberUiModel(
                                        id = member.id.toString(),
                                        name = member.fullName,
                                        profilePhotoUrl =
                                            member.profilePhotoUrl
                                    )
                                }
                            }

                            else -> emptyList()
                        }

                    val allImages =
                        when (imageResult) {
                            is Resource.Success -> {
                                imageResult.data
                            }

                            else -> emptyList()
                        }

// Sementara konsepnya:
// gambar pertama = cover / foto utama
// gambar berikutnya = dokumentasi
                    val mainPhotoUrl =
                        allImages.firstOrNull()?.imageUrl

                    val documentationList =
                        allImages
                            .drop(1)
                            .map {
                                DocumentationUiModel(
                                    id = it.id.toString(),
                                    imageUrl = it.imageUrl
                                )
                            }

                    // Mentor & koordinator TIDAK selalu punya baris di `accounts`
                    // (mereka ditunjuk langsung lewat groups.mentor_id /
                    // groups.koordinator_id, bukan lewat pendaftaran ke grup),
                    // jadi opsi dropdown-nya diambil dari endpoint role khusus
                    // (member/leaders), bukan difilter dari listMembers(tripId).
                    val mentorOptions =
                        when (mentorListResult) {

                            is Resource.Success -> {
                                mentorListResult.data.map {
                                    LeaderOption(
                                        id = it.id,
                                        name = it.fullName
                                    )
                                }
                            }

                            else -> emptyList()
                        }

                    val coordinatorOptions =
                        when (coordinatorListResult) {

                            is Resource.Success -> {
                                coordinatorListResult.data.map {
                                    LeaderOption(
                                        id = it.id,
                                        name = it.fullName
                                    )
                                }
                            }

                            else -> emptyList()
                        }

                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            tripState = group.toTripState(
                                mentorId = detail?.mentor?.id,
                                coordinatorId = detail?.coordinator?.id,
                                imageUrl = mainPhotoUrl
                            ),

                            members = memberUiList,
                            documentations = documentationList,

                            mentorOptions = mentorOptions,
                            coordinatorOptions = coordinatorOptions,

                            mentor = detail?.mentor?.let {
                                LeaderUiModel(
                                    name = it.name,
                                    photoUrl = it.photo
                                )
                            },

                            coordinator = detail?.coordinator?.let {
                                LeaderUiModel(
                                    name = it.name,
                                    photoUrl = it.photo
                                )
                            }
                        )
                }

            } catch (e: Exception) {

                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
            }
        }
    }

    fun removeMember(member: MemberUiModel) {

        viewModelScope.launch {

            when (
                val result =
                    adminRepository.removeMember(
                        userId = member.id.toInt(),
                        groupId = tripId
                    )
            ) {

                is Resource.Success -> {

                    _uiState.value =
                        _uiState.value.copy(
                            members =
                                _uiState.value.members.filter {
                                    it.id != member.id
                                }
                        )
                }

                is Resource.Error -> {

                    _uiState.value =
                        _uiState.value.copy(
                            errorMessage =
                                result.message
                        )
                }

                is Resource.Loading -> {}
            }
        }
    }

    fun removeDocumentation(
        doc: DocumentationUiModel
    ) {

        viewModelScope.launch {

            when (
                val result =
                    adminRepository.removeImage(
                        doc.id.toInt()
                    )
            ) {

                is Resource.Success -> {

                    _uiState.value =
                        _uiState.value.copy(
                            documentations =
                                _uiState.value.documentations.filter {
                                    it.id != doc.id
                                }
                        )
                }

                is Resource.Error -> {

                    _uiState.value =
                        _uiState.value.copy(
                            errorMessage =
                                result.message
                        )
                }

                is Resource.Loading -> {}
            }
        }
    }

    fun updateTrip(
        title: String,
        description: String,
        mentorId: Int,
        coordinatorId: Int,
        price: Int
    ) {

        viewModelScope.launch {

            val trip =
                _uiState.value.tripState
                    ?: return@launch

            when (
                val result =
                    adminRepository.updateTrip(
                        AdminTripUpdateRequest(
                            id = tripId,
                            name = title,
                            description = description,

                            startDate =
                                trip.startDate ?: "",

                            endDate =
                                trip.endDate ?: "",

                            location =
                                trip.location,

                            dresscode =
                                trip.dresscode,

                            meetupTime =
                                trip.meetupTime,

                            meetupAddress =
                                trip.meetupAddress,

                            mentorId =
                                mentorId,

                            koordinatorId =
                                coordinatorId,
                            
                            price = price
                        )
                    )
            ) {

                is Resource.Success -> {
                    loadDetail()
                }

                is Resource.Error -> {

                    _uiState.value =
                        _uiState.value.copy(
                            errorMessage =
                                result.message
                        )
                }

                is Resource.Loading -> {}
            }
        }
    }

    private fun Group.toTripState(
        mentorId: Int?,
        coordinatorId: Int?,
        imageUrl: String?
    ) = TripState(
        title = name,
        description = description ?: "",
        dateRange = "${startDate ?: "-"} - ${endDate ?: "-"}",

        startDate = startDate,
        endDate = endDate,

        mentorId = mentorId,
        coordinatorId = coordinatorId,

        imageUrl = imageUrl,

        location = location,
        dresscode = dresscode,
        meetupTime = meetupTime,
        meetupAddress = meetupAddress,
        price = price
    )

    private fun Member.toUiModel() =
        MemberUiModel(
            id = id.toString(),
            name = fullName,
            profilePhotoUrl = profilePhotoUrl
        )

    companion object {

        fun factory(
            tripId: Int
        ): ViewModelProvider.Factory =

            object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>
                ): T {

                    return OnGoingDetailViewModel(
                        tripId
                    ) as T
                }
            }
    }
}