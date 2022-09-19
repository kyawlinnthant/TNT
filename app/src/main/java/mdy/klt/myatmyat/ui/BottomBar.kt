package mdy.klt.myatmyat.ui

import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import mdy.klt.myatmyat.navigation.destination.Destinations

@Composable
fun BottomBar(navController: NavHostController) {

    // bottom bar tab list
    val screens = listOf(
        BottomBarDestination.Calculator,
        BottomBarDestination.HistoryList
    )
    val screenRoute = listOf(
        BottomBarDestination.Calculator.route,
        BottomBarDestination.HistoryList.route
    )
    val shouldShowBottomBar = screenRoute.contains(
        navController.currentDestination?.route
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    if (shouldShowBottomBar) {
        NavigationBar {
            screens.forEach {
                AddBottomBarItem(
                    screen = it,
                    currentRoute = currentRoute,
                    navController = navController,
                )
            }
        }
    }
}