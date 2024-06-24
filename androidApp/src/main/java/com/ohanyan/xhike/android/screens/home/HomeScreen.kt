package com.ohanyan.xhike.android.screens.home

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.ohanyan.xhike.android.screens.home.trails.TrailsScreenNavMain
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.android.screens.home.aboutus.AboutUsScreen
import com.ohanyan.xhike.android.screens.home.starthiking.StartHikingScreen
import com.ohanyan.xhike.android.screens.home.trails.component.BaseDialog
import com.ohanyan.xhike.android.util.hasAllPermission

@Composable
fun HomeScreen() {
    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    val navController = rememberNavController()
    val context = LocalContext.current
    var showPermissionsDialog by remember { mutableStateOf(false) }

    LifecycleResumeEffect(Unit) {
        showPermissionsDialog = !context.hasAllPermission()
        onPauseOrDispose {  }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(
                modifier = Modifier.clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.background)
            ) {
                BottomNavigationItem().bottomNavigationItems()
                    .forEachIndexed { index, navigationItem ->
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
                                    style = MaterialTheme.typography.titleSmall
                                )
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
                                if (context.hasAllPermission()) {
                                    navigationSelectedItem = index
                                    navController.navigate(navigationItem.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                } else {
                                    showPermissionsDialog = true
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
                TrailsScreenNavMain()
                navigationSelectedItem = 0
            }
            composable(MainScreens.StartHikingScreen.route) {
                StartHikingScreen(navController = navController)
                navigationSelectedItem = 1
            }
            composable(MainScreens.AboutUsScreen.route) {
                AboutUsScreen(navController = navController)
                navigationSelectedItem = 2
            }
        }
    }

    if (showPermissionsDialog) {
        BaseDialog(
            title = stringResource(id = R.string.permission_needed_text),
            positiveButtonText = stringResource(id = R.string.go_to_settings),
            onConfirmClick = {
                val intent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)

            },
            onDismissRequest = {
                showPermissionsDialog = false
            }
        )
    }
}