package org.ukrida.root.data.dummy
import org.ukrida.root.data.model.*
object DummyJournalData {

    val journals = listOf(
        Journal(
            id = 1,
            userId = 1,
            groupId = 1,
            title = "Hari Pertama Retreat",
            content = "Saya belajar bahwa Tuhan selalu menyertai setiap langkah kehidupan saya. Melalui sesi renungan pagi, saya semakin memahami pentingnya berserah kepada-Nya.",
            journalDate = "2025-07-10",
            createdAt = "2025-07-10T20:15:00Z",
            updatedAt = "2025-07-10T20:15:00Z"
        ),
        Journal(
            id = 2,
            userId = 1,
            groupId = 1,
            title = "Belajar Melayani",
            content = "Kegiatan pelayanan hari ini mengajarkan saya untuk lebih peduli terhadap sesama dan melayani dengan kasih yang tulus.",
            journalDate = "2025-07-11",
            createdAt = "2025-07-11T21:00:00Z",
            updatedAt = "2025-07-11T21:00:00Z"
        ),
        Journal(
            id = 3,
            userId = 2,
            groupId = 1,
            title = "Pertumbuhan Iman",
            content = "Diskusi kelompok membuka wawasan baru tentang bagaimana membangun hubungan yang lebih dekat dengan Tuhan.",
            journalDate = "2025-07-11",
            createdAt = "2025-07-11T22:10:00Z",
            updatedAt = "2025-07-11T22:10:00Z"
        ),
        Journal(
            id = 4,
            userId = 3,
            groupId = 2,
            title = "Melayani dengan Sukacita",
            content = "Saya merasakan sukacita ketika dapat membantu orang lain tanpa mengharapkan balasan apa pun.",
            journalDate = "2025-07-20",
            createdAt = "2025-07-20T19:30:00Z",
            updatedAt = "2025-07-20T19:30:00Z"
        ),
        Journal(
            id = 5,
            userId = 1,
            groupId = 2,
            title = "Refleksi Malam",
            content = "Hari ini saya belajar bahwa setiap tantangan yang Tuhan izinkan memiliki tujuan untuk membentuk karakter saya.",
            journalDate = "2025-07-21",
            createdAt = "2025-07-21T21:45:00Z",
            updatedAt = "2025-07-21T22:00:00Z"
        )
    )

    val createJournalRequests = listOf(
        CreateJournalRequest(
            groupId = 1,
            title = "Hari Pertama Retreat",
            content = "Saya belajar untuk lebih mengandalkan Tuhan dalam setiap keadaan.",
            journalDate = "2025-07-10"
        ),
        CreateJournalRequest(
            groupId = 1,
            title = "Belajar Melayani",
            content = "Pelayanan hari ini mengajarkan saya arti kerendahan hati.",
            journalDate = "2025-07-11"
        ),
        CreateJournalRequest(
            groupId = 2,
            title = "Refleksi Malam",
            content = "Tuhan mengingatkan saya untuk selalu bersyukur.",
            journalDate = "2025-07-21"
        )
    )

    val updateJournalRequests = listOf(
        UpdateJournalRequest(
            id = 1,
            title = "Hari Pertama Retreat (Revisi)",
            content = "Saya semakin memahami pentingnya doa dan penyerahan diri kepada Tuhan.",
            journalDate = "2025-07-10"
        ),
        UpdateJournalRequest(
            id = 2,
            title = "Belajar Melayani dengan Kasih",
            content = "Saya belajar bahwa pelayanan yang sejati dilakukan dengan hati yang tulus.",
            journalDate = "2025-07-11"
        )
    )

    val deleteJournalRequests = listOf(
        DeleteJournalRequest(id = 4),
        DeleteJournalRequest(id = 5)
    )
}