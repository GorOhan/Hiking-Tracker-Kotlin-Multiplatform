package com.ohanyan.xhike.android.ui.bottomnav.trails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ohanyan.xhike.android.ui.navigation.Screen
import org.koin.androidx.compose.getViewModel

@Composable
fun TrailsScreen(
    navController: NavController,
    trailsViewModel: TrailsViewModel = getViewModel()
) {

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.clickable {
                navController.navigate(Screen.MapScreen.route)
            },
            text = "Trails",
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
