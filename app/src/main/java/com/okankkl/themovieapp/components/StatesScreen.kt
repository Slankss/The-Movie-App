package com.okankkl.themovieapp.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Loading(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ){
        CircularProgressIndicator(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun Failed(errorMsg : String){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
    ){
        Text(
            text = errorMsg,
            fontSize = 18.sp,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}