package mdy.klt.myatmyat.navigation.destination


object Routes {
    const val APP_ROUTE = "app"
}

object ArgConstants

sealed class Destinations(val route: String) {
    object Branding : Destinations(route = "branding_screen")

    object DailyCalculator : Destinations(route = "daily_calculator")
    object HistoryList : Destinations(route = "history_list")
    object HistoryDetail : Destinations(route = "history_detail")
}


