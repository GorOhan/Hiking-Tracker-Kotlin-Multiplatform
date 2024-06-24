package com.ohanyan.xhike.android.screens.home.trails.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ohanyan.xhike.android.util.MyApplicationTheme

@Composable
fun BaseDialog(
    title: String = "are you sure?",
    positiveButtonText: String = "confirm",
    onConfirmClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    AlertDialog(
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                style = MaterialTheme.typography.titleSmall
            )
        },
        confirmButton = {
            Button(
                modifier = Modifier
                    .clip(RoundedCornerShape(0.4f)),
                enabled = true,
                onClick = {
                    onConfirmClick.invoke()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = positiveButtonText,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        onDismissRequest = {
            onDismissRequest.invoke()
        },
    )
}

@Preview
@Composable
fun BaseDialogPreview() {
    MyApplicationTheme {
        BaseDialog()
    }
}
