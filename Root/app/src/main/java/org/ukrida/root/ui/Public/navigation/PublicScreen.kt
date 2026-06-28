package org.ukrida.root.ui.Public.navigation

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
}