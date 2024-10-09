package com.okankkl.themovieapp.presentation.screens.content_detail.dialog

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.okankkl.themovieapp.presentation.screens.content_detail.components.YoutubeVideoPlayer

@Composable
fun VideoDialog(
    videoKey: String,
    dismissClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    Dialog(
        onDismissRequest = dismissClick,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        )
    ) {
        Box(
            modifier = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight()
            } else {
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            }
        ) {
            YoutubeVideoPlayer(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                videoKey = videoKey,
                lifecycleOwner = LocalLifecycleOwner.current
            )
        }
    }
}