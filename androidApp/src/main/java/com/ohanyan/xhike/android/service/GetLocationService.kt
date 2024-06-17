package com.ohanyan.xhike.android.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import com.google.android.gms.location.LocationServices
import com.ohanyan.xhike.CurrentHike
import com.ohanyan.xhike.android.MainActivity
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.data.db.PointEntity
import com.ohanyan.xhike.domain.usecases.InsertCurrentHikeUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject


const val CHANNEL_ID = "CHANEL_ID_HIKING"

class GetLocationService : Service() {

    private val insertCurrentHikeUseCase: InsertCurrentHikeUseCase by inject()
    private var currentPoints = mutableListOf<PointEntity>()
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    private var channel: NotificationChannel =
        NotificationChannel(CHANNEL_ID, "HikingChannel", NotificationManager.IMPORTANCE_HIGH)


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)

        val locationClient = BackgroundLocationClient(
            applicationContext,
            fusedLocationClient
        )
            locationClient.getLocationUpdates(200)
                .onEach {
                    currentPoints.add(
                        PointEntity(
                            pointLocationLat = it.latitude,
                            pointLocationLot = it.longitude
                        )
                    )
                    insertCurrentHikeUseCase.invoke(
                        CurrentHike(
                            hikeId = 1,
                            hikePoints = currentPoints,
                        )
                    )
                }.launchIn(serviceScope)


        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val openActiveTabIntent = Intent(this, MainActivity::class.java)
        openActiveTabIntent.addFlags(Intent.FLAG_FROM_BACKGROUND)
        openActiveTabIntent.putExtra("action", "activeTab")
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            openActiveTabIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        notificationManager.createNotificationChannel(channel)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                101,
                getNotification(pendingIntent),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
            )
        } else {
            startForeground(101, getNotification(pendingIntent))
        }
        notificationManager.notify(101, getNotification(pendingIntent))
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    private fun getNotification(intent: PendingIntent) = Notification
        .Builder(applicationContext, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_compass)
        .setContentTitle("Trail recording has started!")
        .setContentText("The app will record your trail during the hike even when the app is in the background.")
        .setContentIntent(intent)
        .setOngoing(true)
        .build()
}
