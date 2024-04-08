package com.ohanyan.xhike.android.ui.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.android.ui.navigation.Screen
import org.koin.androidx.compose.getViewModel

@Composable
fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel = getViewModel()

) {
    val footsRotate = 30f
    val animatedFootValues = List(5) { remember { Pair(Animatable(0f), Animatable(0f)) } }

    LaunchedEffect(Unit) {
        animatedFootValues.forEachIndexed { index, animate ->
            animate.first.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 150, delayMillis = index * 100),

            )
            animate.second.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 150, delayMillis = index * 100),

            )
        }
        navController.navigate(Screen.HomeScreen.route)
    }


    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Hiking",
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.titleLarge
        )
        animatedFootValues.forEachIndexed { index, animatable ->

            Image(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .rotate(footsRotate)
                    .alpha(animatable.first.value)
                    .offset(-100.dp, 60.dp - 150.dp * index)
                    .height(90.dp),
                painter = painterResource(id = R.drawable.left_foot),
                contentDescription = ""
            )

            Image(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .rotate(footsRotate)
                    .alpha(animatable.second.value)
                    .offset(-40.dp, -40.dp - 150.dp * index)
                    .height(90.dp),
                painter = painterResource(id = R.drawable.right_foot),
                contentDescription = ""
            )
        }
    }
}
