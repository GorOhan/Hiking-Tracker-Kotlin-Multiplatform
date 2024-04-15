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
import com.ohanyan.xhike.data.db.HikeDifficulty
import com.ohanyan.xhike.data.db.HikeEntity


@Composable
fun HikeItem(
    hikeEntity: HikeEntity
) {
    val painter =
        rememberAsyncImagePainter(model = "https://i0.wp.com/images-prod.healthline.com/hlcmsresource/images/topic_centers/2019-8/couple-hiking-mountain-climbing-1296x728-header.jpg?w=1155&h=1528")

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
                    .background(
                        color =
                        when (hikeEntity.hikeDifficulty) {
                            HikeDifficulty.EASY -> MaterialTheme.colorScheme.onSecondary
                            HikeDifficulty.MEDIUM -> MaterialTheme.colorScheme.secondary
                            HikeDifficulty.HARD -> MaterialTheme.colorScheme.error
                        }
                    )
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
