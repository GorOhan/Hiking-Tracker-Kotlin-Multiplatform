package com.ohanyan.xhike.android.ui.bottomnav.trails

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddHikeDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    painter: Painter,
    imageDescription: String,
) {
    var hikeTitle by remember { mutableStateOf("") }
    var hikeDescription by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { 3 }

    Dialog(
        onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth(),
                state = pagerState
            ) {

                when (it) {
                    0 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Name your hike",
                                style = MaterialTheme.typography.titleSmall
                            )

                            OutlinedTextField(
                                value = hikeTitle,
                                onValueChange = { hikeTitle = it },
                                label = { Text("Title") }
                            )
                            Box(
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .fillMaxWidth(),
                            ) {
                                IconButton(
                                    modifier = Modifier.align(Alignment.BottomEnd),
                                    onClick = {
                                        scope.launch {
                                            pagerState.animateScrollToPage(
                                                1,
                                                animationSpec = tween(
                                                    durationMillis = 700,
                                                    delayMillis = 20
                                                ),
                                            )
                                        }
                                    }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowForward,
                                        contentDescription = " ",
                                        tint = if (hikeTitle.isNotEmpty()) MaterialTheme.colorScheme.secondary
                                        else MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }

                    }

                    1 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Describe your hike",
                                style = MaterialTheme.typography.titleSmall
                            )

                            OutlinedTextField(
                                value = hikeDescription,
                                onValueChange = { hikeDescription = it },
                                label = { Text("Description") }
                            )
                            Row(
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            pagerState.animateScrollToPage(
                                                0,
                                                animationSpec = tween(
                                                    durationMillis = 700,
                                                    delayMillis = 20
                                                ),
                                            )
                                        }
                                    }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = " ",
                                        tint = MaterialTheme.colorScheme.secondary
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            pagerState.animateScrollToPage(
                                                2,
                                                animationSpec = tween(
                                                    durationMillis = 700,
                                                    delayMillis = 20
                                                ),
                                            )
                                        }
                                    }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowForward,
                                        contentDescription = " ",
                                        tint = if (hikeDescription.isNotEmpty()) MaterialTheme.colorScheme.secondary
                                        else MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }

                    2 -> {
                        Text(text = "Third Image")
                    }

                }
//                Column(
//                    modifier = Modifier,
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        horizontalArrangement = Arrangement.Center,
//                    ) {
//                        TextButton(
//                            onClick = { onDismissRequest() },
//                            modifier = Modifier.padding(8.dp),
//                        ) {
//                            Text("Dismiss")
//                        }
//                        TextButton(
//                            onClick = { onConfirmation() },
//                            modifier = Modifier.padding(8.dp),
//                        ) {
//                            Text("Confirm")
//                        }
//                    }
//                }
            }
        }
    }
}