package com.okankkl.themovieapp.presentation.screens.content_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okankkl.themovieapp.data.model.dto.Cast
import com.okankkl.themovieapp.data.model.dto.Credits
import com.okankkl.themovieapp.presentation.components.ContentPoster

@Composable
fun ContentCast(
    modifier: Modifier = Modifier,
    credits: Credits,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            modifier = modifier
                .padding(start = 15.dp, bottom = 10.dp),
            text = "Cast",
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp
            )
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            credits.cast?.let { cast ->
                items(cast) { person ->
                   ContentCastItem(
                       person = person,
                       onClick = { id ->
                           onClick(id)
                       }
                   )
                }
            }

        }
    }

}

@Composable
fun ContentCastItem(
    person: Cast,
    onClick: (Int) -> Unit
){
    Column(
        modifier = Modifier
            .padding(start = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContentPoster(
            modifier = Modifier
                .height(150.dp)
                .padding(bottom = 5.dp),
            posterPath = person.profilePath,
            onClick = {
                person.id?.let {
                    onClick(it)
                }
            }
        )

        Text(
            text = person.name ?: "",
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Light
            ),
            textAlign = TextAlign.Center
        )
    }
}