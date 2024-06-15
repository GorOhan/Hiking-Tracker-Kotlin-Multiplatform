package com.ohanyan.xhike.android.screens.home.trails.trails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.navigation.NavController
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.android.screens.home.trails.TrailsScreenRoute
import com.ohanyan.xhike.android.screens.home.trails.component.HikeCard
import org.koin.androidx.compose.getViewModel

@Composable
fun TrailsScreen(
    navController: NavController,
    trailsViewModel: TrailsViewModel = getViewModel()
) {
    val hikes by trailsViewModel.hikes.collectAsState()

    LifecycleStartEffect(trailsViewModel) {
        trailsViewModel.getHikes()
        onStopOrDispose {  }
    }

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (hikes.isEmpty()) {
            Text(
                modifier = Modifier.clickable {
                    navController.navigate(TrailsScreenRoute.MapScreen.route)
                },
                text = stringResource(id = R.string.there_is_no_trails),
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.titleMedium
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
            ) {
                items(count = hikes.size) { index ->
                    HikeCard(
                        hikeEntity = (hikes[index]),
                        onItemClick = {
                            navController.navigate("${TrailsScreenRoute.SingleTrailScreen.route}/${hikes[index].hikeId}")
                        }, onFavouriteClick = {
                            trailsViewModel.onFavouriteClick(hikes[index])
                        },
                        onSettingsClick = {
                            navController.navigate("${TrailsScreenRoute.TrailSettingsScreen.route}/${hikes[index].hikeId}")
                        }
                    )
                }
            }
        }
    }
}
