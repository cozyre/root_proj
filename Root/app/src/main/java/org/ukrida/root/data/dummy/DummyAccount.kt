package org.ukrida.root.data.dummy

import org.ukrida.root.data.model.*

object DummyAccountData {
    val orderResult = OrderResult(
        accountId = 1,
        statusJoin = "pending",
        isPaid = false
    )
    val accountStatuses = listOf(

        // Pending
        AccountStatus(
            accountId = 1,
            groupName = "ROOT Bandung 2025",
            statusJoin = "pending",
            joinDate = "2025-06-20",
            approvedDate = null,
            isPaid = false
        ),
        AccountStatus(
            accountId = 5,
            groupName = "ROOT Yogyakarta 2025",
            statusJoin = "pending",
            joinDate = "2025-06-21",
            approvedDate = null,
            isPaid = false
        ),
        AccountStatus(
            accountId = 10,
            groupName = "ROOT Jakarta 2025",
            statusJoin = "pending",
            joinDate = "2025-06-22",
            approvedDate = null,
            isPaid = false
        ),

        // Approved
        AccountStatus(
            accountId = 20,
            groupName = "ROOT Tour Rohani Batch 1",
            statusJoin = "approved",
            joinDate = "2026-06-20",
            approvedDate = "2026-06-22",
            isPaid = true
        ),
        AccountStatus(
            accountId = 21,
            groupName = "ROOT Surabaya 2026",
            statusJoin = "approved",
            joinDate = "2026-06-15",
            approvedDate = "2026-06-16",
            isPaid = true
        ),
        AccountStatus(
            accountId = 22,
            groupName = "ROOT Bali Retreat 2026",
            statusJoin = "approved",
            joinDate = "2026-06-10",
            approvedDate = "2026-06-11",
            isPaid = true
        ),

        // Rejected
        AccountStatus(
            accountId = 30,
            groupName = "ROOT Bandung 2026",
            statusJoin = "rejected",
            joinDate = "2026-06-12",
            approvedDate = null,
            isPaid = false
        ),
        AccountStatus(
            accountId = 31,
            groupName = "ROOT Yogyakarta 2026",
            statusJoin = "rejected",
            joinDate = "2026-06-14",
            approvedDate = null,
            isPaid = false
        )
    )


    val groupDetail = GroupDetail(
        id = 1,
        name = "ROOT Tour Rohani Batch 1",
        description = "Perjalanan rohani bersama peserta ROOT untuk memperdalam iman, membangun relasi, dan mengalami pertumbuhan spiritual.",
        startDate = "2026-07-15",
        endDate = "2026-07-18",
        location = "Bandung",
        dresscode = "Kaos ROOT dan celana panjang",
        meetupTime = "06:00",
        meetupAddress = "Kampus Ukrida Jakarta Barat",
        status = "open",
        mentor = GroupPerson(
            name = "Ps. Yohanes Wijaya",
            photo = "https://example.com/mentor.jpg"
        ),
        coordinator = GroupPerson(
            name = "Michael Timothy",
            photo = "https://example.com/coordinator.jpg"
        )
    )

    val groupList = listOf(
        GroupDetail(
            id = 1,
            name = "ROOT Tour Rohani Batch 1",
            description = "Kunjungan pelayanan ke Bandung.",
            startDate = "2026-07-15",
            endDate = "2026-07-18",
            location = "Bandung",
            dresscode = "Kaos ROOT",
            meetupTime = "06:00",
            meetupAddress = "Kampus Ukrida",
            status = "open",
            mentor = GroupPerson(
                name = "Ps. Yohanes Wijaya",
                photo = null
            ),
            coordinator = GroupPerson(
                name = "Michael Timothy",
                photo = null
            )
        ),
        GroupDetail(
            id = 2,
            name = "ROOT Tour Rohani Batch 2",
            description = "Retret rohani dan pelayanan sosial.",
            startDate = "2026-08-10",
            endDate = "2026-08-13",
            location = "Yogyakarta",
            dresscode = "Kemeja Putih",
            meetupTime = "07:00",
            meetupAddress = "Stasiun Gambir",
            status = "open",
            mentor = GroupPerson(
                name = "Ps. Daniel Santoso",
                photo = null
            ),
            coordinator = GroupPerson(
                name = "Maria Angelica",
                photo = null
            )
        )
    )
}
