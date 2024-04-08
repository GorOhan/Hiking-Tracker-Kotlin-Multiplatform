package com.ohanyan.xhike.android.ui.splash

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ohanyan.xhike.android.R
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel
import kotlin.math.roundToInt

@Composable
fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel = getViewModel()

) {
    //val toLoginScreen = splashViewModel.toLoginScreen.collectAsState(initial = false)
    val testString = splashViewModel.testString.collectAsState(initial = "")

    var leftStep by remember { mutableStateOf(0) }
    var rightStep by remember { mutableStateOf(0) }

    var pxToMove = with(LocalDensity.current) {
        -100.dp.toPx().roundToInt()
    }
    var pxToMoveRight = with(LocalDensity.current) {
        -40.dp.toPx().roundToInt()
    }
    val pxToMoveY = with(LocalDensity.current) {
        0.dp.toPx().roundToInt() + leftStep.dp.toPx().roundToInt()
    }

    val pxToMoveYRight = with(LocalDensity.current) {
        -100.dp.toPx().roundToInt() + rightStep.dp.toPx().roundToInt()
    }
    val offsetLeft by animateIntOffsetAsState(
        targetValue = IntOffset(pxToMove, pxToMoveY),
        animationSpec = tween(300),
        label = "offset",
        finishedListener = {
            rightStep -= 200
        }
    )

    val offsetRight by animateIntOffsetAsState(
        targetValue = IntOffset(pxToMoveRight, pxToMoveYRight),
        animationSpec = tween(300),
        label = "offset",
        finishedListener = {
            leftStep -= 200
        }
    )


    LaunchedEffect(Unit) {
        delay(1000)
        leftStep -= 200
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
        Image(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .rotate(30f)
                .offset {
                    offsetLeft
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    // step -= 100
                }
                .height(100.dp),
            painter = painterResource(id = R.drawable.left_foot),
            contentDescription = "")

        Image(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .rotate(30f)
                .offset {
                    offsetRight
                }
                .height(100.dp),
            painter = painterResource(id = R.drawable.right_foot),
            contentDescription = "")

    }
}
