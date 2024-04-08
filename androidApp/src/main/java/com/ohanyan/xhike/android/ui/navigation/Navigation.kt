package com.ohanyan.xhike.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ohanyan.xhike.android.ui.home.HomeScreen
import com.ohanyan.xhike.android.ui.map.MapScreen
import com.ohanyan.xhike.android.ui.splash.SplashScreen

sealed class Screen(val route: String) {
    data object SplashScreen : Screen("splashScreen")
    data object HomeScreen : Screen("homeScreen")
    data object MapScreen : Screen("mapScreen")
}

@Composable
internal fun NavMain() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.SplashScreen.route) {

        composable(route = Screen.SplashScreen.route) {
            SplashScreen(
                navController = navController,
            )
        }

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                navController = navController,
            )
        }

        composable(route = Screen.MapScreen.route) {
            MapScreen(
                navController = navController,
            )
        }

//        composable(
//            route = "${Screen.OtpVerificationCode.route}/{retrySeconds}/{phone}",
//            arguments = listOf(
//                navArgument("retrySeconds") { type = NavType.IntType },
//                navArgument("phone") { type = NavType.StringType })
//        ) { backStackEntry ->
//
//            OtpVerificationCode(
//                navController = navController,
//                phoneNumber = backStackEntry.arguments?.getString("phone") ?: "",
//                retrySecondsArg = backStackEntry.arguments?.getInt("retrySeconds") ?: 10
//            )
//        }

    }
}
