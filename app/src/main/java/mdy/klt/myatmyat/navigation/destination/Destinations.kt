package mdy.klt.myatmyat.navigation.destination

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


object Routes {
    const val APP_ROUTE = "app"
}

object ArgConstants {
    const val ID = "db_id"
}

sealed class Destinations(val route: String) {
    object Branding : Destinations(route = "branding_screen")

    object DailyCalculator : Destinations(route = "daily_calculator")
    object HistoryList : Destinations(route = "history_list")
    object HistoryDetail : Destinations(route = "history_detail/{${ArgConstants.ID}}") {
        fun passId(dataId: Long): String {
            return this.route.replace(
                oldValue = "{${ArgConstants.ID}}",
                newValue = dataId.toString()
            )
        }
    }
}


