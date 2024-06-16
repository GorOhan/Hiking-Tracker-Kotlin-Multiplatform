package com.ohanyan.xhike.android.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.Priority
import com.ohanyan.xhike.android.util.hasLocationPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

interface LocationClient {
    fun getLocationUpdates(interval: Long): Flow<Location>
    class LocationException(message: String) : Exception()
}

class BackgroundLocationClient(
    private val context: Context,
    private val client: FusedLocationProviderClient,
) : LocationClient {
    override fun getLocationUpdates(interval: Long): Flow<Location> = callbackFlow {
        if (!context.hasLocationPermission()) {
            throw LocationClient.LocationException("Missing Location Permission")
        }

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isGpsEnabled) {
            throw LocationClient.LocationException("GPS  is disabled")
        }

        val locationRequest = com.google.android.gms.location.LocationRequest.Builder(2000L)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setMinUpdateIntervalMillis(2000L)
            .build()

        val locationCallback = object : LocationCallback(
        ) {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                locationResult.locations.lastOrNull()?.let {
                    launch { trySend(it) }
                }
            }
        }
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            client.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null /* Looper */
            )
        }

        awaitClose {
            client.removeLocationUpdates(locationCallback)
        }
    }
}