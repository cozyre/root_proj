package org.ukrida.root.ui.admin.screens.approval.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ukrida.root.data.fake.FakeAccountRepository
import org.ukrida.root.data.fake.FakeMemberRepository
import org.ukrida.root.ui.admin.screens.finished.viewmodel.ApprovalUiModel
import org.ukrida.root.utils.Resource

class ApprovalViewModel : ViewModel() {

    private val accountRepository = FakeAccountRepository()
    private val memberRepository = FakeMemberRepository()

    var approvals by mutableStateOf<List<ApprovalUiModel>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var showAllPending by mutableStateOf(false)
        private set

    var showAllProcessed by mutableStateOf(false)
        private set

    init {
        loadApprovals()
    }

    private fun loadApprovals() {
        viewModelScope.launch {

            isLoading = true

            try {

                val accountStatuses =
                    accountRepository.getAllStatuses().getOrNull()
                        ?: emptyList()

                val membersResult =
                    memberRepository.listMembers(groupId = 1)

                val members = when (membersResult) {
                    is Resource.Success -> membersResult.data ?: emptyList()
                    else -> emptyList()
                }

                approvals = accountStatuses.map { status ->

                    val member = members.find {
                        it.id == status.accountId
                    }

                    ApprovalUiModel(
                        accountId = status.accountId,
                        userName = member?.fullName ?: "Unknown User",
                        groupName = status.groupName,
                        status = status.statusJoin
                    )
                }

            } finally {
                isLoading = false
            }
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

        approvals = approvals.map {

            if (it.accountId == accountId) {
                it.copy(
                    status = "approved"
                )
            } else {
                it
            }
        }
    }

    fun reject(accountId: Int) {

        approvals = approvals.map {

            if (it.accountId == accountId) {
                it.copy(
                    status = "rejected"
                )
            } else {
                it
            }
        }
    }
}