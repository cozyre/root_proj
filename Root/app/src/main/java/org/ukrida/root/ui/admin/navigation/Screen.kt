package org.ukrida.root.ui.admin.navigation

sealed class Screen(val route: String) {

    object Dashboard : Screen("dashboard")

    object Approval : Screen("approval")

    object HymnForHim : Screen("hymn_for_him")

    object OngoingTrip : Screen("ongoing_trip")

    object NewTrip : Screen("new_trip")

    object FinishedTrip : Screen("finished_trip")

    object FinishedDetail : Screen(
        "finished_detail/{tripId}"
    )

    object OngoingDetail : Screen("ongoing_detail/{tripId}")

    object EditItinerary : Screen("edit_itinerary/{tripId}")

    object EditSongs : Screen("edit_songs/{tripId}")

    object EditDailyBread : Screen("daily_bread/{tripId}")

    object Trip : Screen("trip")

    object CreateTrip : Screen("new_trip")

    object NewItinerary : Screen("new_itinerary")
    object AddSong : Screen("add_song")

    object EditSong : Screen("edit_song/{songId}") {
    }
}