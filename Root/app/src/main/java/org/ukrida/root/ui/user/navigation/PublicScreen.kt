package org.ukrida.root.ui.user.navigation

/**
 * Sealed class for all public navigation routes.
 * Root-level routes: home, promised_land, history, group, profile
 * Group-scoped routes: group/{groupId}/dashboard, group/{groupId}/hymn, etc.
 */
sealed class PublicScreen(val route: String) {
    // Root-level destinations
    object Home : PublicScreen("home")
    object PromisedLand : PublicScreen("promised_land")
    object History : PublicScreen("history")
    object Group : PublicScreen("group")
    object Profile : PublicScreen("profile")

    object HistoryDetail : PublicScreen("history_detail/{groupId}") {
        fun createRoute(groupId: Int) = "history_detail/$groupId"
    }

    object Order : PublicScreen("order/{groupId}") {
        fun createRoute(groupId: Int) = "order/$groupId"
    }

    // Group-scoped routes (nested under group/{groupId}/)
    object Dashboard : PublicScreen("group/{groupId}/dashboard") {
        fun createRoute(groupId: Int) = "group/$groupId/dashboard"
    }

    object Itinerary : PublicScreen("group/{groupId}/itinerary") {
        fun createRoute(groupId: Int) = "group/$groupId/itinerary"
    }

    object Hymn : PublicScreen("group/{groupId}/hymn") {
        fun createRoute(groupId: Int) = "group/$groupId/hymn"
    }

    object HymnDetail : PublicScreen("group/{groupId}/hymn_detail/{songId}") {
        fun createRoute(groupId: Int, songId: Int) = "group/$groupId/hymn_detail/$songId"
    }

    object Gallery : PublicScreen("group/{groupId}/gallery") {
        fun createRoute(groupId: Int) = "group/$groupId/gallery"
    }

    object Journal : PublicScreen("group/{groupId}/journal") {
        fun createRoute(groupId: Int) = "group/$groupId/journal"
    }

    object JournalEditor : PublicScreen("group/{groupId}/journal_editor/{journalId}") {
        fun createRoute(groupId: Int, journalId: Int = -1) = "group/$groupId/journal_editor/$journalId"
    }

    object DailyBread : PublicScreen("group/{groupId}/daily_bread") {
        fun createRoute(groupId: Int) = "group/$groupId/daily_bread"
    }

    object DailyBreadDetail : PublicScreen("group/{groupId}/daily_bread_detail/{date}") {
        fun createRoute(groupId: Int, date: String) = "group/$groupId/daily_bread_detail/$date"
    }

    object Members : PublicScreen("group/{groupId}/members") {
        fun createRoute(groupId: Int) = "group/$groupId/members"
    }

    object MemberDetail : PublicScreen("group/{groupId}/member_detail/{userId}") {
        fun createRoute(groupId: Int, userId: Int) = "group/$groupId/member_detail/$userId"
    }
}