package com.ohanyan.xhike.android

import com.ohanyan.xhike.android.ui.main.aboutus.AboutUsViewModel
import com.ohanyan.xhike.android.ui.main.starthiking.StartHikingViewModel
import com.ohanyan.xhike.android.ui.main.trails.followtrail.FollowTrailViewModel
import com.ohanyan.xhike.android.ui.main.trails.trails.TrailsViewModel
import com.ohanyan.xhike.android.ui.main.trails.singletrail.SingleTrailViewModel
import com.ohanyan.xhike.android.ui.main.trails.trailsettings.TrailSettingViewModel
import com.ohanyan.xhike.android.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { TrailsViewModel(get(), get(), get()) }
    viewModel { StartHikingViewModel(get()) }
    viewModel { AboutUsViewModel() }
    viewModel { SingleTrailViewModel(get()) }
    viewModel { TrailSettingViewModel(get(), get(), get()) }
    viewModel { FollowTrailViewModel(get()) }
}