package com.okankkl.themovieapp.presentation.screens.content_detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.ui.theme.OceanPalet4

@Composable
fun ContentHeaderUI(
    modifier: Modifier = Modifier,
    title: String,
    isFavourite: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier =  modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 24.sp,
                lineHeight = 32.sp
            ),
            modifier = Modifier.weight(4f)
        )
        Icon(
            painter = if (!isFavourite) painterResource(id = R.drawable.ic_fav_unselected) else painterResource(
                id = R.drawable.ic_fav_selected
            ),
            contentDescription = null,
            tint = if (!isFavourite) Color.White else OceanPalet4,
            modifier = Modifier
                .weight(1f)
                .size(28.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClick()
                }
        )
    }
}