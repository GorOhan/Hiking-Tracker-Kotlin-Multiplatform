package com.ohanyan.xhike.android.screens.home

sealed class MainScreens(val route : String) {
    data object TrailsScreen : MainScreens("trails_screen")
    data object StartHikingScreen : MainScreens("start_hiking_screen")
    data object AboutUsScreen : MainScreens("about_us_screen")
}