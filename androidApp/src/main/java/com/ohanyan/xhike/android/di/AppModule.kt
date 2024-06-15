package com.ohanyan.xhike.android.di

import com.ohanyan.xhike.android.screens.home.aboutus.AboutUsViewModel
import com.ohanyan.xhike.android.screens.home.starthiking.StartHikingViewModel
import com.ohanyan.xhike.android.screens.home.trails.followtrail.FollowTrailViewModel
import com.ohanyan.xhike.android.screens.home.trails.trails.TrailsViewModel
import com.ohanyan.xhike.android.screens.home.trails.singletrail.SingleTrailViewModel
import com.ohanyan.xhike.android.screens.home.trails.trailsettings.TrailSettingViewModel
import com.ohanyan.xhike.android.screens.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::TrailsViewModel)
    viewModelOf(::StartHikingViewModel)
    viewModelOf(::AboutUsViewModel)
    viewModelOf(::SingleTrailViewModel)
    viewModelOf(::TrailSettingViewModel)
    viewModelOf(::FollowTrailViewModel)
}