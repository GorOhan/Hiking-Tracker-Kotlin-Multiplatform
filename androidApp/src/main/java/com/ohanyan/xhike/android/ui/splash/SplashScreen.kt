package com.ohanyan.xhike.android.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel = getViewModel()

) {
    //val toLoginScreen = splashViewModel.toLoginScreen.collectAsState(initial = false)
    val testString = splashViewModel.testString.collectAsState(initial = "")

    Box(
        modifier = Modifier
            .background(color = darkColorScheme().primary)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
            Text(text = testString.value)

    }
}
