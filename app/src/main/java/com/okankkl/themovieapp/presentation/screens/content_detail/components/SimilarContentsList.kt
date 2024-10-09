package com.okankkl.themovieapp.presentation.screens.content_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.presentation.components.ContentPoster
import java.time.LocalDate

@Composable
fun SimilarContentList(
    modifier: Modifier = Modifier,
    similarContents: List<Content>,
    onClick: (Int, String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Similar Contents",
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(start = 15.dp,bottom = 10.dp)
        )
        LazyRow{
            itemsIndexed(similarContents) { position, content ->
                SimilarContentsItem(
                    content = content,
                    position = position,
                    onClick = {
                        if (content.id != null && content.contentType != null){
                            onClick(content.id, content.contentType!!)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SimilarContentsItem(
    content: Content,
    position: Int,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                start = if (position == 0) 15.dp else 0.dp,
                end = 15.dp,
                bottom = 10.dp
            )
    ) {
        content.backdropPath?.let {
            ContentPoster(
                posterPath = it,
                modifier = Modifier.size(100.dp),
                onClick = onClick
            )
        }

        Text(
            modifier = Modifier
                .width(100.dp)
                .padding(
                    top = 10.dp
                ),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                ) {
                    append(content.title)
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFBCBCBC)
                    )
                ) {
                    if (!content.releaseDate.isNullOrEmpty()) {
                        val date = LocalDate.parse(content.releaseDate)
                        val year = date.year.toString()
                        append(" ($year)")
                    }
                }
            }
        )
    }
}