package com.ohanyan.xhike.android.ui.bottomnav.trails.singletrail

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.geojson.LineString
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.layers.addLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource
import org.koin.androidx.compose.getViewModel

@Composable
fun SingleTrailScreen(
    navController: NavController,
    hikeId: Int,
    singleTrailViewModel: SingleTrailViewModel = getViewModel()
) {

    val routePoints by  singleTrailViewModel.points.collectAsState()

    LaunchedEffect(Unit) {
        singleTrailViewModel.getHikeById(hikeId)
    }
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        AndroidView(
            modifier = Modifier
                .height(300.dp),
            factory = { context ->

                MapView(context).apply {


                        mapboxMap.setCamera(
                            CameraOptions.Builder()
                                .center(
                                   routePoints?.first()
                                )
                                .pitch(0.0)
                                .zoom(7.0)
                                .bearing(0.0)
                                .build()
                        )


                        mapboxMap.loadStyle(Style.MAPBOX_STREETS) { style ->
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

                            val lineLayer = LineLayer("line-layer", "line-source")
                            lineLayer.lineWidth(5.0)
                            lineLayer.lineColor(Color.parseColor("#FF0000"))

                            style.addLayer(lineLayer)

                        }
                }

            },
            update = { mapView ->
                mapView.apply {

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

                    mapboxMap.loadStyle(Style.MAPBOX_STREETS) { style ->
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

                        val lineLayer = LineLayer("line-layer", "line-source")
                        lineLayer.lineWidth(5.0)
                        lineLayer.lineColor(Color.parseColor("#175366"))
                        style.addLayer(lineLayer)
                    }
                }
            }
        )
    }
}

