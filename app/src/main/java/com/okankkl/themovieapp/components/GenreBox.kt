package com.okankkl.themovieapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GenreBox(
    genreName : String,
    modifier : Modifier
){

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .border(width = 1.dp,color = Color.White, shape = RoundedCornerShape(15.dp))
            .background(
                color = Color(0xDFAF0CA)
            )

    ){
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .align(Alignment.Center),
            text = genreName,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 12.sp,
                color = Color.White
            )
        )
    }

}