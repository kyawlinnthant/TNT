package mdy.klt.myatmyat.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import mdy.klt.myatmyat.navigation.destination.Destinations

@Composable
fun RowScope.AddBottomBarItem(
    screen: BottomBarDestination,
    currentRoute: NavDestination?,
    navController: NavHostController,

    ) {
    var selectedRoute by remember {
        mutableStateOf(screen.route)
    }
    NavigationBarItem(
        label = {
            Text(
                text = stringResource(id = screen.title),
                maxLines = 1
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.iconId),
                contentDescription = "BottomBar Icon",
            )
        },
        selected = currentRoute?.hierarchy?.any {
            it.route == screen.route

        } == true,
        onClick = {
            selectedRoute = screen.route
            navController.navigate(screen.route) {
                popUpTo(
                    Destinations.HistoryList.route
                )
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}