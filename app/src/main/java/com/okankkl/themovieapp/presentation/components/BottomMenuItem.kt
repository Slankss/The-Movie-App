package com.okankkl.themovieapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.okankkl.themovieapp.presentation.MenuItem

@Composable
fun BottomMenuItem(selected :Boolean,menuItem: MenuItem){

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