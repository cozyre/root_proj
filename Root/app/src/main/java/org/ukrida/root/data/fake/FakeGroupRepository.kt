package org.ukrida.root.data.fake

import kotlinx.coroutines.delay
import org.ukrida.root.data.dummy.DummyGroupData
import org.ukrida.root.data.model.Group

class FakeGroupRepository {

    suspend fun getAllTours(): Result<List<Group>> {
        delay(500) // simulasi network

        return Result.success(
            DummyGroupData.groups
        )
    }

    suspend fun getTourById(id: Int): Result<Group> {
        delay(500)

        val group = DummyGroupData.groups.find {
            it.id == id
        }

        return if (group != null) {
            Result.success(group)
        } else {
            Result.failure(
                Exception("Tour not found")
            )
        }
    }

    suspend fun getPastTours(limit: Int = 10): Result<List<Group>> {
        delay(500)

        val completedTours = DummyGroupData.groups
            .filter {
                it.status.equals("completed", ignoreCase = true)
            }
            .take(limit)

        return Result.success(completedTours)
    }
}