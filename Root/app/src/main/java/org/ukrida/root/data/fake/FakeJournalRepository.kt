package org.ukrida.root.data.fake

import kotlinx.coroutines.delay
import org.ukrida.root.data.dummy.DummyJournalData
import org.ukrida.root.data.model.Journal
import org.ukrida.root.utils.Resource

class FakeJournalRepository {

    suspend fun createJournal(
        groupId: Int,
        title: String,
        content: String,
        journalDate: String
    ): Resource<Journal> {

        delay(500)

        val journal = Journal(
            id = (DummyJournalData.journals.maxOfOrNull { it.id } ?: 0) + 1,
            userId = 1,
            groupId = groupId,
            title = title,
            content = content,
            journalDate = journalDate,
            createdAt = "2026-06-27T12:00:00Z",
            updatedAt = "2026-06-27T12:00:00Z"
        )

        return Resource.Success(journal)
    }

    suspend fun listJournals(
        groupId: Int
    ): Resource<List<Journal>> {

        delay(500)

        return Resource.Success(
            DummyJournalData.journals.filter {
                it.groupId == groupId
            }
        )
    }

    suspend fun updateJournal(
        id: Int,
        title: String,
        content: String,
        journalDate: String
    ): Resource<Journal> {

        delay(500)

        val existingJournal = DummyJournalData.journals.find {
            it.id == id
        }

        return if (existingJournal != null) {

            Resource.Success(
                existingJournal.copy(
                    title = title,
                    content = content,
                    journalDate = journalDate,
                    updatedAt = "2026-06-27T12:00:00Z"
                )
            )

        } else {

            Resource.Error("Journal not found")

        }
    }

    suspend fun deleteJournal(
        id: Int
    ): Resource<Unit> {

        delay(500)

        val exists = DummyJournalData.journals.any {
            it.id == id
        }

        return if (exists) {
            Resource.Success(Unit)
        } else {
            Resource.Error("Journal not found")
        }
    }
}