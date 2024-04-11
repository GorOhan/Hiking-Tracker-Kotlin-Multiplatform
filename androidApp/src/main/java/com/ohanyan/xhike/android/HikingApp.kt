package com.ohanyan.xhike.android

import android.app.Application
import com.mapbox.common.MapboxOptions
import com.ohanyan.xhike.domainModule
import com.ohanyan.xhike.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.osmdroid.config.Configuration


class HikingApp : Application() {
    override fun onCreate() {
        super.onCreate()

        MapboxOptions.accessToken = "pk.eyJ1IjoiZ3JvYW4iLCJhIjoiY2x1dGdoa2ljMGx5ZDJyb2lheHptb243YyJ9.VFWaDAoyH7IgpIq0w1_12Q"
        Configuration.getInstance().load(applicationContext, getSharedPreferences("osmdroid", 0))
    //  Configuration.getInstance().setUserAgentValue("R661W5Z8ssl908BRAnmCSImEn2ZZ7ugfqhO1BEzHQWU")

        Configuration.getInstance().setUserAgentValue("8yqxkMfyLqYKZ7KAdgGwWkZgd6vhOjSj4mOWrF_5Po")

        startKoin {
            androidContext(this@HikingApp)
            modules(appModule, domainModule, networkModule)
        }
    }
}