package com.okankkl.themovieapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.okankkl.themovieapp.ui.theme.BackgroundColor
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YouTubePlayer(
    videoId : String,
    lifecycleOwner : LifecycleOwner
){
    AndroidView(
        modifier = Modifier
            .background(
                color = BackgroundColor
            )
            .fillMaxWidth(),
        factory = { context ->

        YouTubePlayerView(context = context).apply {
            addYouTubePlayerListener(
                object  : AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer)
                    {
                        youTubePlayer.loadVideo(videoId,0f)
                        super.onReady(youTubePlayer)
                    }


                })
            lifecycleOwner.lifecycle.addObserver(this)
        }
    })

}