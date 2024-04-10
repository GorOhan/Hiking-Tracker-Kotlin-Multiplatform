package com.ohanyan.xhike.android.ui.bottomnav.starthiking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ohanyan.xhike.android.ui.map.MapboxMapCon
import org.koin.androidx.compose.getViewModel

@Composable
fun StartHikingScreen(
    navController: NavController,
    startHikingViewModel: StartHikingViewModel = getViewModel()
) {



    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        //MapContainer()
        MapboxMapCon(
                context = LocalContext.current
            )
//        Text(
//            modifier = Modifier.clickable {
//                navController.navigate(Screen.MapScreen.route)
//            },
//            text = "Start Hiking",
//            color = MaterialTheme.colorScheme.onSecondary,
//            style = MaterialTheme.typography.titleLarge
//        )
    }
}
