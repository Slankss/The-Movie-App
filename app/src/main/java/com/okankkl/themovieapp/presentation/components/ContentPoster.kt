package com.okankkl.themovieapp.presentation.components
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.utils.FunctionUtils

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContentPoster(
    posterPath : String?,
    modifier : Modifier = Modifier,
    onClick : () -> Unit? = {},
    onLongClick: () -> Unit? = {}
){
    val haptics = LocalHapticFeedback.current
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .combinedClickable(
                onClick = {
                    onClick()
                },
                onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongClick()
                }
            )
    ){
        AsyncImage(
            model = FunctionUtils.getImageUrl(posterPath),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(R.drawable.place_holder),
            error = painterResource(R.drawable.place_holder)
        )
    }
}