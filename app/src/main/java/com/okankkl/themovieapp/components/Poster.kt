package com.okankkl.themovieapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.model.TvSeries
import com.okankkl.themovieapp.util.Util

@Composable
fun Poster(
    posterPath : String,
    id : Int,
    modifier : Modifier,
    onClick : (Int) -> Unit){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
    ){
        AsyncImage(
            model = Util.IMAGE_BASE_URL +posterPath,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick(id)
                },
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(R.drawable.place_holder)
        )
    }
}