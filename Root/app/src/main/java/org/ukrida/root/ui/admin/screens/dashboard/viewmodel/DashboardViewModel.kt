package org.ukrida.root.ui.admin.screens.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.ukrida.root.data.model.Group
import org.ukrida.root.data.model.PendingAccount
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.data.repository.GroupRepository
import org.ukrida.root.utils.Resource

data class ApprovalItem(
    val accountId: Int,
    val userId: Int,
    val userName: String,
    val groupName: String,
    val joinDate: String,
    val profilePhotoUrl: String?
)

class DashboardViewModel(
    private val adminRepository: AdminRepository,
    private val groupRepository: GroupRepository
) : ViewModel() {

    // Pending approvals
    private val _pendingApprovals = MutableStateFlow<Resource<List<ApprovalItem>>>(Resource.Loading())
    val pendingApprovals: StateFlow<Resource<List<ApprovalItem>>> = _pendingApprovals

    // Latest tour
    private val _latestTour = MutableStateFlow<Resource<Group>>(Resource.Loading())
    val latestTour: StateFlow<Resource<Group>> = _latestTour

    init {
        loadDashboard()
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            loadPendingApprovals()
            loadLatestTour()
        }
    }

    private suspend fun loadPendingApprovals() {
        _pendingApprovals.value = Resource.Loading()

        val result = adminRepository.getPendingAccounts()

        if (result.isSuccess) {
            val pending = result.getOrNull() ?: emptyList()

            // Map raw API response to ApprovalItem (UI-specific model)
            val items = pending
                .map { account ->
                    ApprovalItem(
                        accountId = account.id,
                        userId = account.userId,
                        userName = account.fullName,
                        groupName = account.groupName,
                        joinDate = account.joinDate,
                        profilePhotoUrl = account.profilePhotoUrl
                    )
                }
                .take(3) // Show only the 3 most recent

            _pendingApprovals.value = Resource.Success(items)
        } else {
            _pendingApprovals.value = Resource.Error(
                result.exceptionOrNull()?.message ?: "Failed to load pending approvals"
            )
        }
    }

    private suspend fun loadLatestTour() {
        _latestTour.value = Resource.Loading()

        val result = groupRepository.getAllTours()

        if (result.isSuccess) {
            val tours = result.getOrNull() ?: emptyList()
            val latest = tours.maxByOrNull { it.id }

            if (latest != null) {
                _latestTour.value = Resource.Success(latest)
            } else {
                _latestTour.value = Resource.Error("No tours available")
            }
        } else {
            _latestTour.value = Resource.Error(
                result.exceptionOrNull()?.message ?: "Failed to load latest tour"
            )
        }
    }

    // Reload methods for pull-to-refresh or manual refresh
    fun refreshApprovals() {
        viewModelScope.launch { loadPendingApprovals() }
    }

    fun refreshLatestTour() {
        viewModelScope.launch { loadLatestTour() }
    }
}