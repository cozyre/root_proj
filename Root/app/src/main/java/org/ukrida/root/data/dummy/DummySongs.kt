package org.ukrida.root.data.dummy
import org.ukrida.root.data.model.*

object DummySongData {

    val songSummaries = listOf(
        SongSummary(
            id = 1,
            title = "Ku Mau Cinta Yesus Selamanya",
            author = "Niko Njotorahardjo",
            sortOrder = 1
        ),
        SongSummary(
            id = 2,
            title = "Bapa Engkau Sungguh Baik",
            author = "Jeffry S. Tjandra",
            sortOrder = 2
        ),
        SongSummary(
            id = 3,
            title = "Sungguh Indah",
            author = "True Worshippers",
            sortOrder = 3
        ),
        SongSummary(
            id = 4,
            title = "Kunyanyi Haleluya",
            author = "Franky Sihombing",
            sortOrder = 4
        ),
        SongSummary(
            id = 5,
            title = "Bagi Tuhan Tak Ada Yang Mustahil",
            author = null,
            sortOrder = 5
        )
    )

    val songs = listOf(
        Song(
            id = 1,
            title = "Ku Mau Cinta Yesus Selamanya",
            author = "Niko Njotorahardjo",
            lyrics = """
                Ku mau cinta Yesus selamanya
                Ku mau cinta Yesus selamanya
                
                Meskipun badai silih berganti
                Dalam hidupku
                Ku tetap cinta Yesus selamanya
            """.trimIndent()
        ),
        Song(
            id = 2,
            title = "Bapa Engkau Sungguh Baik",
            author = "Jeffry S. Tjandra",
            lyrics = """
                Bapa Engkau sungguh baik
                Kasih-Mu melimpah di hidupku
                
                Bapa ku berterima kasih
                Berkat-Mu hari ini yang Kau sediakan bagiku
            """.trimIndent()
        ),
        Song(
            id = 3,
            title = "Sungguh Indah",
            author = "True Worshippers",
            lyrics = """
                Sungguh indah Kau Tuhan
                Engkau Allah yang setia
                
                Besar kasih-Mu
                Nyata di dalam hidupku
            """.trimIndent()
        ),
        Song(
            id = 4,
            title = "Kunyanyi Haleluya",
            author = "Franky Sihombing",
            lyrics = """
                Kunyanyi haleluya
                Kunyanyi haleluya
                
                Ku memuji nama-Mu
                Sepanjang hidupku
            """.trimIndent()
        ),
        Song(
            id = 5,
            title = "Bagi Tuhan Tak Ada Yang Mustahil",
            author = null,
            lyrics = """
                Bagi Tuhan tak ada yang mustahil
                Bagi Tuhan tak ada yang tak mungkin
                
                Mujizat-Nya disediakan bagiku
                Ku diangkat dan dipulihkan-Nya
            """.trimIndent()
        )
    )

    val songBrowseItems = listOf(
        SongBrowseItem(
            id = 1,
            title = "Ku Mau Cinta Yesus Selamanya",
            author = "Niko Njotorahardjo"
        ),
        SongBrowseItem(
            id = 2,
            title = "Bapa Engkau Sungguh Baik",
            author = "Jeffry S. Tjandra"
        ),
        SongBrowseItem(
            id = 3,
            title = "Sungguh Indah",
            author = "True Worshippers"
        ),
        SongBrowseItem(
            id = 4,
            title = "Kunyanyi Haleluya",
            author = "Franky Sihombing"
        ),
        SongBrowseItem(
            id = 5,
            title = "Bagi Tuhan Tak Ada Yang Mustahil",
            author = null
        ),
        SongBrowseItem(
            id = 6,
            title = "Hosanna",
            author = "Hillsong Worship"
        ),
        SongBrowseItem(
            id = 7,
            title = "What A Beautiful Name",
            author = "Hillsong Worship"
        )
    )

    fun getSongById(id: Int): Song? =
        songs.find { it.id == id }

    fun getSongSummaryById(id: Int): SongSummary? =
        songSummaries.find { it.id == id }
}