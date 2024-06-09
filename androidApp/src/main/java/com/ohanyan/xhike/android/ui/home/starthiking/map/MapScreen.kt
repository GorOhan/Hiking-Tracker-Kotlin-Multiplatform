package com.ohanyan.xhike.android.ui.home.starthiking.map


import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleStartEffect
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
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

@Composable
fun MapContainer(
    startHikingViewModel: StartHikingViewModel,
) {
    val context = LocalContext.current
    val hasPointChange by startHikingViewModel.hasPointChange.collectAsState()

    LifecycleStartEffect(startHikingViewModel) {
        startHikingViewModel.drawRoute()
        onStopOrDispose { }
    }

    AndroidView(
        modifier = Modifier,
        factory = { ctx ->

            MapView(ctx).apply {

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
    )
}


