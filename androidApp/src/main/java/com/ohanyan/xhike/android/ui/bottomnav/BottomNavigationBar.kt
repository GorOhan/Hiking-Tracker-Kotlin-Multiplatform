package com.ohanyan.xhike.android.ui.bottomnav

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ohanyan.xhike.android.ui.bottomnav.aboutus.AboutUsScreen
import com.ohanyan.xhike.android.ui.bottomnav.starthiking.StartHikingScreen
import com.ohanyan.xhike.android.ui.bottomnav.trails.TrailsScreen
import com.ohanyan.xhike.android.ui.navigation.TrailsScreenNavMain

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigationBar() {
    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                modifier = Modifier.clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.background)
            ) {
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.secondary,
                            selectedIconColor = MaterialTheme.colorScheme.tertiary,
                        ),
                        selected = index == navigationSelectedItem,
                        label = {
                            Text(
                                navigationItem.label,
                                color = MaterialTheme.colorScheme.background,
                                style = MaterialTheme.typography.titleSmall)
                        },
                        icon = {
                            Icon(
                                painterResource(id = navigationItem.icon),
                                contentDescription = navigationItem.label,
                                tint = if (index != navigationSelectedItem) MaterialTheme.colorScheme.background
                                else MaterialTheme.colorScheme.tertiary

                            )
                        },
                        onClick = {
                            navigationSelectedItem = index
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = MainScreens.TrailsScreen.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(MainScreens.TrailsScreen.route) {
                //TrailsScreen(navController =  navController)
                TrailsScreenNavMain()
            }
            composable(MainScreens.StartHikingScreen.route) {
                StartHikingScreen(navController =  navController)
            }
            composable(MainScreens.AboutUsScreen.route) {
                AboutUsScreen(navController =  navController)
            }
        }
    }
}