package org.ukrida.root.ui.admin.screens.dashboard.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ukrida.root.data.fake.FakeAccountRepository
import org.ukrida.root.data.fake.FakeGroupRepository
import org.ukrida.root.data.fake.FakeMemberRepository
import org.ukrida.root.data.model.Group
import org.ukrida.root.utils.Resource

data class ApprovalItem(
    val accountId: Int,
    val userName: String,
    val groupName: String
)

class DashboardViewModel : ViewModel() {

    private val accountRepository = FakeAccountRepository()
    private val memberRepository = FakeMemberRepository()
    private val groupRepository = FakeGroupRepository()

    var approvals by mutableStateOf<List<ApprovalItem>>(emptyList())
        private set

    var latestTour by mutableStateOf<Group?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        loadDashboard()
    }

    private fun loadDashboard() {

        viewModelScope.launch {

            isLoading = true

            loadApprovals()
            loadLatestTour()

            isLoading = false
        }
    }

    private suspend fun loadApprovals() {

        val statusResult =
            accountRepository.getAllStatuses()

        if (statusResult.isSuccess) {

            val pendingAccounts =
                statusResult.getOrNull()
                    ?.filter {
                        it.statusJoin == "pending"
                    }
                    ?: emptyList()

            approvals = pendingAccounts.mapNotNull { status ->

                when (
                    val member =
                        memberRepository.getMemberDetail(
                            userId = status.accountId,
                            groupId = 0
                        )
                ) {

                    is Resource.Success -> {

                        ApprovalItem(
                            accountId = status.accountId,
                            userName = "${member.data.firstName} ${member.data.lastName}",
                            groupName = status.groupName
                        )
                    }

                    else -> null
                }
            }
                .take(3)
        }
    }

    private suspend fun loadLatestTour() {

        val result =
            groupRepository.getAllTours()

        if (result.isSuccess) {

            latestTour =
                result.getOrNull()
                    ?.maxByOrNull { it.id }
        }
    }
}