package com.okankkl.themovieapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.enum_sealed.MenuItems
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.ui.theme.BackgroundColor

@Composable
fun BottomMenuItem(selected :Boolean,menuItem: MenuItems){

    val icon = if(selected) menuItem.selectedIcon else menuItem.unSelectedIcon

    Box(
        modifier = Modifier
    ) {
        if(selected){
            Image(
                painterResource(id = menuItem.selectedIcon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp)
            )
        } else {
            Image(
                painterResource(id = menuItem.unSelectedIcon),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp)
            )
        }

    }


}