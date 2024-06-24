package com.ohanyan.xhike.android.screens.home.aboutus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.android.util.MyApplicationTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun AboutUsScreen(
    navController: NavController,
    aboutUsViewModel: AboutUsViewModel = getViewModel()
) {
    AboutUsScreenUI()
}

@Composable
fun AboutUsScreenUI() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(id = R.string.about_us_body),
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}


@Preview
@Composable
fun AboutUsScreenPreview() {
    MyApplicationTheme {
        AboutUsScreenUI()
    }
}