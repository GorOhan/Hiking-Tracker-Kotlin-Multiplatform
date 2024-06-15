package com.ohanyan.xhike.android

import android.app.Application
import com.mapbox.common.MapboxOptions
import com.ohanyan.xhike.android.di.appModule
import com.ohanyan.xhike.di.platformModule
import com.ohanyan.xhike.di.repositoryModule
import com.ohanyan.xhike.di.useCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HikingApp : Application() {

    override fun onCreate() {
        super.onCreate()

        MapboxOptions.accessToken = applicationContext.getString(R.string.mapbox_key)
        startKoin {
            androidContext(this@HikingApp)
            modules(appModule, useCasesModule, repositoryModule, platformModule)
        }
    }
}