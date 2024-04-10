package com.ohanyan.xhike.android.ui.map

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
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
            MapContainer()
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
fun MapContainer() {
    AndroidView(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth(),
        factory = { context ->
//            val markerIcon =
//                context.resources.getDrawable(R.drawable.ic_point) // Replace with your custom marker icon resource


            MapView(context).apply {

//                val yerevanMarker = Marker(this)
//                yerevanMarker.position = GeoPoint(40.1772, 44.5035)
//                yerevanMarker.title = "Yerevan"
//                yerevanMarker.icon = markerIcon
//                overlays.add(yerevanMarker)
//
//                val startPoint = GeoPoint(40.1772, 44.5035)
//                val secondPoint = GeoPoint(40.1772, 44.5040)
//                val endPoint = GeoPoint(40.1862, 44.5152)
//               // addMarker(mapView, startPoint, "Start")
//              //  addMarker(mapView, endPoint, "End")
//
//                // Add polyline to represent the route
//                val routePoints = mutableListOf(startPoint, secondPoint, endPoint)
//                val routePolyline = Polyline()
//                routePolyline.setPoints(routePoints)
//                routePolyline.color = Color.RED // Set the color of the route line
//                overlays.add(routePolyline)
//
//                val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), this)
//                locationOverlay.enableMyLocation()
//                controller.setCenter(locationOverlay.myLocation)
//                controller.setZoom(20.0)
//                overlays.add(locationOverlay)

            }


        },
        update = { mapView ->

        }
    )
}

@SuppressLint("MissingPermission")
@Composable
fun MapboxMapCon(
    context: Context,
    modifier: Modifier = Modifier,
    onMapViewCreated: (MapView) -> Unit = {}
) {
    // Initialization options for setting the access token and other configurations
    val initOptions = MapInitOptions(
        context = context,
    )

    AndroidView(
        modifier = modifier,
        factory = { context ->
            MapView(context, initOptions).apply {
                getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) {
                    // Map is ready to use
                }
            }

        },
        update = { mapView ->
            mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
            onMapViewCreated(mapView)
        }
    )
}