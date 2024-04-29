package com.ohanyan.xhike.android.ui.bottomnav.starthiking

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ohanyan.xhike.android.ui.bottomnav.MainScreens
import com.ohanyan.xhike.android.ui.bottomnav.starthiking.map.MapContainer
import org.koin.androidx.compose.getViewModel

@Composable
fun StartHikingScreen(
    navController: NavController,
    startHikingViewModel: StartHikingViewModel = getViewModel()
) {

    val isHikeStarted by startHikingViewModel.startHiking.collectAsState()

//    val xOffset by rememberInfiniteTransition(label = "").animateFloat(
//        initialValue = 0f,
//        targetValue = 2f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(durationMillis = 1200, easing = LinearEasing),
//            repeatMode = RepeatMode.Restart
//        ),
//    )


    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        MapContainer(
            startHikingViewModel = startHikingViewModel,
        )
//        Image(
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .alpha(1f)
//                .height(50.dp)
//                .clickable {
//                    startHikingViewModel.startHiking()
//                },
//            painter = painterResource(id = R.drawable.ic_compass),
//            contentDescription = ""
//        )
        Button(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter),
            onClick = {
                if (isHikeStarted) {
                    startHikingViewModel.finishHike()
                    navController.navigate(MainScreens.TrailsScreen.route)
                } else {
                    startHikingViewModel.startHiking()
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
                text = if (isHikeStarted) "Finish Hike" else "Start Hike",
                style = MaterialTheme.typography.titleMedium,
            )
        }

//        Row(
//            modifier = Modifier
//                .padding(vertical = 24.dp, horizontal = 8.dp)
//                .align(Alignment.BottomEnd),
//        ) {
//            Image(
//                modifier = Modifier
//                    .alpha(1f)
//                    // .alpha(if (xOffset <= 1) 0.2f else 1f)
//                    .offset(y = if (xOffset >= 1) 30.dp * (xOffset - 1) else 30.dp)
//                    .height(50.dp),
//                painter = painterResource(id = R.drawable.left_foot),
//                contentDescription = ""
//            )
//            Image(
//                modifier = Modifier
//                    .alpha(1f)
//                    //  .alpha(if (xOffset <= 1) 1f else 0.2f)
//                    .offset(y = if (xOffset <= 1) 30.dp * (xOffset) else 30.dp)
//                    .height(50.dp),
//                painter = painterResource(id = R.drawable.right_foot),
//                contentDescription = ""
//            )
//        }
    }

}