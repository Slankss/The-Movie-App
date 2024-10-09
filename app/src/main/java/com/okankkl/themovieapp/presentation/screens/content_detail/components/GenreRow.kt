package com.okankkl.themovieapp.presentation.screens.content_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okankkl.themovieapp.data.model.dto.Genre

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreRowUI(
    genres: List<Genre>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = "Genres",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp,
                color = Color(0xFFFFFFFF)
            )
        )
        FlowRow(
            maxItemsInEachRow = 3,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            genres.forEach { genre ->
                GenresRowItem(
                    genre = genre
                )
            }
        }
    }
}

@Composable
fun GenresRowItem(
    modifier: Modifier = Modifier,
    genre: Genre
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(15.dp)
            )
            .background(
                color = Color(0xDFAF0CA)
            )
    ) {
        genre.name?.let {
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .align(Alignment.Center),
                text = it,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 12.sp,
                    color = Color.White
                )
            )
        }
    }
}