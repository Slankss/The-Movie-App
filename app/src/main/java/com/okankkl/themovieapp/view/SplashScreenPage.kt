package com.okankkl.themovieapp.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.foundation.layout.size
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieConstants
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.enum_sealed.Pages
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