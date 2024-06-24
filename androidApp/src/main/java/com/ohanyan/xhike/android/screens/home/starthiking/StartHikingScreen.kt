package com.ohanyan.xhike.android.screens.home.starthiking

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.android.service.GetLocationService
import com.ohanyan.xhike.android.screens.home.MainScreens
import com.ohanyan.xhike.android.screens.home.starthiking.map.MapContainer
import com.ohanyan.xhike.android.screens.home.trails.trailsettings.TrailSettingScreenUI
import com.ohanyan.xhike.android.util.MyApplicationTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun StartHikingScreen(
    navController: NavController,
    startHikingViewModel: StartHikingViewModel = getViewModel()
) {
    val context = LocalContext.current
    val isHikeStarted by startHikingViewModel.startHiking.collectAsState()

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        MapContainer(startHikingViewModel = startHikingViewModel)

        Button(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter),
            onClick = {
                if (isHikeStarted) {
                    startHikingViewModel.finishHike()
                    navController.navigate(MainScreens.TrailsScreen.route)
                    val serviceIntent = Intent(context, GetLocationService::class.java)
                    context.stopService(serviceIntent)


                } else {
                    startHikingViewModel.startHiking()
                    val serviceIntent = Intent(context, GetLocationService::class.java)
                    context.startForegroundService(serviceIntent)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isHikeStarted)
                    MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(
                text = stringResource(id = if (isHikeStarted) R.string.finish_hiking else R.string.start_hiking),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview
@Composable
fun StartHikingScreenPreview() {
    MyApplicationTheme {
        TrailSettingScreenUI()
    }
}
