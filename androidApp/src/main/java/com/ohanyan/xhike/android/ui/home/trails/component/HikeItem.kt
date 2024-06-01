package com.ohanyan.xhike.android.ui.home.trails.component

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ohanyan.xhike.android.MyApplicationTheme
import com.ohanyan.xhike.android.R
import com.ohanyan.xhike.data.db.HikeDifficulty
import com.ohanyan.xhike.data.db.HikeEntity

@Composable
fun HikeItem(
    hikeEntity: HikeEntity,
    onItemClick: (HikeEntity) -> Unit,
    onFavouriteClick: (HikeEntity) -> Unit,
    onSettingsClick: (HikeEntity) -> Unit
) {
    val defaultImage = "https://i0.wp.com/images-prod.healthline.com/hlcmsresource/images/topic_centers/2019-8/couple-hiking-mountain-climbing-1296x728-header.jpg?w=1155&h=1528"
    val painter = rememberAsyncImagePainter(hikeEntity.hikeImage.ifEmpty { defaultImage })

    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onItemClick(hikeEntity)
            },
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

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        onSettingsClick(hikeEntity)
                    },
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = " ",
                    tint = MaterialTheme.colorScheme.tertiary,
                )

                Icon(
                    modifier = Modifier.clickable {
                        onFavouriteClick(hikeEntity)
                    },
                    imageVector = if (hikeEntity.hikeIsFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = " ",
                    tint = MaterialTheme.colorScheme.tertiary,
                )
            }
        }

        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
                Column(
                    modifier = Modifier.fillMaxWidth(.3f)
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
                Row(
                ) {
                    Row(modifier = Modifier.padding(4.dp)) {
                        Text(
                            text = "${if(hikeEntity.hikeTime > 0) hikeEntity.hikeTime else "-"} h",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                        Image(
                            modifier = Modifier
                                .size(16.dp),
                            painter = painterResource(id = R.drawable.ic_timer),
                            contentDescription = " ",
                        )
                    }
                    Image(
                        modifier = Modifier,
                        painter = painterResource(id = R.drawable.ic_slash),
                        contentDescription = " ",
                        )
                    Row {
                        Text(
                            text = "${if (hikeEntity.hikeLengthInKm>0) hikeEntity.hikeLengthInKm else "-"} km",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                        Image(
                            modifier = Modifier
                                .size(16.dp),
                            painter = painterResource(id = R.drawable.ic_walk),
                            contentDescription = " ",
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                ) {
                    repeat(hikeEntity.hikeRating.toInt()) {
                        Image(
                            modifier = Modifier
                                .size(16.dp),
                            painter = painterResource(id = R.drawable.ic_star_fill),
                            contentDescription = " ",
                        )
                    }

                    repeat(5-hikeEntity.hikeRating.toInt()) {
                        Image(
                            modifier = Modifier
                                .size(16.dp),
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = " ",
                        )
                    }
                }
        }
    }
}

@Preview
@Composable
fun HikeItemPreview() {
    MyApplicationTheme {
        HikeItem(
            hikeEntity = HikeEntity(),
            onItemClick = {},
            onFavouriteClick = {},
            onSettingsClick = {}
        )
    }
}