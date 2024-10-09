package com.okankkl.themovieapp.presentation.screens.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.utils.FunctionUtils
import com.okankkl.themovieapp.presentation.components.ContentPoster
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalContentListUI(
    contents: List<Content>,
    navigateToContentDetail: (Int?, String?) -> Unit,
    navigateToViewAll: (String?, String?) -> Unit,
    showPosterDialog:(Boolean,String?) -> Unit
) {
    val scope = rememberCoroutineScope()
    val longPressTimeout = 1000L
    val haptics = LocalHapticFeedback.current
    Column(
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            contents.firstOrNull()?.category?.let { category ->
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 15.dp),
                    text = FunctionUtils.getCategory(category).title,
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 16.sp)
                )
            }

            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp)
                    .clickable {
                        contents.firstOrNull()?.let { content ->
                            navigateToViewAll(content.contentType,content.category)
                        }
                    },
                text = "view all",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 12.sp,
                    color = Color(0xB3FFFFFF)
                )
            )
        }
        LazyRow(
            modifier = Modifier
        ) {
            itemsIndexed(contents) { index, content ->
                var isPressed by remember { mutableStateOf(false) }
                if (!content.posterPath.isNullOrEmpty()) {
                    ContentPoster(
                        posterPath = content.posterPath,
                        modifier = Modifier
                            .width(125.dp)
                            .height(150.dp)
                            .padding(
                                start = 15.dp, end = if (index == contents.lastIndex) 15.dp else 0.dp
                            ),
                        onClick = {
                            navigateToContentDetail(content.id,content.contentType)
                        },
                        onLongClick = {
                            showPosterDialog(true,content.posterPath)
                        }
                    )
                }
            }
        }
    }
}