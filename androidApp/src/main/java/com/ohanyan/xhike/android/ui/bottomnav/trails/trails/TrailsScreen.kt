package com.ohanyan.xhike.android.ui.bottomnav.trails.trails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.navigation.NavController
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.android.ui.bottomnav.trails.component.AddHikeDialog
import com.ohanyan.xhike.android.ui.bottomnav.trails.component.HikeItem
import org.koin.androidx.compose.getViewModel

@Composable
fun TrailsScreen(
    navController: NavController,
    trailsViewModel: TrailsViewModel = getViewModel()
) {
    var showAddHikeDialog by remember { mutableStateOf(false) }
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
                text = "There is no any trails yet.",
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
                    HikeItem(
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

        FloatingActionButton(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomEnd),
            onClick = {
                showAddHikeDialog = true
            },
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background,
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }

        if (showAddHikeDialog) {
            AddHikeDialog(
                onDismissRequest = {
                    showAddHikeDialog = false
                },
                onConfirmation = {
                    showAddHikeDialog = false
                    trailsViewModel.addHike(it)
                },
                painter = painterResource(id = R.drawable.ic_hiking),
                imageDescription = "Image description",
            )
        }
    }
}
