package com.ohanyan.xhike.android.ui.bottomnav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = ""
) {
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Trails",
                icon = Icons.Filled.Home,
                route = MainScreens.TrailsScreen.route
            ),
            BottomNavigationItem(
                label = "Start",
                icon = Icons.Filled.Search,
                route = MainScreens.StartHikingScreen.route
            ),
            BottomNavigationItem(
                label = "About Us",
                icon = Icons.Filled.AccountCircle,
                route = MainScreens.AboutUsScreen.route
            ),
        )
    }
}