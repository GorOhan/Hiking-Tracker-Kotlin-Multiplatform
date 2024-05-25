package com.ohanyan.xhike.android.ui.main.starthiking.map

import android.Manifest
import android.annotation.SuppressLint
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
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.ohanyan.xhike.android.ui.main.starthiking.StartHikingViewModel

lateinit var locationCallback: LocationCallback

@Composable
fun MapContainer(
    startHikingViewModel: StartHikingViewModel,
) {
    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    val routePoints by startHikingViewModel.points.collectAsState()
    val startHiking by startHikingViewModel.startHiking.collectAsState()

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
                            mapboxMap.setCamera(
                                CameraOptions.Builder()
                                    .center(currentPoint)
                                    .zoom(10.0)
                                    .build()
                            )
                            startHikingViewModel.addPoint(
                                Point.fromLngLat(
                                    locationResult.lastLocation?.longitude ?: 0.0,
                                    locationResult.lastLocation?.latitude ?: 0.0
                                )
                            )
                            mapboxMap.loadStyle(Style.MAPBOX_STREETS) { style ->
                                style.addSource(
                                    GeoJsonSource.Builder("line-source")
                                        .featureCollection(
                                            FeatureCollection.fromFeatures(
                                                arrayOf(
                                                    Feature.fromGeometry(
                                                        LineString.fromLngLats(
                                                            routePoints
                                                        )
                                                    )
                                                )
                                            )
                                        )
                                        .build()
                                )

                                val lineLayer = LineLayer("line-layer", "line-source")
                                lineLayer.lineWidth(5.0)
                                lineLayer.lineColor(Color.parseColor("#FF0000"))

                                style.addLayer(lineLayer)

                            }

                        }
                    }


                }

            }


        },
        update = { mapView ->
            if (startHiking){
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->

                val symbol = SymbolLayer("symbol-layer-id", "source-id")
                symbol.iconImage("ic_route-image-id")

                val feature = Feature.fromGeometry(Point.fromLngLat(location?.longitude!!, location.latitude))
                val featureCollection = FeatureCollection.fromFeature(feature)

                mapView.mapboxMap.loadStyle(Style.MAPBOX_STREETS) { style ->
                    style.addLayer(symbol)
                    style.addSource(
                        GeoJsonSource.Builder("source-id")
                            .featureCollection(
                                featureCollection
                            )
                            .build()
                    )
                }
            }}
        }
    )
}


