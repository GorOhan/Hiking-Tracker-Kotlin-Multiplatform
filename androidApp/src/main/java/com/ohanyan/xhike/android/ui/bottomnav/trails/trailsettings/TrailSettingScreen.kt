package com.ohanyan.xhike.android.ui.bottomnav.trails.trailsettings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ohanyan.xhike.data.db.HikeDifficulty
import com.ohanyan.xhike.data.db.HikeRate
import org.koin.androidx.compose.getViewModel

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
            .background(color = MaterialTheme.colorScheme.background)
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

        OutlinedTextField(

            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDiffPicker = true
                },
            readOnly = true,
            value = currentHike.hikeDifficulty.value,
            onValueChange = {

            },
            label = { Text("easy, hard...") }
        )
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
                            trailSettingViewModel.onUpdateHike(currentHike.copy(hikeRating = it.value))
                            showRatePicker = false
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
    }
}

