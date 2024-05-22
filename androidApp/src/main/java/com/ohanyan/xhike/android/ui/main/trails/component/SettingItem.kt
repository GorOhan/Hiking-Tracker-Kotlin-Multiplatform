package com.ohanyan.xhike.android.ui.main.trails.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun SettingItem(
    painter: Painter,
    title: String,
    onItemClick:()->Unit,
){
    Row(
        modifier = Modifier
            .clickable {
                onItemClick.invoke()
            }
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                shape = RoundedCornerShape(3.dp)
            )
            .padding(8.dp)
    ) {

        Icon(
            painter = painter,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.secondary,
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = 6.dp)
        )

    }
}