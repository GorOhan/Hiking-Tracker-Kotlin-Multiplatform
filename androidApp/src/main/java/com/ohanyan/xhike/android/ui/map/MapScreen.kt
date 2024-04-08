package com.ohanyan.xhike.android.ui.map

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ohanyan.xhike.android.ui.home.HomeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun MapScreen(
    navController: NavController,
    splashViewModel: HomeViewModel = getViewModel()
) {

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        Box(modifier = Modifier.height(250.dp)){
            Map(modifier = Modifier.fillMaxSize())
        }
        Row(
            modifier = Modifier.fillMaxHeight(0.2f),
        ) {
            Text(
                text = "Map",
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}


@SuppressLint("UseCompatLoadingForDrawables")
@Composable
fun Map(modifier:Modifier = Modifier) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
//            val markerIcon =
//                context.resources.getDrawable(R.drawable.ic_point) // Replace with your custom marker icon resource


            MapView(context).apply {
                setMultiTouchControls(true)
                setTileSource(TileSourceFactory.MAPNIK)
                controller.setCenter(GeoPoint(40.1772, 44.5035))

//                val yerevanMarker = Marker(this)
//                yerevanMarker.position = GeoPoint(40.1772, 44.5035)
//                yerevanMarker.title = "Yerevan"
//                yerevanMarker.icon = markerIcon
//                overlays.add(yerevanMarker)
//
                val startPoint = GeoPoint(40.1772, 44.5035)
                val secondPoint = GeoPoint(40.1772, 44.5040)
                val endPoint = GeoPoint(40.1862, 44.5152)
//               // addMarker(mapView, startPoint, "Start")
//              //  addMarker(mapView, endPoint, "End")
//
//                // Add polyline to represent the route
                val routePoints = mutableListOf(startPoint, secondPoint, endPoint)
                val routePolyline = Polyline()
                routePolyline.setPoints(routePoints)
                routePolyline.color = Color.RED // Set the color of the route line
                overlays.add(routePolyline)

                val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), this)
                locationOverlay.enableMyLocation()
                controller.setCenter(locationOverlay.myLocation)
                controller.setZoom(20.0)
                overlays.add(locationOverlay)

            }


        },
        update = { mapView ->

        }
    )
}