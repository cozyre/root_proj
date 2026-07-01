package org.ukrida.root.data.dummy
import org.ukrida.root.data.model.*

object DummyDevotionData {

    val devotions = listOf(
        Devotion(
            id = 1,
            groupId = 1,
            title = "Mengandalkan Tuhan",
            content = "Percayalah kepada Tuhan dengan segenap hatimu dan jangan bersandar kepada pengertianmu sendiri.",
            date = "2025-07-01",
            createdAt = "2025-06-25T08:00:00Z"
        ),
        Devotion(
            id = 2,
            groupId = 1,
            title = "Kasih yang Tidak Berkesudahan",
            content = "Kasih Tuhan selalu baru setiap pagi dan tidak pernah berakhir bagi umat-Nya.",
            date = "2025-07-02",
            createdAt = "2025-06-26T08:00:00Z"
        ),
        Devotion(
            id = 3,
            groupId = 2,
            title = "Menjadi Terang Dunia",
            content = "Sebagai orang percaya, kita dipanggil untuk menjadi terang bagi sesama melalui tindakan dan perkataan.",
            date = "2025-07-03",
            createdAt = "2025-06-27T08:00:00Z"
        ),
        Devotion(
            id = 4,
            groupId = 2,
            title = "Kekuatan dalam Doa",
            content = "Doa adalah sarana untuk membangun hubungan yang intim dengan Tuhan dan memperoleh kekuatan dari-Nya.",
            date = "2025-07-04",
            createdAt = "2025-06-28T08:00:00Z"
        ),
        Devotion(
            id = 5,
            groupId = 3,
            title = "Hidup dalam Syukur",
            content = "Bersyukur dalam segala hal merupakan kehendak Tuhan bagi setiap orang percaya.",
            date = "2025-07-05",
            createdAt = "2025-06-29T08:00:00Z"
        )
    )

    val devotionDates = listOf(
        DevotionDate(
            date = "2025-07-01",
            title = "Mengandalkan Tuhan"
        ),
        DevotionDate(
            date = "2025-07-02",
            title = "Kasih yang Tidak Berkesudahan"
        ),
        DevotionDate(
            date = "2025-07-03",
            title = "Menjadi Terang Dunia"
        ),
        DevotionDate(
            date = "2025-07-04",
            title = "Kekuatan dalam Doa"
        ),
        DevotionDate(
            date = "2025-07-05",
            title = "Hidup dalam Syukur"
        )
    )
}