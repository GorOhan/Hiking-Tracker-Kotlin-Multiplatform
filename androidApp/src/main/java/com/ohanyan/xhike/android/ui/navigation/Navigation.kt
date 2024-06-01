package com.ohanyan.xhike.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ohanyan.xhike.android.ui.home.BottomNavigationBar
import com.ohanyan.xhike.android.ui.splash.SplashScreen

sealed class Screen(val route: String) {
    data object SplashScreen : Screen("splashScreen")
    data object HomeScreen : Screen("homeScreen")
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
            BottomNavigationBar()
        }
    }
}
