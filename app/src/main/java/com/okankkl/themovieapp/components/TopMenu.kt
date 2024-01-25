package com.okankkl.themovieapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.ui.theme.BackgroundColor

@Composable
fun TopMenuItem(
    displayType : DisplayType,
    isSelected : Boolean,
    click : () -> Unit
){

    val mainShape = CircleShape

    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.White,
                shape = mainShape
            )
            .background(
                color = if (isSelected) Color.White else Color.Transparent,
                shape = mainShape
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                click()
            },){
        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp),
            text = displayType.title,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if(isSelected) BackgroundColor else Color.White,
                fontSize = 12.sp
            )
        )
    }

}