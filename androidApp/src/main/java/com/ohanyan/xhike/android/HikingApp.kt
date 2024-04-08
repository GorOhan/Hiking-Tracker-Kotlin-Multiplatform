package com.ohanyan.xhike.android

import android.app.Application
import com.ohanyan.xhike.domainModule
import com.ohanyan.xhike.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.osmdroid.config.Configuration


class HikingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        Configuration.getInstance().load(applicationContext, getSharedPreferences("osmdroid", 0))

        startKoin {
            androidContext(this@HikingApp)
            modules(appModule, domainModule, networkModule)
        }
    }
}