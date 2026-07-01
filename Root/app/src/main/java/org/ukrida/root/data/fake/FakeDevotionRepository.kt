package org.ukrida.root.data.fake

import kotlinx.coroutines.delay
import org.ukrida.root.data.dummy.DummyDevotionData
import org.ukrida.root.data.model.Devotion
import org.ukrida.root.data.model.DevotionDate

class FakeDevotionRepository {

    suspend fun getDevotion(
        groupId: Int,
        date: String
    ): Result<Devotion> {

        delay(500) // simulasi network

        val devotion = DummyDevotionData.devotions.find {
            it.groupId == groupId &&
                    it.date == date
        }

        return if (devotion != null) {
            Result.success(devotion)
        } else {
            Result.failure(
                Exception("Devotion not found")
            )
        }
    }

    suspend fun getDevotionDates(
        groupId: Int
    ): Result<List<DevotionDate>> {

        delay(500)

        val dates = DummyDevotionData.devotions
            .filter { it.groupId == groupId }
            .map {
                DevotionDate(
                    date = it.date,
                    title = it.title
                )
            }

        return Result.success(dates)
    }
}