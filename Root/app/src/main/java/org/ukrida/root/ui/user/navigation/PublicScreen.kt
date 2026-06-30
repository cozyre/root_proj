package org.ukrida.root.ui.user.navigation

sealed class PublicScreen(val route: String) {
    object Home : PublicScreen("home")
    object PromisedLand : PublicScreen("promised_land")
    object History : PublicScreen("history")
    object Group : PublicScreen("group")
    object Profile : PublicScreen("profile")

    object HistoryDetail : PublicScreen("history_detail/{groupId}") {
        fun createRoute(groupId: Int) =
            "history_detail/$groupId"
    }
    object Order {
        const val route = "order/{groupId}"
        fun createRoute(groupId: Int) = "order/$groupId"
    }
    object Dashboard {
        const val route = "dashboard/{groupId}"
        fun createRoute(groupId: Int) =
            "dashboard/$groupId"
    }
    object Hymn : PublicScreen("hymn/{groupId}") {
        fun createRoute(groupId: Int) = "hymn/$groupId"
    }
    object HymnDetail : PublicScreen("hymn_detail/{groupId}/{songId}") {
        fun createRoute(groupId: Int,songId: Int) = "hymn_detail/$groupId/$songId"
    }
    object Gallery : PublicScreen("gallery/{groupId}") {
        fun createRoute(groupId: Int) = "gallery/$groupId"
    }
    object Journal : PublicScreen("journal/{groupId}") {
        fun createRoute(groupId: Int) =
            "journal/$groupId"
    }
    object JournalEditor : PublicScreen("journal_editor/{groupId}/{journalId}") {
        fun createRoute(
            groupId: Int,
            journalId: Int = -1
        ) = "journal_editor/$groupId/$journalId"
    }
    object Itinerary : PublicScreen("itinerary/{groupId}") {
        fun createRoute(groupId: Int) =
            "itinerary/$groupId"
    }
    object DailyBread : PublicScreen("daily_bread/{groupId}") {
        fun createRoute(groupId: Int) =
            "daily_bread/$groupId"
    }
    object DailyBreadDetail :
        PublicScreen("daily_bread_detail/{groupId}/{date}") {
        fun createRoute(
            groupId: Int,
            date: String
        ) = "daily_bread_detail/$groupId/$date"
    }
    object Members : PublicScreen("members/{groupId}") {
        fun createRoute(groupId: Int) =
            "members/$groupId"
    }
    object MemberDetail :
        PublicScreen("member_detail/{groupId}/{userId}") {
        fun createRoute(
            groupId: Int,
            userId: Int
        ) = "member_detail/$groupId/$userId"
    }
}