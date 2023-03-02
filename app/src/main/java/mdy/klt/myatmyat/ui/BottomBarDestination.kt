package mdy.klt.myatmyat.ui

import mdy.klt.myatmyat.R
import mdy.klt.myatmyat.navigation.destination.Destinations

sealed class BottomBarDestination(
    val route: String,
    val title: Int,
    val iconId: Int
) {
    object Calculator : BottomBarDestination(
        route = Destinations.DailyCalculator.route,
        title = R.string.nav_calculator,
        iconId = R.drawable.ic_calculator
    )

    object HistoryList : BottomBarDestination(
        route = Destinations.HistoryList.route,
        title = R.string.nav_history_list,
        iconId = R.drawable.ic_list
    )

}