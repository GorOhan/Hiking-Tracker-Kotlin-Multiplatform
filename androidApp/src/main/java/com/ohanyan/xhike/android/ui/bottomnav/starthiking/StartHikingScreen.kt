package com.ohanyan.xhike.android.ui.bottomnav.starthiking

import android.graphics.Color
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.mapbox.common.location.Location
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString

import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.ImageHolder
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.navigation.core.lifecycle.MapboxNavigationApp
import com.mapbox.navigation.core.lifecycle.forwardMapboxNavigation
import com.mapbox.navigation.core.trip.session.LocationMatcherResult
import com.mapbox.navigation.core.trip.session.LocationObserver
import com.mapbox.navigation.ui.maps.location.NavigationLocationProvider
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.android.ui.bottomnav.starthiking.map.MapContainer
import com.ohanyan.xhike.android.ui.navigation.Screen
import org.koin.androidx.compose.getViewModel

@Composable
fun StartHikingScreen(
    navController: NavController,
    startHikingViewModel: StartHikingViewModel = getViewModel()
) {
    val navigationLocationProvider = NavigationLocationProvider()

    val alpha = remember { Animatable(1f) }
    val infiniteTransition = rememberInfiniteTransition()


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
        Image(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .alpha(1f)
                .height(50.dp)
                .clickable {
                    startHikingViewModel.startHiking()
                },
            painter = painterResource(id = R.drawable.ic_compass),
            contentDescription = ""
        )

        Image(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .alpha(1f)
                .height(50.dp)
                .clickable {
                    startHikingViewModel.insertHikeInDb()
                },
            painter = painterResource(id = R.drawable.ic_stop),
            contentDescription = ""
        )
        Row(
            modifier = Modifier
                .padding(vertical = 24.dp, horizontal = 8.dp)
                .align(Alignment.BottomEnd),
        ) {
            Image(
                modifier = Modifier
                    .alpha(1f)
                    // .alpha(if (xOffset <= 1) 0.2f else 1f)
                    .offset(y = if (xOffset >= 1) 30.dp * (xOffset - 1) else 30.dp)
                    .height(50.dp),
                painter = painterResource(id = R.drawable.left_foot),
                contentDescription = ""
            )
            Image(
                modifier = Modifier
                    .alpha(1f)
                    //  .alpha(if (xOffset <= 1) 1f else 0.2f)
                    .offset(y = if (xOffset <= 1) 30.dp * (xOffset) else 30.dp)
                    .height(50.dp),
                painter = painterResource(id = R.drawable.right_foot),
                contentDescription = ""
            )
        }
    }
    val locationObserver = object : LocationObserver {

        override fun onNewRawLocation(rawLocation: Location) {

        }

        override fun onNewLocationMatcherResult(locationMatcherResult: LocationMatcherResult) {
            navigationLocationProvider.changePosition(
                location = locationMatcherResult.enhancedLocation,
                keyPoints = locationMatcherResult.keyPoints,
            )
        }
    }
}


private const val GEOJSON_SOURCE_ID = "line"
