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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.navigation.NavController
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.android.screens.home.trails.TrailsScreenRoute
import com.ohanyan.xhike.android.screens.home.trails.component.HikeCard
import com.ohanyan.xhike.android.util.MyApplicationTheme
import com.ohanyan.xhike.data.db.HikeEntity
import org.koin.androidx.compose.getViewModel

@Composable
fun TrailsScreen(
    navController: NavController,
    trailsViewModel: TrailsViewModel = getViewModel()
) {
    val hikes by trailsViewModel.hikes.collectAsState()

    LifecycleStartEffect(trailsViewModel) {
        trailsViewModel.getHikes()
        onStopOrDispose { }
    }
    TrailsScreenUI(
        hikes = hikes,
        onNavigateClick = { navController.navigate(it) },
        onFavouriteClick = { trailsViewModel.onFavouriteClick(it) },
    )
}

@Composable
fun TrailsScreenUI(
    hikes: List<HikeEntity> = emptyList(),
    onNavigateClick: (String) -> Unit = {},
    onFavouriteClick: (HikeEntity) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (hikes.isEmpty()) {
            Text(
                modifier = Modifier.clickable {
                    onNavigateClick(TrailsScreenRoute.MapScreen.route)
                },
                text = stringResource(id = R.string.there_are_no_trails),
                color = MaterialTheme.colorScheme.tertiary,
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
                            onNavigateClick("${TrailsScreenRoute.SingleTrailScreen.route}/${hikes[index].hikeId}")
                        }, onFavouriteClick = {
                            onFavouriteClick(hikes[index])
                        },
                        onSettingsClick = {
                            onNavigateClick("${TrailsScreenRoute.TrailSettingsScreen.route}/${hikes[index].hikeId}")
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun BaseDialogPreview() {
    MyApplicationTheme {
        TrailsScreenUI()
    }
}