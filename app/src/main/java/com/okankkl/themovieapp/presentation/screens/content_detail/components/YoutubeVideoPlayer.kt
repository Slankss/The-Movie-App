package com.okankkl.themovieapp.presentation.screens.content_detail.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubeVideoPlayer(
    modifier: Modifier = Modifier,
    videoKey: String,
    lifecycleOwner: LifecycleOwner
) {
    var currentSecond by rememberSaveable { mutableFloatStateOf(0f) }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            YouTubePlayerView(context = context).apply {

                addYouTubePlayerListener(
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(videoKey, currentSecond)
                            super.onReady(youTubePlayer)
                        }

                        override fun onCurrentSecond(
                            youTubePlayer: YouTubePlayer,
                            second: Float
                        ) {
                            currentSecond = second
                            super.onCurrentSecond(youTubePlayer, second)
                        }
                    })

                lifecycleOwner.lifecycle.addObserver(this)
            }
        })
}