package com.okankkl.themovieapp.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.utils.ContentType
import com.okankkl.themovieapp.ui.theme.BgTransparentColor

@Composable
fun TopMenu(
    modifier : Modifier,
    selectedPage: ContentType,
    setSelectedPage: (ContentType) -> Unit,
    navigateSearch: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = BgTransparentColor)
            .pointerInput(Unit) {
                detectTapGestures {}
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContentType.values().forEachIndexed { index, menu ->
                val isSelected = selectedPage.path == menu.path
                TopMenuItem(contentType = menu, isSelected = isSelected) {
                    setSelectedPage(menu)
                }
            }
        }

        Icon(painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "search",
            tint = Color.White,
            modifier = Modifier
                .size(26.dp)
                .align(Alignment.TopEnd)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    navigateSearch()
                })
    }
}