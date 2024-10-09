package com.okankkl.themovieapp.presentation.screens.home.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.okankkl.themovieapp.presentation.components.ContentPoster

@Composable
fun PosterDialog(
    modifier: Modifier = Modifier,
    posterPath: String,
    onDismiss:() -> Unit
){
    Dialog(
        onDismissRequest = onDismiss
    ){
        Box(
            modifier = modifier.fillMaxHeight(0.7f)
        ){
            ContentPoster(
                modifier = modifier
                    .fillMaxWidth(),
                posterPath = posterPath
            )
        }
    }
}