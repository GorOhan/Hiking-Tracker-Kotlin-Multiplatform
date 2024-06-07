package com.ohanyan.xhike.android.service

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.ohanyan.xhike.android.MainActivity
import com.ohanyan.xhike.android.R


const val CHANNEL_ID = "CHANEL_ID_HIKING"

class GetLocationService : Service() {

    private var channel: NotificationChannel = NotificationChannel(CHANNEL_ID, "HikingChannel", NotificationManager.IMPORTANCE_HIGH)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val openActiveTabIntent = Intent(this, MainActivity::class.java)
        openActiveTabIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        openActiveTabIntent.putExtra("action", "activeTab")
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            openActiveTabIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        notificationManager.createNotificationChannel(channel)

        getLocation(applicationContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                101,
                getNotification(applicationContext, pendingIntent),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
            )
        } else {
            startForeground(101, getNotification(applicationContext, pendingIntent))
        }
        notificationManager.notify(101, getNotification(applicationContext, pendingIntent))
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}


private fun getLocation(context: Context) {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    val locationRequest = LocationRequest.Builder(100L)
        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
        .setMinUpdateIntervalMillis(1000L)
        .build()

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        return

    }
    fusedLocationClient.requestLocationUpdates(
        locationRequest,
        object : LocationCallback(
        ) {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {

            }
        },
        null /* Looper */
    )

}

private fun getNotification(context: Context, intent: PendingIntent) = Notification
    .Builder(context, CHANNEL_ID)
    .setSmallIcon(R.drawable.ic_compass)
    .setContentTitle("this is title")
    .setContentText("this is context")
    .setContentIntent(intent)
    .setOngoing(true)
    .build()
