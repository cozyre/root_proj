package org.ukrida.root.data.fake

import kotlinx.coroutines.delay
import org.ukrida.root.data.dummy.DummyItineraryData
import org.ukrida.root.data.model.Itinerary
import org.ukrida.root.data.model.ItineraryDate

class FakeItineraryRepository {

    suspend fun getItinerary(
        groupId: Int,
        date: String
    ): Result<Itinerary> {

        delay(500) // simulasi network

        val itinerary = DummyItineraryData.itineraries.find {
            it.date == date
        }

        return if (itinerary != null) {
            Result.success(itinerary)
        } else {
            Result.failure(
                Exception("Itinerary not found")
            )
        }
    }

    suspend fun getItineraryDates(
        groupId: Int
    ): Result<List<ItineraryDate>> {

        delay(500)

        return Result.success(
            DummyItineraryData.itineraryDates
        )
    }
}