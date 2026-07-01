package org.ukrida.root.ui.user.screens.groupmenus.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.GroupDetail
import org.ukrida.root.data.model.GroupImage
import org.ukrida.root.data.repository.AccountRepository
import org.ukrida.root.data.repository.GalleryRepository
import org.ukrida.root.utils.Resource

class DashboardViewModel(
    private val accountRepository: AccountRepository,
    private val galleryRepository: GalleryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    data class DashboardUiState(
        val group: Resource<GroupDetail> = Resource.Loading(),
        val gallery: Resource<List<GroupImage>> = Resource.Loading()
    )

    fun loadDashboard(groupId: Int) {
        viewModelScope.launch {
            fetchDashboardData(groupId)
        }
    }

    private suspend fun fetchDashboardData(groupId: Int) {
        _uiState.update {
            it.copy(
                group = Resource.Loading(),
                gallery = Resource.Loading()
            )
        }

        coroutineScope {
            val groupDeferred = async { accountRepository.getGroupDetail(groupId) }
            val galleryDeferred = async { galleryRepository.listImages(groupId) }

            val groupResult = groupDeferred.await()
            val galleryRes = galleryDeferred.await()

            val groupResource = if (groupResult.isSuccess) {
                Resource.Success(groupResult.getOrThrow())
            } else {
                Resource.Error(groupResult.exceptionOrNull()?.message ?: "Failed to load group details")
            }

            val galleryResource = when (galleryRes) {
                is Resource.Success -> Resource.Success(galleryRes.data.take(1))
                is Resource.Error -> Resource.Error(galleryRes.message)
                else -> Resource.Loading()
            }

            _uiState.update {
                it.copy(
                    group = groupResource,
                    gallery = galleryResource
                )
            }
        }
    }
}
