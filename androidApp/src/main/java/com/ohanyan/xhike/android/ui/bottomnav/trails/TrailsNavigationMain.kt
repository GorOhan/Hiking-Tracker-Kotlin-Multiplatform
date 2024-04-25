package com.ohanyan.xhike.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ohanyan.xhike.android.ui.bottomnav.BottomNavigationBar
import com.ohanyan.xhike.android.ui.bottomnav.starthiking.StartHikingScreen
import com.ohanyan.xhike.android.ui.bottomnav.trails.SingleTrailScreen
import com.ohanyan.xhike.android.ui.bottomnav.trails.TrailsScreen
import com.ohanyan.xhike.android.ui.splash.SplashScreen

sealed class TrailsScreenRoute(val route: String) {
    data object TrailsScreen : TrailsScreenRoute("trailsScreen")
    data object SingleTrailScreen : TrailsScreenRoute("singleTrailScreen")
    data object MapScreen : TrailsScreenRoute("mapScreen")
}

@Composable
internal fun TrailsScreenNavMain() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = TrailsScreenRoute.TrailsScreen.route) {

        composable(route = TrailsScreenRoute.TrailsScreen.route) {
            TrailsScreen(
                navController = navController,
            )
        }

        composable(route = "${TrailsScreenRoute.SingleTrailScreen.route}/{hikeId}",
            arguments = listOf(
                navArgument("hikeId") { type = NavType.IntType },
                )
            ) { backStackEntry ->
            SingleTrailScreen(
                navController = navController,
                hikeId = backStackEntry.arguments?.getInt("hikeId")?:0
            )
        }

        composable(route = Screen.MapScreen.route) {
            StartHikingScreen(navController = navController)
        }

    }
}
