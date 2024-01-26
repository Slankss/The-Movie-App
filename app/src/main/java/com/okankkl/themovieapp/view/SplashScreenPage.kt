package com.okankkl.themovieapp.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.okankkl.themovieapp.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(){

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_screen_anim))

    LaunchedEffect(composition){
        delay(1500)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        LottieAnimation(
            composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
        )
    }

}