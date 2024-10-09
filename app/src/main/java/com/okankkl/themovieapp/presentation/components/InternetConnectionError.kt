package com.okankkl.themovieapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.ui.theme.BgColor

@Composable
fun InternetConnectionError() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_alert),
                contentDescription = "alert",
                modifier = Modifier
                    .size(64.dp)
                    .padding(bottom = 15.dp)
            )

            Text(
                text = "Please check your internet connection",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 20.sp,
                    color = Color.White
                ),
                textAlign = TextAlign.Center
            )
        }

    }
}