package com.ohanyan.xhike.android

import android.app.Application
import com.mapbox.common.MapboxOptions
import com.ohanyan.xhike.di.platformModule
import com.ohanyan.xhike.di.repositoryModule
import com.ohanyan.xhike.di.useCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HikingApp : Application() {

    override fun onCreate() {
        super.onCreate()

        MapboxOptions.accessToken = "pk.eyJ1IjoiZ3JvYW4iLCJhIjoiY2x1dGdoa2ljMGx5ZDJyb2lheHptb243YyJ9.VFWaDAoyH7IgpIq0w1_12Q"
        startKoin {
            androidContext(this@HikingApp)
            modules(appModule, useCasesModule, repositoryModule, platformModule)
        }
    }
}