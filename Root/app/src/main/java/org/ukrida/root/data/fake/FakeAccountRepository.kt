package org.ukrida.root.data.fake

import kotlinx.coroutines.delay
import org.ukrida.root.data.dummy.DummyAccountData
import org.ukrida.root.data.model.AccountStatus
import org.ukrida.root.data.model.GroupDetail
import org.ukrida.root.data.model.OrderResult

class FakeAccountRepository {

    suspend fun orderTour(groupId: Int): Result<OrderResult> {
        delay(500)

        return Result.success(
            DummyAccountData.orderResult.copy(
                accountId = groupId
            )
        )
    }

    suspend fun getOrderStatus(groupId: Int): Result<AccountStatus> {
        delay(500)

        return Result.success(
            DummyAccountData.accountStatus.copy(
                accountId = groupId
            )
        )
    }

    suspend fun getGroupDetail(groupId: Int): Result<GroupDetail> {
        delay(500)

        val group = DummyAccountData.groupList.find {
            it.id == groupId
        }

        return if (group != null) {
            Result.success(group)
        } else {
            Result.failure(Exception("Group not found"))
        }
    }
}