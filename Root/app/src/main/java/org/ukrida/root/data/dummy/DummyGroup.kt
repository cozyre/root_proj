package org.ukrida.root.data.dummy
import org.ukrida.root.data.model.*

object DummyGroupData {

    val groups = listOf(
        Group(
            id = 1,
            name = "ROOT Bandung 2025",
            description = "Perjalanan rohani bersama mahasiswa UKRIDA ke Bandung.",
            location = "Bandung",
            dresscode = "Kaos ROOT & Celana Panjang",
            status = "upcoming",
            startDate = "2025-07-10",
            endDate = "2025-07-13",
            meetupTime = "06:00",
            meetupAddress = "Kampus UKRIDA Jakarta"
        ),
        Group(
            id = 2,
            name = "ROOT Yogyakarta 2025",
            description = "Tour rohani dan pelayanan di Yogyakarta.",
            location = "Yogyakarta",
            dresscode = "Kemeja Putih & Celana Hitam",
            status = "active",
            startDate = "2025-07-20",
            endDate = "2025-07-25",
            meetupTime = "05:30",
            meetupAddress = "Bandara Soekarno Hatta"
        ),
        Group(
            id = 3,
            name = "ROOT Bali 2025",
            description = "Retreat dan pembinaan rohani di Bali.",
            location = "Bali",
            dresscode = "Casual Sopan",
            status = "completed",
            startDate = "2025-06-01",
            endDate = "2025-06-05",
            meetupTime = "04:30",
            meetupAddress = "Terminal 3 Bandara Soekarno Hatta"
        ),
        Group(
            id = 4,
            name = "ROOT Semarang 2025",
            description = "Pelayanan sosial dan penginjilan.",
            location = "Semarang",
            dresscode = "Kaos ROOT",
            status = "upcoming",
            startDate = "2025-08-15",
            endDate = "2025-08-18",
            meetupTime = "06:00",
            meetupAddress = "Kampus UKRIDA Jakarta"
        ),
        Group(
            id = 5,
            name = "ROOT Surabaya 2025",
            description = "Kegiatan pembinaan dan pelayanan mahasiswa.",
            location = "Surabaya",
            dresscode = "Polo Shirt ROOT",
            status = "completed",
            startDate = "2025-05-10",
            endDate = "2025-05-14",
            meetupTime = "05:00",
            meetupAddress = "Bandara Halim Perdanakusuma"
        )
    )

    val groupResponse = GroupResponse(
        success = true,
        data = groups,
        message = "Data grup berhasil diambil"
    )

    val groupSingleResponse = GroupSingleResponse(
        success = true,
        data = groups.first(),
        message = "Detail grup berhasil diambil"
    )

    val groupImages = listOf(

        // Group 1 - Bandung
        GroupImage(
            id = 1,
            groupId = 1,
            imageUrl = "https://picsum.photos/800/600?random=1",
            caption = "Keberangkatan peserta dari UKRIDA",
            imageType = "gallery",
            sortOrder = 1,
            createdAt = "2025-07-10T06:00:00Z"
        ),

        GroupImage(
            id = 2,
            groupId = 1,
            imageUrl = "https://picsum.photos/800/600?random=2",
            caption = "Sesi doa sebelum perjalanan",
            imageType = "gallery",
            sortOrder = 2,
            createdAt = "2025-07-10T07:30:00Z"
        ),

        GroupImage(
            id = 3,
            groupId = 1,
            imageUrl = "https://picsum.photos/800/600?random=3",
            caption = "Ibadah malam pertama",
            imageType = "gallery",
            sortOrder = 3,
            createdAt = "2025-07-10T19:00:00Z"
        ),

        // Group 2 - Yogyakarta
        GroupImage(
            id = 4,
            groupId = 2,
            imageUrl = "https://picsum.photos/800/600?random=4",
            caption = "Pelayanan di sekolah",
            imageType = "gallery",
            sortOrder = 1,
            createdAt = "2025-07-20T09:00:00Z"
        ),

        GroupImage(
            id = 5,
            groupId = 2,
            imageUrl = "https://picsum.photos/800/600?random=5",
            caption = "Foto bersama peserta",
            imageType = "gallery",
            sortOrder = 2,
            createdAt = "2025-07-20T15:00:00Z"
        ),

        // Group 3 - Bali
        GroupImage(
            id = 6,
            groupId = 3,
            imageUrl = "https://picsum.photos/800/600?random=6",
            caption = "Retreat pagi hari",
            imageType = "gallery",
            sortOrder = 1,
            createdAt = "2025-06-01T06:30:00Z"
        ),

        GroupImage(
            id = 7,
            groupId = 3,
            imageUrl = "https://picsum.photos/800/600?random=7",
            caption = "Sharing kelompok kecil",
            imageType = "gallery",
            sortOrder = 2,
            createdAt = "2025-06-02T13:00:00Z"
        )
    )
}