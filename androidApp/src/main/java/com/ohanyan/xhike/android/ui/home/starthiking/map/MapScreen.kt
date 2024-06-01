package com.ohanyan.xhike.android.ui.home.starthiking.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.viewport
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.android.ui.home.starthiking.StartHikingViewModel
import com.ohanyan.xhike.android.ui.home.starthiking.drawableIdToBitmap

private lateinit var locationCallback: LocationCallback

@Composable
fun MapContainer(
    startHikingViewModel: StartHikingViewModel,
) {
    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    val startHiking by startHikingViewModel.startHiking.collectAsState()
    val hasPointChange by startHikingViewModel.hasPointChange.collectAsState()

    val locationRequest = LocationRequest.Builder(100L)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .setMinUpdateIntervalMillis(1000L)
        .build()

    LaunchedEffect(startHiking) {
        if (startHiking) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null /* Looper */
                )
            }
        }
    }

    AndroidView(
        modifier = Modifier,
        factory = { context ->

            MapView(context).apply {

                mapboxMap.setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(-98.0, 39.5))
                        .pitch(0.0)
                        .zoom(2.0)
                        .bearing(0.0)
                        .build()
                )

                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->

                        }

                    locationCallback = object : LocationCallback(
                    ) {
                        override fun onLocationResult(locationResult: LocationResult) {

                            val currentPoint =
                                Point.fromLngLat(
                                    locationResult.lastLocation?.longitude ?: 0.0,
                                    locationResult.lastLocation?.latitude ?: 0.0
                                )
//                            mapboxMap.setCamera(
//                                CameraOptions.Builder()
//                                    .center(currentPoint)
//                                    .zoom(10.0)
//                                    .build()
//                            )
                            startHikingViewModel.addPoint(
                                Point.fromLngLat(
                                    locationResult.lastLocation?.longitude ?: 0.0,
                                    locationResult.lastLocation?.latitude ?: 0.0
                                )
                            )
                            if (hasPointChange) {
                                mapboxMap.loadStyle(Style.OUTDOORS) { style ->
//                                    style.addSource(
//                                        GeoJsonSource.Builder("user-location-source")
//                                            .geometry( Point.fromLngLat(
//                                                locationResult.lastLocation?.longitude ?: 0.0,
//                                                locationResult.lastLocation?.latitude ?: 0.0
//                                            ))
//                                            .build()
//                                    )

                                    style.addSource(
                                        GeoJsonSource.Builder("line-source")
                                            .featureCollection(
                                                FeatureCollection.fromFeatures(
                                                    arrayOf(
                                                        Feature.fromGeometry(
                                                            LineString.fromLngLats(
                                                                startHikingViewModel.points.value
                                                            )
                                                        )
                                                    )
                                                )
                                            )
                                            .build()
                                    )

                                    val arrowBitmap = drawableIdToBitmap(context, R.drawable.ic_compass)
                                    style.addImage("user-location-icon", arrowBitmap)
//                                    val symbolLayer =
//                                        SymbolLayer("user-location-layer", "user-location-source")
//                                    symbolLayer.iconImage("user-location-icon")
//                                    symbolLayer.iconSize(1.0)

                                    val lineLayer = LineLayer("line-layer", "line-source")
                                    lineLayer.lineWidth(5.0)
                                    lineLayer.lineColor(Color.parseColor("#FF0000"))

                                    style.addLayer(lineLayer)
                                    //style.addLayer(symbolLayer)
                                }
                            }
                        }
                    }
                }
            }
        },
        update = { mapView ->

            mapView.apply {

                location.locationPuck = createDefault2DPuck(withBearing = false)
                location.enabled = true
                location.pulsingEnabled = true
                location.puckBearing = PuckBearing.COURSE
                viewport.transitionTo(
                    targetState = viewport.makeFollowPuckViewportState(),
                    transition = viewport.makeImmediateViewportTransition()
                )
            }

        }
    )
}


