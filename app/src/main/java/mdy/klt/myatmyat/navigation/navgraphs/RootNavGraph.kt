package mdy.klt.myatmyat.navigation.navgraphs

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import mdy.klt.myatmyat.navigation.destination.ArgConstants
import mdy.klt.myatmyat.navigation.destination.Destinations
import mdy.klt.myatmyat.navigation.destination.Routes
import mdy.klt.myatmyat.ui.*

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun RootNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Branding.route,
        route = Routes.APP_ROUTE
    ) {
        composable(route = Destinations.Branding.route) {
            BrandingScreen(navController = navController)
        }
        composable(route = Destinations.DailyCalculator.route) {
            val parentEntry = remember {
                navController.getBackStackEntry(Routes.APP_ROUTE)
            }
            val appViewModel = hiltViewModel<MyViewModel>(parentEntry)
            DailyCalculatorScreen(name = "tzo", vm = appViewModel, navController = navController)
        }
        composable(route = Destinations.HistoryList.route) {
            val parentEntry = remember {
                navController.getBackStackEntry(Routes.APP_ROUTE)
            }
            val appViewModel = hiltViewModel<MyViewModel>(parentEntry)
            HistoryListScreen(navController = navController, vm = appViewModel)
        }
        composable(route = Destinations.HistoryDetail.route,
            arguments = listOf(
            navArgument(name = ArgConstants.ID) {
                type = NavType.LongType
            }
        )) {
            val parentEntry = remember {
                navController.getBackStackEntry(Routes.APP_ROUTE)
            }
            val appViewModel = hiltViewModel<MyViewModel>(parentEntry)
            HistoryDetailScreen(navController = navController, vm = appViewModel, id = it.arguments?.getLong(ArgConstants.ID)?:0L)
        }


    }
}