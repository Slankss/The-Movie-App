package com.okankkl.themovieapp.presentation.screens.content_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.utils.FunctionUtils

@Composable
fun ContentPosterLarge(
    modifier: Modifier,
    posterPath: String,
    contentScale: ContentScale
){
    Box(
        modifier = modifier
    ){
        AsyncImage(
            model = FunctionUtils.getImageUrl(posterPath),
            modifier = modifier,
            contentDescription = null,
            contentScale = contentScale,
            placeholder = painterResource(R.drawable.place_holder)
        )
    }
}