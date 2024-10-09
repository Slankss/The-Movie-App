package com.okankkl.themovieapp.presentation.screens.news.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okankkl.themovieapp.data.model.dto.Movie
import com.okankkl.themovieapp.presentation.components.ContentPoster
import com.okankkl.themovieapp.ui.theme.OceanPalet4
import java.time.LocalDate

@Composable
fun NewMovieContent(
    movieDetail : Movie,
    onClick : ()->Unit
) {
    val date = LocalDate.parse(movieDetail.releaseDate)
    val day = date.dayOfMonth
    var month = date.month.name
    month = month.lowercase().replaceFirstChar { it.uppercase() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
            ) {
                Divider(
                    modifier = Modifier
                        .height(225.dp)
                        .width(1.dp)
                        .align(Alignment.TopCenter)
                        .background(Color(0xB3FFFFFF))
                )
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(12.dp)
                        .align(Alignment.Center)
                        .background(
                            color = OceanPalet4,
                            shape = CircleShape
                        )
                )
            }
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    ){
                        append(day.toString())
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = Color(0xB3FFFFFF)
                        )
                    ){
                        append(" $month")
                    }
                },
                modifier = Modifier
                    .padding(end = 10.dp)
            )
        }

        ContentPoster(
            posterPath = movieDetail.posterPath!!,
            modifier = Modifier
                .weight(1f)
                .height(225.dp)
                .padding(bottom = 25.dp),
            onClick = {
                onClick()
            }
        )
    }
}
