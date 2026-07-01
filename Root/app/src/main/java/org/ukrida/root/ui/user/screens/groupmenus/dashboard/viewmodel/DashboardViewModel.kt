package org.ukrida.root.ui.user.screens.groupmenus.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.model.GroupDetail
import org.ukrida.root.data.model.GroupImage
import org.ukrida.root.data.model.Member
import org.ukrida.root.data.repository.AccountRepository
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.data.repository.MemberRepository
import org.ukrida.root.utils.Resource

class DashboardViewModel(
    private val accountRepository: AccountRepository,
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val _group = MutableStateFlow<GroupDetail?>(null)
    val group = _group.asStateFlow()

    private val _gallery = MutableStateFlow<List<GroupImage>>(emptyList())
    val gallery = _gallery.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun loadDashboard(groupId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            // Fetch Group Details
            val groupResult = accountRepository.getGroupDetail(groupId)
            groupResult.onSuccess {
                _group.value = it
            }.onFailure {
                _errorMessage.value = it.message ?: "Failed to load group details"
            }

            // Fetch Gallery Images
            when (val galleryRes = galleryRepository.listImages(groupId)) {
                is Resource.Success -> _gallery.value = galleryRes.data.take(1)
                is Resource.Error -> {
                    if (_errorMessage.value == null) _errorMessage.value = galleryRes.message
                }
                else -> {}
            }

            _isLoading.value = false
        }
    }

}