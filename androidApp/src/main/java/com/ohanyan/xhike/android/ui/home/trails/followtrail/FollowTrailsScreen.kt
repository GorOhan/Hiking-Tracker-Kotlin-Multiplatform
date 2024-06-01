package com.ohanyan.xhike.android.ui.home.trails.followtrail

import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateBearing
import com.mapbox.maps.plugin.viewport.data.FollowPuckViewportStateOptions
import com.mapbox.maps.plugin.viewport.state.FollowPuckViewportState
import com.mapbox.maps.plugin.viewport.viewport
import org.koin.androidx.compose.getViewModel

@Composable
fun FollowTrailScreen(
    navController: NavController,
    hikeId: Int,
    followTrailViewModel: FollowTrailViewModel = getViewModel()
) {
    val context = LocalContext.current
    val routePoints by followTrailViewModel.points.collectAsState()
    val userLocation by followTrailViewModel.userLocation.collectAsState()
    val loadState by followTrailViewModel.loadStyle.collectAsState()

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val locationRequest = LocationRequest.Builder(100L)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .setMinUpdateIntervalMillis(1000L)
        .build()


    LaunchedEffect(Unit) {
        followTrailViewModel.getHikeById(hikeId)

//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            fusedLocationClient.requestLocationUpdates(
//                locationRequest,
//                locationCallback,
//                null /* Looper */
//            )
//        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                MapView(context).apply {


                            mapboxMap.loadStyle(Style.OUTDOORS) { style ->
//                                style.addSource(
//                                    GeoJsonSource.Builder("user-location-source")
//                                        .geometry(currentPoint)
//                                        .build()
//                                )

                              //  val arrowBitmap = drawableIdToBitmap(context, R.drawable.ic_compass)
                           //     style.addImage("user-location-icon", arrowBitmap)

                                val lineLayer = LineLayer("line-layer", "line-source")
                                lineLayer.lineWidth(5.0)
                                lineLayer.lineColor(Color.parseColor("#175366"))

//                                val symbolLayer =
//                                    SymbolLayer("user-location-layer", "user-location-source")
//                                symbolLayer.iconImage("user-location-icon")
//                                symbolLayer.iconSize(1.0)

                                style.addSource(
                                    GeoJsonSource.Builder("line-source")
                                        .featureCollection(
                                            FeatureCollection.fromFeatures(
                                                arrayOf(
                                                    Feature.fromGeometry(
                                                        routePoints?.let {
                                                            LineString.fromLngLats(
                                                                it
                                                            )
                                                        }
                                                    )
                                                )
                                            )
                                        )
                                        .build()
                                )

                                style.addLayer(lineLayer)
                               // style.addLayer(symbolLayer)


                        }

                    mapboxMap.setCamera(
                        CameraOptions.Builder()
                            .center(
                                routePoints?.first()
                            )
                            .pitch(0.0)
                            .zoom(9.0)
                            .bearing(0.0)
                            .build()
                    )


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
                    val viewportPlugin = mapView.viewport
// transition to followPuckViewportState with default transition
                    val followPuckViewportState: FollowPuckViewportState = viewportPlugin.makeFollowPuckViewportState(
                        FollowPuckViewportStateOptions.Builder()
                            .bearing(FollowPuckViewportStateBearing.Constant(0.0))
                            .padding(EdgeInsets(200.0 * resources.displayMetrics.density, 0.0, 0.0, 0.0))
                            .build()
                    )
                    viewportPlugin.transitionTo(followPuckViewportState) { success ->
                        // the transition has been completed with a flag indicating whether the transition succeeded
                    }
                }
            }
        )
    }
}

