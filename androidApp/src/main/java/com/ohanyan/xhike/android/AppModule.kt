package com.ohanyan.xhike.android

import com.ohanyan.xhike.android.ui.bottomnav.aboutus.AboutUsViewModel
import com.ohanyan.xhike.android.ui.bottomnav.starthiking.StartHikingViewModel
import com.ohanyan.xhike.android.ui.bottomnav.trails.TrailsViewModel
import com.ohanyan.xhike.android.ui.home.HomeViewModel
import com.ohanyan.xhike.android.ui.splash.SplashViewModel
import com.ohanyan.xhike.data.db.Database
import com.ohanyan.xhike.data.db.DatabaseDriverFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { TrailsViewModel(get(),get()) }
    viewModel { StartHikingViewModel() }
    viewModel { AboutUsViewModel() }
     single { Database(get()) }
     single { DatabaseDriverFactory(get()) }
}