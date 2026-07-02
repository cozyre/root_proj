package org.ukrida.root.ui.admin.screens.approval.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ukrida.root.data.remote.RetrofitClient
import org.ukrida.root.data.repository.AdminRepository
import org.ukrida.root.ui.admin.screens.finished.viewmodel.ApprovalUiModel
import org.ukrida.root.utils.Resource

class ApprovalViewModel : ViewModel() {

    private val repository = AdminRepository(RetrofitClient.instance)

    var approvals by mutableStateOf<List<ApprovalUiModel>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var processingAccountId by mutableStateOf<Int?>(null)
        private set

    var showAllPending by mutableStateOf(false)
        private set

    var showAllProcessed by mutableStateOf(false)
        private set

    init {
        loadApprovals()
    }

    fun loadApprovals() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val result = repository.getApprovalAccounts()

            result
                .onSuccess { accounts ->
                    approvals = accounts.map { account ->
                        ApprovalUiModel(
                            accountId = account.id,
                            userName = account.fullName,
                            groupName = account.groupName,
                            status = account.statusJoin
                        )
                    }
                }
                .onFailure { error ->
                    errorMessage = error.message ?: "Failed to load approvals"
                }

            isLoading = false
        }
    }

    val pendingRequests: List<ApprovalUiModel>
        get() = approvals.filter {
            it.status.equals("pending", ignoreCase = true)
        }

    val processedRequests: List<ApprovalUiModel>
        get() = approvals.filter {
            it.status.equals("approved", ignoreCase = true) ||
                    it.status.equals("rejected", ignoreCase = true)
        }

    fun togglePending() {
        showAllPending = !showAllPending
    }

    fun toggleProcessed() {
        showAllProcessed = !showAllProcessed
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
            errorMessage = null

            val result = if (isApprove) {
                repository.approveOrder(accountId)
            } else {
                repository.rejectOrder(accountId)
            }

            when (result) {
                is Resource.Success -> {
                    val updatedOrder = result.data

                    approvals = approvals.map { approval ->
                        if (approval.accountId == accountId) {
                            approval.copy(
                                userName = updatedOrder.userName,
                                groupName = updatedOrder.groupName,
                                status = updatedOrder.statusJoin
                            )
                        } else {
                            approval
                        }
                    }
                }

                is Resource.Error -> {
                    errorMessage = result.message
                }

                else -> Unit
            }

            processingAccountId = null
        }
    }
}