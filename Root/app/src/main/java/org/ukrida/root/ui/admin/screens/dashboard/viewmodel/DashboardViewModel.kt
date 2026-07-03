package org.ukrida.root.ui.admin.screens.dashboard.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

class DashboardViewModel(
    private val adminRepository: AdminRepository,
    private val groupRepository: GroupRepository
) : ViewModel() {

    var processingAccountId by mutableStateOf<Int?>(null)
        private set

    var approvalActionError by mutableStateOf<String?>(null)
        private set

    // Pending approvals
    private val _pendingApprovals =
        MutableStateFlow<Resource<List<PendingAccount>>>(Resource.Loading())
    val pendingApprovals: StateFlow<Resource<List<PendingAccount>>> = _pendingApprovals

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
            _pendingApprovals.value = Resource.Success(pending)
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

    fun refreshApprovals() {
        viewModelScope.launch {
            loadPendingApprovals()
        }
    }

    fun refreshLatestTour() {
        viewModelScope.launch {
            loadLatestTour()
        }
    }

    fun approve(accountId: Int) {
        updateApprovalStatus(
            accountId = accountId,
            isApprove = true
        )
    }

    fun reject(accountId: Int) {
        updateApprovalStatus(
            accountId = accountId,
            isApprove = false
        )
    }

    private fun updateApprovalStatus(
        accountId: Int,
        isApprove: Boolean
    ) {
        viewModelScope.launch {
            processingAccountId = accountId
            approvalActionError = null

            val result = if (isApprove) {
                adminRepository.approveOrder(accountId)
            } else {
                adminRepository.rejectOrder(accountId)
            }

            when (result) {
                is Resource.Success -> {
                    val currentPending =
                        (_pendingApprovals.value as? Resource.Success)?.data.orEmpty()

                    _pendingApprovals.value = Resource.Success(
                        currentPending.filterNot { it.id == accountId }
                    )
                }

                is Resource.Error -> {
                    approvalActionError = result.message
                }

                else -> Unit
            }

            processingAccountId = null
        }
    }
}