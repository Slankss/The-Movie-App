package com.okankkl.themovieapp.presentation.screens.content_detail.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okankkl.themovieapp.data.model.dto.Video
import com.okankkl.themovieapp.data.model.dto.Videos
import com.okankkl.themovieapp.ui.theme.BottomSheetBgColor
import com.okankkl.themovieapp.ui.theme.BoxBgColor
import com.okankkl.themovieapp.utils.DateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideosBottomSheet(
    videos: Videos?,
    onClick: (String) -> Unit,
    onDismissClick: () -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismissClick,
        containerColor = BottomSheetBgColor
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    text = "Trailers",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 24.sp,
                        color = Color.White
                    ),
                    textAlign = TextAlign.Center
                )
            }
            videos?.results?.let {
                items(it) { video ->
                    VideoListItem(
                        video = video,
                        onClick = { videoKey ->
                            onClick(videoKey)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun VideoListItem(
    video: Video,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .background(color = BoxBgColor, shape = RoundedCornerShape(14.dp))
            .clickable {
                video.key?.let { key ->
                    onClick(key)
                }
            }
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = video.name ?: "",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = DateUtils.formatDate(video.publishedAt),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Italic
                    )
                )
                Text(
                    text = video.type ?: "",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}