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
import com.okankkl.themovieapp.domain.model.Movie
import com.okankkl.themovieapp.domain.extensions.convertDate

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieContent(movie: Movie){
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
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = null,
                    tint = Color(0xFFBBBBBB)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp),
                    text = "${movie.runtime} minutes",
                    color = Color(0xFFBCBCBC)
                )
            }
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
                    text = "${String.format("%.1f",movie.voteAverage)} (TMDB)",
                    color = Color(0xFFBCBCBC)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = Color(0xFFBBBBBB),
                    modifier = Modifier
                )
                Text(
                    text = convertDate(movie.releaseDate),
                    color = Color(0xFFBCBCBC),
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }
        }

        Divider(
            modifier = Modifier
                .background(color = Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Genres",
            style = subTitleStyle
        )

        FlowRow(
            maxItemsInEachRow = 3,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            movie.genres.forEach { genre ->
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

        Text(
            text = "Overview",
            style = subTitleStyle,
            modifier = Modifier
        )
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Color(0xFFBCBCBC),
                textAlign = TextAlign.Justify
            )
        )


    }
}