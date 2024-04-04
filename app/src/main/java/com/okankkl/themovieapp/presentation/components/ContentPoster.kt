package com.okankkl.themovieapp.presentation.components
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.common.Constants
@Composable
fun ContentPoster(
    posterPath : String,
    id : Int,
    modifier : Modifier,
    onClick : (Int) -> Unit){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
    ){
        AsyncImage(
            model = Constants.IMAGE_BASE_URL +posterPath,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick(id)
                },
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(R.drawable.place_holder)
        )
    }
}