package com.okankkl.themovieapp.presentation.content_detail_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.okankkl.themovieapp.common.Constants
import com.okankkl.themovieapp.data.remote.dto.Videos
import com.okankkl.themovieapp.presentation.components.YouTubePlayer

@Composable
fun Trailer(videos: Videos?, backdropPath : String?){
    Box(
        modifier = Modifier
            .fillMaxWidth()
        ,
    ){
        videos?.apply {
            val trailer = results.firstOrNull{ it.type == "Trailer"}
                ?: results.firstOrNull()

            trailer?.let { video ->
                YouTubePlayer(videoId = video.key, lifecycleOwner = LocalLifecycleOwner.current)
            }  ?: Box(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                AsyncImage(
                    model = Constants.IMAGE_BASE_URL +backdropPath,
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp)
                )
            }
        }
    }
}