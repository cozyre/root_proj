package org.ukrida.root.data.repository

import org.ukrida.root.data.model.AccountStatus
import org.ukrida.root.data.model.GroupDetail
import org.ukrida.root.data.model.OrderRequest
import org.ukrida.root.data.model.OrderResult
import org.ukrida.root.data.remote.ApiService

class AccountRepository(private val api: ApiService) {

    suspend fun orderTour(groupId: Int): Result<OrderResult> {
        return try {
            val res = api.orderTour(body = OrderRequest(groupId))
            if (res.isSuccessful && res.body()?.success == true) {
                Result.success(res.body()!!.data!!)
            } else {
                Result.failure(Exception(res.body()?.message ?: "Order failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getOrderStatus(groupId: Int): Result<AccountStatus> {
        return try {
            val res = api.getOrderStatus(groupId = groupId)
            if (res.isSuccessful && res.body()?.success == true) {
                Result.success(res.body()!!.data!!)
            } else {
                Result.failure(Exception(res.body()?.message ?: "Failed to fetch status"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getGroupDetail(groupId: Int): Result<GroupDetail> {
        return try {
            val res = api.getGroupDetail(groupId = groupId)
            if (res.isSuccessful && res.body()?.success == true) {
                Result.success(res.body()!!.data!!)
            } else {
                Result.failure(Exception(res.body()?.message ?: "Access denied or group not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}