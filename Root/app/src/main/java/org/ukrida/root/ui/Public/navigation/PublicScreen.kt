package org.ukrida.root.ui.Public.navigation

sealed class PublicScreen(val route: String) {
    object Home : PublicScreen("home")
    object PromisedLand : PublicScreen("promised_land")
    object History : PublicScreen("history")
    object Group : PublicScreen("group")
    object Profile : PublicScreen("profile")
}