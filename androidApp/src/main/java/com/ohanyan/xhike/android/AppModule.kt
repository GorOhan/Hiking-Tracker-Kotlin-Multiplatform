package com.ohanyan.xhike.android

import com.ohanyan.xhike.android.ui.home.HomeViewModel
import com.ohanyan.xhike.android.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}