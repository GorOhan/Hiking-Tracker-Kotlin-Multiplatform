package com.ohanyan.xhike.android.ui.bottomnav.starthiking.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.android.ui.bottomnav.starthiking.StartHikingViewModel
import com.ohanyan.xhike.android.ui.home.HomeViewModel
import org.koin.androidx.compose.getViewModel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline


@Composable
fun MapScreen(
    startHikingViewModel: StartHikingViewModel,
) {

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        Box(modifier = Modifier.height(250.dp)) {
            //  MapContainer()
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
fun MapContainer(
    startHikingViewModel: StartHikingViewModel,
) {
    val context = LocalContext.current
    var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    val routePoints by startHikingViewModel.points.collectAsState()

    AndroidView(
        modifier = Modifier,
        factory = { context ->

            MapView(context).apply {

                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED && true
                ) {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->

                            controller.setCenter(
                                GeoPoint(
                                    location?.latitude ?: 0.0,
                                    location?.longitude ?: 0.0
                                )
                            )

                            val markerIcon =
                                context.resources.getDrawable(R.drawable.ic_hiking)
                            val yerevanMarker = Marker(this)
                            yerevanMarker.position =
                                GeoPoint(location?.latitude ?: 0.0, location?.longitude ?: 0.0)
                            yerevanMarker.title = "Yerevan"
                            yerevanMarker.icon = markerIcon
                            overlays.add(yerevanMarker)
                        }
                    val locationRequest = LocationRequest.create().apply {
                        interval = 10000 // Update interval in milliseconds
                        fastestInterval = 5000 // Fastest update interval in milliseconds
                        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    }


                    val locationCallback = object : LocationCallback(
                    ) {
                        override fun onLocationResult(locationResult: LocationResult) {
//                            routePoints.addAll(locationResult.locations.map {
//                                GeoPoint(it.latitude, it.longitude)
//                            })
                           startHikingViewModel.addPoint(
                                     GeoPoint(
                                         locationResult.lastLocation?.latitude ?: 0.0,
                                         locationResult.lastLocation?.longitude ?: 0.0
                                     )
                                 )

                            val routePolyline = Polyline()
                            routePolyline.setPoints(routePoints)
                            println("FROM SCREEN$routePoints")

                            routePolyline.color = Color.argb(240, 16, 8, 0)
                            overlays.clear()
                            overlays.add(routePolyline)
                            invalidate()
                        }
                    }

                    fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        null /* Looper */
                    )

                }
                controller.setZoom(17.0)

            }


        },
        update = { mapView ->

            val locationRequest = LocationRequest.create().apply {
                interval = 5000 // Update interval in milliseconds
                fastestInterval = 5000 // Fastest update interval in milliseconds
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            val locationCallback = object : LocationCallback(
            ) {
                override fun onLocationResult(locationResult: LocationResult) {
//                    mapView.controller.setCenter(
//                        GeoPoint(
//                            locationResult.lastLocation?.latitude ?: 0.0,
//                            locationResult.lastLocation?.longitude ?: 0.0
//                        )
//                    )


//                    val routePolyline = Polyline()
//                    routePolyline.setPoints(routePoints)
//                    mapView.invalidate()
//
//
//                    routePolyline.color = Color.parseColor("#175366")
//                    mapView.overlays.add(routePolyline)
//
//                    mapView.invalidate()
                }
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null /* Looper */
            )

        }
    )
}


