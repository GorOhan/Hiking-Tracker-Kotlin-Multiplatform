package com.ohanyan.xhike.android.ui.home

import com.ohanyan.xhike.android.R

data class BottomNavigationItem(
    val label : String = "",
    val icon : Int = R.drawable.ic_route,
    val route : String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Trails",
                icon = R.drawable.ic_route,
                route = MainScreens.TrailsScreen.route
            ),
            BottomNavigationItem(
                label = "Start",
                icon = R.drawable.ic_hiking,
                route = MainScreens.StartHikingScreen.route
            ),
            BottomNavigationItem(
                label = "About Us",
                icon = R.drawable.ic_about,
                route = MainScreens.AboutUsScreen.route
            ),
        )
    }
}