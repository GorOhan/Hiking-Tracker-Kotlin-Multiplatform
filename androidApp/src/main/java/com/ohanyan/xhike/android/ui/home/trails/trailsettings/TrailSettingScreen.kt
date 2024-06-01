package com.ohanyan.xhike.android.ui.home.trails.trailsettings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.ohanyan.xhike.android.MyApplicationTheme
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.android.copyUriToInternalStorage
import com.ohanyan.xhike.android.ui.home.trails.component.SettingItem
import com.ohanyan.xhike.data.db.HikeDifficulty
import com.ohanyan.xhike.data.db.HikeRate
import com.ohanyan.xhike.data.db.HikeTime
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrailSettingScreen(
    navController: NavController,
    hikeId: Int,
    trailSettingViewModel: TrailSettingViewModel = getViewModel()
) {
    val currentHike by trailSettingViewModel.trail.collectAsState()
    val onSaveChanges by trailSettingViewModel.onSaveChanges.collectAsState(initial = false)
    var showDiffPicker by remember { mutableStateOf(false) }
    var showRatePicker by remember { mutableStateOf(false) }
    var showHikeTimePicker by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }
    val pickPhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        try {
            if (uri != null) {
                val path = copyUriToInternalStorage(context, uri)
                imageUri = uri
                trailSettingViewModel.onUpdateHike(
                    currentHike.copy(hikeImage = path.toString())
                )
            }
            //    imageUri = Uri.EMPTY
        } catch (e: Exception) {

        }
    }

    LaunchedEffect(Unit) {
        trailSettingViewModel.getHikeById(hikeId)
    }

    LaunchedEffect(key1 = onSaveChanges) {
        if (onSaveChanges) navController.popBackStack()
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = currentHike.hikeName,
            onValueChange = {
                trailSettingViewModel.onUpdateHike(currentHike.copy(hikeName = it))

            },
            label = { Text("Title") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = currentHike.hikeDescription,
            onValueChange = {
                trailSettingViewModel.onUpdateHike(currentHike.copy(hikeDescription = it))
            },
            label = { Text("Description") },
            minLines = 4,
            maxLines = 4,
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = if (currentHike.hikeLengthInKm > 0.0) currentHike.hikeLengthInKm.toString() else "",
            onValueChange = {
                trailSettingViewModel.onUpdateHike(currentHike.copy(hikeLengthInKm = it.toDouble()))
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal
            ),
            label = { Text("in km") }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SettingItem(
                painter = painterResource(id = R.drawable.ic_hard),
                title = currentHike.hikeDifficulty.value,
            ) {
                showDiffPicker = true
            }

            SettingItem(
                painter = painterResource(id = R.drawable.ic_rate),
                title = currentHike.hikeRating.toString(),
            ) {
                showRatePicker = true
            }

            SettingItem(
                painter = painterResource(id = R.drawable.ic_timer),
                title = "${currentHike.hikeTime} h",
            ) {
                showHikeTimePicker = true
            }
        }

        Row(
            modifier = Modifier
                .clickable {
                    pickPhotoLauncher.launch("image/*")
                }
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                    shape = RoundedCornerShape(3.dp)
                )
                .padding(8.dp)
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_gallery),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondary,
            )

            if (imageUri == Uri.EMPTY) {
                Text(
                    text = "upload photo",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(start = 6.dp)
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    alignment = Alignment.Center,

                    modifier = Modifier
                        .padding(16.dp)
                        .width(80.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }

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
                            trailSettingViewModel.onUpdateHike(currentHike.copy(hikeDifficulty = it))
                            showDiffPicker = false
                        }
                )
            }
        }

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
                            trailSettingViewModel.onUpdateHike(currentHike.copy(hikeRating = it.value))
                            showRatePicker = false
                        }
                )
            }
        }

        DropdownMenu(
            expanded = showHikeTimePicker,
            onDismissRequest = { showHikeTimePicker = false },
            modifier = Modifier
                .fillMaxWidth()

        ) {
            HikeTime.entries.forEach {
                Text(
                    text = it.name,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable {
                            trailSettingViewModel.onUpdateHike(currentHike.copy(hikeTime = it.value))
                            showHikeTimePicker = false
                        }
                )
            }
        }

        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                trailSettingViewModel.saveChanges()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(
                text = "Save Changes",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
            )
        }

        Button(
            onClick = {
                trailSettingViewModel.deleteHike()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Text(
                text = "Delete Hike",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
            )
        }
    }
}

@Preview
@Composable
fun TrailSettingsScreenPreview() {
    MyApplicationTheme {
        TrailSettingScreen(
            navController = rememberNavController(),
            hikeId = 1,
            trailSettingViewModel = koinViewModel()
        )
    }
}