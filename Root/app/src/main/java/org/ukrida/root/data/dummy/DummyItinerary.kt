package org.ukrida.root.data.dummy
import org.ukrida.root.data.model.*

object DummyItineraryData {

    val itineraries = listOf(
        Itinerary(
            id = 1,
            date = "2025-07-10",
            desc = "Hari Keberangkatan",
            items = listOf(
                ItineraryItem(
                    id = 1,
                    startTime = "06:00",
                    endTime = "06:30",
                    type = "activity",
                    description = "Registrasi peserta"
                ),
                ItineraryItem(
                    id = 2,
                    startTime = "06:30",
                    endTime = "07:00",
                    type = "devotion",
                    description = "Renungan pagi dan doa keberangkatan"
                ),
                ItineraryItem(
                    id = 3,
                    startTime = "07:00",
                    endTime = "12:00",
                    type = "activity",
                    description = "Perjalanan menuju lokasi retreat"
                ),
                ItineraryItem(
                    id = 4,
                    startTime = "19:00",
                    endTime = "20:00",
                    type = "song",
                    description = "Sesi pujian dan penyembahan"
                )
            )
        ),

        Itinerary(
            id = 2,
            date = "2025-07-11",
            desc = "Hari Pembinaan Rohani",
            items = listOf(
                ItineraryItem(
                    id = 5,
                    startTime = "06:00",
                    endTime = "07:00",
                    type = "devotion",
                    description = "Saat teduh bersama"
                ),
                ItineraryItem(
                    id = 6,
                    startTime = "08:00",
                    endTime = "10:00",
                    type = "activity",
                    description = "Seminar kepemimpinan Kristen"
                ),
                ItineraryItem(
                    id = 7,
                    startTime = "13:00",
                    endTime = "15:00",
                    type = "activity",
                    description = "Diskusi kelompok"
                ),
                ItineraryItem(
                    id = 8,
                    startTime = "19:00",
                    endTime = "21:00",
                    type = "song",
                    description = "Malam pujian dan kesaksian"
                )
            )
        ),

        Itinerary(
            id = 3,
            date = "2025-07-12",
            desc = "Hari Pelayanan",
            items = listOf(
                ItineraryItem(
                    id = 9,
                    startTime = "07:00",
                    endTime = "08:00",
                    type = "devotion",
                    description = "Doa pagi"
                ),
                ItineraryItem(
                    id = 10,
                    startTime = "09:00",
                    endTime = "12:00",
                    type = "activity",
                    description = "Pelayanan sosial di masyarakat"
                ),
                ItineraryItem(
                    id = 11,
                    startTime = "14:00",
                    endTime = "17:00",
                    type = "activity",
                    description = "Kunjungan dan doa bagi warga"
                )
            )
        ),

        Itinerary(
            id = 4,
            date = "2025-07-13",
            desc = "Hari Kepulangan",
            items = listOf(
                ItineraryItem(
                    id = 12,
                    startTime = "07:00",
                    endTime = "08:00",
                    type = "devotion",
                    description = "Renungan penutup"
                ),
                ItineraryItem(
                    id = 13,
                    startTime = "08:00",
                    endTime = "09:00",
                    type = "activity",
                    description = "Foto bersama dan penutupan"
                ),
                ItineraryItem(
                    id = 14,
                    startTime = "09:00",
                    endTime = "15:00",
                    type = "activity",
                    description = "Perjalanan kembali ke Jakarta"
                )
            )
        )
    )

    val itineraryDates = listOf(
        ItineraryDate(
            date = "2025-07-10",
            desc = "Hari Keberangkatan"
        ),
        ItineraryDate(
            date = "2025-07-11",
            desc = "Hari Pembinaan Rohani"
        ),
        ItineraryDate(
            date = "2025-07-12",
            desc = "Hari Pelayanan"
        ),
        ItineraryDate(
            date = "2025-07-13",
            desc = "Hari Kepulangan"
        )
    )
}