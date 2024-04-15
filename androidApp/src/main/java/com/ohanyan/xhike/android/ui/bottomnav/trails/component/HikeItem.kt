package com.ohanyan.xhike.android.ui.bottomnav.trails.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ohanyan.xhike.data.db.HikeEntity


@Composable
fun HikeItem(
     hikeEntity: HikeEntity
) {
    val painter =
        rememberAsyncImagePainter(model = "https://images.unsplash.com/photo-1628373383885-4be0bc0172fa?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1301&q=80")

    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { },
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = MaterialTheme.colorScheme.onSecondary)
                    .padding(horizontal = 8.dp),
                text = hikeEntity.hikeDifficulty.value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary,
                textAlign = TextAlign.Center
            )

            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = " ",
                tint = MaterialTheme.colorScheme.tertiary,
            )
        }
        Column(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = hikeEntity.hikeName,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.tertiary,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = hikeEntity.hikeDescription,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.background,
            )
        }
    }
}
