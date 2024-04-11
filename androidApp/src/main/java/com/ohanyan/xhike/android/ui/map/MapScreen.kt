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

import android.annotation.SuppressLint
import android.graphics.Color
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
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
        Box(modifier = Modifier.height(250.dp)){
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
     routePoints: List<GeoPoint>
) {
    val context = LocalContext.current
    AndroidView(
        modifier = Modifier ,
        factory = { context ->
            val markerIcon =
                context.resources.getDrawable(R.drawable.ic_hiking) // Replace with your custom marker icon resource


            MapView(context).apply {
           //     this.setTileSource(TileSourceFactory.HIKEBIKEMAP)

                val yerevanMarker = Marker(this)
                yerevanMarker.position = GeoPoint(40.1772, 44.5035)
                yerevanMarker.title = "Yerevan"
                yerevanMarker.icon = markerIcon
                overlays.add(yerevanMarker)

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
               controller.setCenter(GeoPoint(40.1772, 44.5035))
               controller.setZoom(17.0)
 //                overlays.add(locationOverlay)


            }


        },
        update = { mapView ->

            val routePolyline = Polyline()
            routePolyline.setPoints(routePoints)

            routePolyline.color = Color.BLUE
            mapView.overlays.add(routePolyline)
            mapView.invalidate()

        }
    )
}

//@SuppressLint("MissingPermission")
//@Composable
//fun MapboxMapCon(
//    context: Context,
//    modifier: Modifier = Modifier,
//    onMapViewCreated: (MapView) -> Unit = {}
//) {
//    // Initialization options for setting the access token and other configurations
//    val initOptions = MapInitOptions(
//        context = context,
//    )
//    val cameraOptions = CameraOptions.Builder()
//        .center(Point.fromLngLat(44.4991, 40.1792))
//        .zoom(11.0).build()
//
//    AndroidView(
//        modifier = modifier,
//        factory = { context ->
//
//            MapView(context, initOptions).apply {
//                getMapboxMap().setCamera(cameraOptions)
//
//                getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) {
//                    // Map is ready to use
//                }
//            }
//
//        },
//        update = { mapView ->
//
//            onMapViewCreated(mapView)
//        }
//    )
//}

//@Composable
//fun MapBoxMap(
//    modifier: Modifier = Modifier,
//    point: Point?,
//) {
//    val context = LocalContext.current
//    val marker = remember(context) {
//        context.getDrawable(R.drawable.ic_hiking)!!.toBitmap()
//    }
//    var pointAnnotationManager: PointAnnotationManager? by remember {
//        mutableStateOf(null)
//    }
//
//
//    AndroidView(
//        factory = {
//            MapView(it).also { mapView ->
//                mapView.getMapboxMap().loadStyleUri(Style.TRAFFIC_DAY)
//                val annotationApi = mapView.annotations
//                pointAnnotationManager = annotationApi.createPointAnnotationManager()
//            }
//        },
//        update = { mapView ->
//            mapView.mapboxMap.setCamera(
//                CameraOptions.Builder().center(
//                    point
//                ).zoom(4.0).build()
//            )
//
//
//            mapView.mapboxMap.loadStyle(
//                (
//                        style(style = Style.STANDARD) {
//                            +geoJsonSource(GEOJSON_SOURCE_ID) {
//                                url("asset://from_crema_to_council_crest.geojson")
//                            }
//                            +lineLayer("linelayer", GEOJSON_SOURCE_ID) {
//                                lineCap(LineCap.ROUND)
//                                lineJoin(LineJoin.ROUND)
//                                lineOpacity(0.7)
//                                lineWidth(8.0)
//                                lineColor("#888")
//                            }
//                        }
//                        )
//                    )
//
//            if (point != null) {
////                pointAnnotationManager?.let {
////                    it.deleteAll()
////                    val pointAnnotationOptions = PointAnnotationOptions()
////                        .withPoint(point)
////                        .withIconImage(marker)
////
////                    it.create(pointAnnotationOptions)
////                    mapView.getMapboxMap()
////                        .flyTo(CameraOptions.Builder().zoom(16.0).center(point).build())
////                }
//            }
//            NoOpUpdate
//        },
//        modifier = modifier
//    )
//}
    private const val GEOJSON_SOURCE_ID = "line"
    private const val LATITUDE = -122.486052
    private const val LONGITUDE = 37.830348
    private const val ZOOM = 14.0

