package com.okankkl.themovieapp.presentation.screens.content_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.utils.DateUtils

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContentDetailSubHeader(
    modifier: Modifier = Modifier,
    runtime: Int?,
    voteAverage: Double?,
    releaseDate: String?,
    numberOfSeason: Int?,
    numberOfEpisode: Int?,
    lastAirDate: String?
) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 3,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        runtime?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = null,
                    tint = Color(0xFFBBBBBB)
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "$it minutes",
                    color = Color(0xFFBCBCBC),
                    fontSize = 13.sp
                )

            }
        }

        numberOfSeason?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_tv_series_season),
                    contentDescription = null,
                    tint = Color(0xFFBBBBBB)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp),
                    text = "$it season",
                    color = Color(0xFFBCBCBC),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 13.sp,
                        color = Color(0xFFBCBCBC)
                    )
                )
            }
        }

        voteAverage?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.filled_star),
                    contentDescription = null,
                    tint = Color(0xFFBBBBBB),
                )
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp),
                    text = "${String.format("%.1f", it)} (TMDB)",
                    color = Color(0xFFBCBCBC)
                )
            }
        }

        releaseDate?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = Color(0xFFBBBBBB),
                    modifier = Modifier
                )
                Text(
                    text = DateUtils.convertDate(it),
                    color = Color(0xFFBCBCBC),
                    modifier = Modifier
                        .padding(start = 5.dp)
                )

                if (!lastAirDate.isNullOrEmpty()) {
                    Text(
                        text = " - " + DateUtils.convertDate(lastAirDate),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 13.sp,
                            color = Color(0xFFBCBCBC)
                        )
                    )
                }
            }
        }
    }
}

