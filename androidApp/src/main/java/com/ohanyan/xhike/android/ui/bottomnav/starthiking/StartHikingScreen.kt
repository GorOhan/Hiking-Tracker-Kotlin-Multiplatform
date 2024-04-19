package com.ohanyan.xhike.android.ui.bottomnav.starthiking

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mapbox.geojson.Point
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.android.ui.bottomnav.starthiking.map.MapContainer
import com.ohanyan.xhike.android.ui.navigation.Screen
import org.koin.androidx.compose.getViewModel
import org.osmdroid.util.GeoPoint

@Composable
fun StartHikingScreen(
    navController: NavController,
    startHikingViewModel: StartHikingViewModel = getViewModel()
) {
    val alpha = remember { Animatable(1f) }
    val infiniteTransition = rememberInfiniteTransition()

    val points by startHikingViewModel.points.collectAsState()

    val xOffset by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 0f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
    )

    LaunchedEffect(Unit) {
        alpha.animateTo(
            targetValue = 0f,
            animationSpec = androidx.compose.animation.core.tween(durationMillis = 1000)
        )
    }

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        MapContainer(
            startHikingViewModel = startHikingViewModel,
        )
        Row(
            modifier = Modifier
                .padding(vertical = 24.dp,horizontal = 8.dp)
                .align(Alignment.BottomEnd),
        ) {
            Image(
                modifier = Modifier
                    .alpha(1f)
                   // .alpha(if (xOffset <= 1) 0.2f else 1f)
                    .offset(y = if  (xOffset>=1 ) 30.dp * (xOffset-1) else 30.dp)
                    .height(50.dp),
                painter = painterResource(id = R.drawable.left_foot),
                contentDescription = ""
            )
            Image(
                modifier = Modifier
                    .alpha(1f)
                  //  .alpha(if (xOffset <= 1) 1f else 0.2f)
                    .offset(y = if  (xOffset<=1) 30.dp * (xOffset) else 30.dp)
                    .height(50.dp),
                painter = painterResource(id = R.drawable.right_foot),
                contentDescription = ""
            )
        }
    }
}
