package org.ukrida.root.ui.user.screens.historydetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.GroupDetail
import org.ukrida.root.data.model.GroupImage
import org.ukrida.root.data.model.Member
import org.ukrida.root.data.repository.AccountRepository
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.data.repository.MemberRepository
import org.ukrida.root.utils.Resource

class HistoryDetailViewModel(
    private val accountRepository: AccountRepository,
    private val galleryRepository: GalleryRepository,
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryDetailUiState())
    val uiState: StateFlow<HistoryDetailUiState> = _uiState.asStateFlow()

    data class HistoryDetailUiState(
        val group: Resource<GroupDetail> = Resource.Loading(),
        val members: Resource<List<Member>> = Resource.Loading(),
        val gallery: Resource<List<GroupImage>> = Resource.Loading()
    )

    fun loadHistoryDetail(groupId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    group = Resource.Loading(),
                    members = Resource.Loading(),
                    gallery = Resource.Loading()
                )
            }

            // Fetch Group Details
            val groupResult = accountRepository.getGroupDetail(groupId)
            
            // Fetch Members and Gallery
            val membersRes = memberRepository.listMembers(groupId)
            val galleryRes = galleryRepository.listImages(groupId)

            if (groupResult.isSuccess) {
                val groupData = groupResult.getOrNull()
                _uiState.update {
                    it.copy(
                        group = if (groupData != null) Resource.Success(groupData) else Resource.Error("Group details not found"),
                        members = membersRes,
                        gallery = galleryRes
                    )
                }
            } else {
                val errorMsg = groupResult.exceptionOrNull()?.message ?: "Failed to load group details"
                _uiState.update {
                    it.copy(
                        group = Resource.Error(errorMsg),
                        members = membersRes,
                        gallery = galleryRes
                    )
                }
            }
        }
    }
}
