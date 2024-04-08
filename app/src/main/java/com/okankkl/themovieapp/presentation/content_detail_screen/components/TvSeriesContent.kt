package com.okankkl.themovieapp.presentation.content_detail_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.domain.model.TvSeries
import com.okankkl.themovieapp.domain.extensions.convertDate

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TvSeriesContent(tvSeries: TvSeries){

    val subTitleStyle = MaterialTheme.typography.bodyLarge.copy(
        fontSize = 16.sp,
        color = Color(0xFFFFFFFF)
    )

    Column(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {


        FlowRow(
            maxItemsInEachRow = 3,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_tv_series_season),
                    contentDescription = null,
                    tint = Color(0xFFBBBBBB)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp),
                    text = "${tvSeries.numberOfSeasons} season",
                    color = Color(0xFFBCBCBC),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 13.sp,
                        color = Color(0xFFBCBCBC)
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_tv_series_season),
                    contentDescription = null,
                    tint = Color(0xFFBBBBBB)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp),
                    text = "${tvSeries.numberOfEpisodes} episodes",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 13.sp,
                        color = Color(0xFFBCBCBC)
                    ),
                    color = Color(0xFFBCBCBC)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = R.drawable.filled_star),
                    contentDescription = null,
                    tint = Color(0xFFBBBBBB)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp),
                    text = "${String.format("%.1f",tvSeries.voteAverage)} (TMDB)",
                    color = Color(0xFFBCBCBC),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 13.sp,
                        color = Color(0xFFBCBCBC)
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "date",
                    tint = Color(0xFFBBBBBB),
                    modifier = Modifier
                        .padding(end = 5.dp)
                )
                if(tvSeries.releaseDate.isNotEmpty()){
                    Text(
                        text = convertDate(tvSeries.releaseDate),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 13.sp,
                            color = Color(0xFFBCBCBC)
                        )
                    )
                }
                if(tvSeries.lastAirDate.isNotEmpty()){
                    Text(
                        text = " - "+ convertDate(tvSeries.lastAirDate),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 13.sp,
                            color = Color(0xFFBCBCBC)
                        )
                    )
                }
            }
        }


        Divider(
            modifier = Modifier
                .background(color = Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Genre",
            style = subTitleStyle,
            modifier = Modifier
        )

        FlowRow(
            maxItemsInEachRow = 3,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
        ) {
            tvSeries.genres.forEach { genre ->
                GenreBox(
                    modifier = Modifier
                        .padding(end = 15.dp),
                    genreName = genre.name
                )
            }
        }

        Divider(
            modifier = Modifier
                .background(color = Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )

        if(tvSeries.createdBy.isNotEmpty()){
            CreditRow(createdtBy = tvSeries.createdBy)
            Divider(
                modifier = Modifier
                    .background(color = Color.White)
                    .height(1.dp)
                    .fillMaxWidth()
            )
        }

        if(tvSeries.overview.isNotEmpty()){
            Text(
                text = "Overview",
                style = subTitleStyle,
                modifier = Modifier
                    .padding(bottom= 10.dp)
            )

            Text(
                text = tvSeries.overview,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = Color(0xFFBCBCBC),
                    textAlign = TextAlign.Justify
                )
            )
        }
    }
}