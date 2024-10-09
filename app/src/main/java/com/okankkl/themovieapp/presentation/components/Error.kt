package com.okankkl.themovieapp.presentation.components
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun Error(
    modifier: Modifier = Modifier,
    errorMsg : String){
    Box(
        modifier = Modifier
            .fillMaxSize()
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