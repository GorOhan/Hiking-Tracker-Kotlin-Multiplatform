package com.ohanyan.xhike.android.ui.main.trails.singletrail

import android.graphics.Color
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
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
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleTrailScreen(
    navController: NavController,
    hikeId: Int,
    singleTrailViewModel: SingleTrailViewModel = getViewModel()
) {

    val routePoints by  singleTrailViewModel.points.collectAsState()
    val pagerState = rememberPagerState(pageCount = {
        4
    })

    LaunchedEffect(Unit) {
        singleTrailViewModel.getHikeById(hikeId)
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
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


        HorizontalPager(state = pagerState) { page ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue
                    }
            ) {
                // Card content
            }
        }
    }
}

