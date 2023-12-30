package com.okankkl.themovieapp.components

import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import com.okankkl.themovieapp.ui.theme.BackgroundColor
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import androidx.compose.runtime.*

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