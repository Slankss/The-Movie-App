package com.okankkl.themovieapp.presentation.screens

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LifecycleStartEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.SessionViewModel
import com.okankkl.themovieapp.utils.ScreenUtils
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubePlayerScreen(
    videoKey: String?,
    sessionViewModel: SessionViewModel,
    navigateToBack: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var currentSecond by rememberSaveable { mutableFloatStateOf(0f) }

    LifecycleStartEffect(Unit) {
        sessionViewModel.changeMenuVisibility(false)

        ScreenUtils.setScreenOrientation(context)
        ScreenUtils.setVisibilityOfSystemUi(context, false)

        onStopOrDispose {
            sessionViewModel.changeMenuVisibility(true)
            ScreenUtils.setScreenOrientation(context, false)
            ScreenUtils.setVisibilityOfSystemUi(context, true)
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ){
        videoKey?.let {
            AndroidView(
                factory = { context ->
                    YouTubePlayerView(context = context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
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
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f,true)
            )
        }
        IconButton(
            onClick = navigateToBack,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(15.dp)
                .background(
                    color = Color.LightGray,
                    shape = CircleShape
                )
        ){
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}