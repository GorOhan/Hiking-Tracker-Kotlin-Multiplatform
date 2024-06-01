package com.ohanyan.xhike.android.ui.home.trails.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ohanyan.xhike.data.db.HikeDifficulty
import com.ohanyan.xhike.data.db.HikeEntity
import com.ohanyan.xhike.data.db.HikeRate
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddHikeDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (HikeEntity) -> Unit,
    painter: Painter,
    imageDescription: String,
) {
    var currentHike by remember { mutableStateOf(HikeEntity()) }
    var showDiffPicker by remember { mutableStateOf(false) }
    var hikeLength by remember { mutableStateOf("") }
    var showRatePicker by remember { mutableStateOf(false) }

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
                Box(
                    modifier = Modifier
                        .height(400.dp)
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
                                    value = currentHike.hikeName,
                                    onValueChange = {
                                        currentHike = currentHike.copy(hikeName = it)
                                        //  hikeTitle = it
                                    },
                                    label = { Text("Title") }
                                )

                                Text(
                                    text = "Describe your hike",
                                    style = MaterialTheme.typography.titleSmall
                                )

                                OutlinedTextField(
                                    value = currentHike.hikeDescription,
                                    onValueChange = {
                                        currentHike = currentHike.copy(hikeDescription = it)
                                    },
                                    label = { Text("Description") },
                                    minLines = 4,
                                    maxLines = 4,
                                )

                            }

                            Box(
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter),
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
                                        tint = if (currentHike.hikeName.isNotEmpty()) MaterialTheme.colorScheme.secondary
                                        else MaterialTheme.colorScheme.primary
                                    )
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
                                    text = "How long it was?",
                                    style = MaterialTheme.typography.titleSmall
                                )

                                OutlinedTextField(
                                    value =  hikeLength,
                                    onValueChange = {
                                        currentHike =
                                            currentHike.copy(hikeLengthInKm = it.toDouble())
                                        hikeLength = it
                                    },
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Decimal
                                    ),
                                    label = { Text("in km") }
                                )

                                Text(
                                    modifier = Modifier.clickable {
                                        showDiffPicker = true
                                    },
                                    text = "How difficult it was?",
                                    style = MaterialTheme.typography.titleSmall
                                )

                                Box(
                                    modifier = Modifier.clickable {
                                        showDiffPicker = true
                                    },
                                ) {
                                    OutlinedTextField(
                                        readOnly = true,
                                        value = currentHike.hikeDifficulty.value,
                                        onValueChange = {
                                            currentHike = currentHike.copy(
                                                hikeDifficulty = HikeDifficulty.valueOf(it)
                                            )
                                        },
                                        label = { Text("easy, hard...") }
                                    )
                                }

                                DropdownMenu(
                                    expanded = showDiffPicker,
                                    onDismissRequest = { showDiffPicker = false },
                                    modifier = Modifier
                                        .fillMaxWidth()

                                ) {
                                    HikeDifficulty.entries.forEach {
                                        Text(
                                            text = it.name,
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .clickable {
                                                    currentHike =
                                                        currentHike.copy(hikeDifficulty = it)
                                                    showDiffPicker = false
                                                }
                                        )
                                    }
                                }

                                Box {
                                    Text(
                                        modifier = Modifier.clickable {
                                            showRatePicker = true
                                        },
                                        text = "Rate your hike ${currentHike.hikeRating}",
                                        style = MaterialTheme.typography.titleSmall
                                    )

                                    DropdownMenu(
                                        expanded = showRatePicker,
                                        onDismissRequest = { showRatePicker = false },
                                        modifier = Modifier
                                            .fillMaxWidth()

                                    ) {
                                        HikeRate.entries.forEach {
                                            Text(
                                                text = it.name,
                                                modifier = Modifier
                                                    .padding(16.dp)
                                                    .clickable {
                                                        currentHike =
                                                            currentHike.copy(hikeRating = it.value)
                                                        showRatePicker = false
                                                    }
                                            )
                                        }
                                    }
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .align(Alignment.BottomCenter)
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
                                            onConfirmation(currentHike)
//                                            pagerState.animateScrollToPage(
//                                                2,
//                                                animationSpec = tween(
//                                                    durationMillis = 700,
//                                                    delayMillis = 20
//                                                ),
//                                            )
                                        }
                                    }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowForward,
                                        contentDescription = " ",
                                        tint = if (currentHike.hikeDifficulty.value.isNotEmpty()) MaterialTheme.colorScheme.secondary
                                        else MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }

                        2 -> {
                            Text(text = "Third Image")
                        }

                    }
                }
            }
        }
    }
}