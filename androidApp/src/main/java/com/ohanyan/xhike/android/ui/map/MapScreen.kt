package com.ohanyan.xhike.android.ui.map

//import com.mapbox.maps.CameraOptions
//import com.mapbox.maps.MapInitOptions
//import com.mapbox.maps.MapView
//import com.mapbox.maps.Style
//import com.mapbox.maps.extension.style.layers.generated.lineLayer
//import com.mapbox.maps.extension.style.layers.properties.generated.LineCap
//import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
//import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
//import com.mapbox.maps.extension.style.style
//import com.mapbox.maps.plugin.annotation.annotations
//import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
//import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.mapbox.maps.extension.style.expressions.dsl.generated.within
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.android.ui.home.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.getViewModel
import org.osmdroid.tileprovider.MapTileProviderBasic
import org.osmdroid.tileprovider.tilesource.ITileSource
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.TilesOverlay
import java.util.concurrent.Flow


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
) {
    val context = LocalContext.current
    var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    var routePoints = mutableListOf<GeoPoint>()

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

                        controller.setCenter(GeoPoint(location?.latitude?:0.0, location?.longitude?:0.0))

                        val markerIcon =
                            context.resources.getDrawable(R.drawable.ic_hiking)
                        val yerevanMarker = Marker(this)
                        yerevanMarker.position = GeoPoint(location?.latitude?:0.0, location?.longitude?:0.0)
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
                        routePoints.addAll(locationResult.locations.map {
                            GeoPoint(it.latitude, it.longitude)
                        })


                        val routePolyline = Polyline()
                        routePolyline.setPoints(routePoints)
                        println("FROM SCREEN$routePoints")

                        routePolyline.color = Color.argb(240, 16, 8, 0)
                        overlays.add(routePolyline)
                        invalidate()
                    }
                }

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null /* Looper */)

            }

//                val markerIcon =
//                    context.resources.getDrawable(R.drawable.ic_hiking) // Replace with your custom marker icon resource
//
//
//
//
//
//                    //     this.setTileSource(TileSourceFactory.HIKEBIKEMAP)
//
//                    val yerevanMarker = Marker(this)
//                    yerevanMarker.position = GeoPoint(40.1772, 44.5035)
//                    yerevanMarker.title = "Yerevan"
//                    yerevanMarker.icon = markerIcon
//                    overlays.add(yerevanMarker)

//                val startPoint = GeoPoint(40.1772, 44.5035)
//                val secondPoint = GeoPoint(40.1772, 44.5040)
//                val endPoint = GeoPoint(40.1862, 44.5152)
                    // addMarker(mapView, startPoint, "Start")
                    //  addMarker(mapView, endPoint, "End")

                    // Add polyline to represent the route
                    // val routePoints = mutableListOf(startPoint, secondPoint, endPoint)
                    //   val routePolyline = Polyline()
                    //   routePolyline.setPoints(routePoints)
                    //   routePolyline.color = Color.BLUE // Set the color of the route line
                    //   overlays.add(routePolyline)

//                val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), this)
//                locationOverlay.enableMyLocation()
//                    controller.setCenter(GeoPoint(40.1772, 44.5035))
                    controller.setZoom(17.0)
                    //                overlays.add(locationOverlay)


                }


            },
            update = { mapView ->

                val locationRequest = LocationRequest.create().apply {
                    interval = 1000 // Update interval in milliseconds
                    fastestInterval = 500 // Fastest update interval in milliseconds
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }

                val locationCallback = object : LocationCallback(
                ) {
                    override fun onLocationResult(locationResult: LocationResult) {
//                        routePointslocationResult.locations.map {
//                            GeoPoint(it.latitude, it.longitude)
//                        }
                        mapView.controller.setCenter(
                            GeoPoint(
                                locationResult.lastLocation?.latitude?:0.0,
                                locationResult.lastLocation?.longitude?:0.0
                            )
                        )


                        val routePolyline = Polyline()
                        routePolyline.setPoints(routePoints)

                        routePolyline.color = Color.parseColor("#175366")
                        mapView.overlays.add(routePolyline)

                        mapView.invalidate()
                    }
                }

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null /* Looper */)

            }
            )
        }

                private const val GEOJSON_SOURCE_ID = "line"
    private const val LATITUDE = -122.486052
    private const val LONGITUDE = 37.830348
    private const val ZOOM = 14.0

