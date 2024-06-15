package com.ohanyan.xhike.android.screens.home.starthiking

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.android.service.GetLocationService
import com.ohanyan.xhike.android.screens.home.MainScreens
import com.ohanyan.xhike.android.screens.home.starthiking.map.MapContainer
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

fun drawableIdToBitmap(context: Context, drawableId: Int): Bitmap {
    val drawable =
        context.getDrawable(drawableId) ?: throw IllegalArgumentException("Invalid drawable ID")

    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }

    val width = if (drawable.intrinsicWidth > 0) drawable.intrinsicWidth else 1
    val height = if (drawable.intrinsicHeight > 0) drawable.intrinsicHeight else 1
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}