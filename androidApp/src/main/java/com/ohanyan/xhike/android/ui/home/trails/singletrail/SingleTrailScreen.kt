package com.ohanyan.xhike.android.ui.home.trails.singletrail

import android.graphics.Color
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
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

    val currentHike by singleTrailViewModel.hike.collectAsState()
    val routePoints by singleTrailViewModel.points.collectAsState()
    val pagerState = rememberPagerState(pageCount = { 4 })

    LaunchedEffect(Unit) {
        singleTrailViewModel.getHikeById(hikeId)
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primary)
            ,

            ) {
            Column {
                AndroidView(
                    modifier = Modifier
                        .height(220.dp),
                    factory = { context ->

                        MapView(context).apply {

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

                            mapboxMap.loadStyle(Style.OUTDOORS) { style ->
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
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(80.dp)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                   Text(
                       text = currentHike?.hikeName?:"",
                       style = MaterialTheme.typography.titleMedium,
                       color = MaterialTheme.colorScheme.background
                       )

                }


            }
        }


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

        Button(
            modifier = Modifier
                .padding(bottom = 16.dp),
            onClick = {
                navController.navigate("${TrailsScreenRoute.FollowTrailScreen.route}/${hikeId}")

            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(
                text = "Follow",
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

class TriangleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width-40, size.height/4)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}
