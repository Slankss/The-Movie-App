package com.okankkl.themovieapp.presentation.content_detail_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun SimilarContent(content: Content, index : Int, onClick : (Int) -> Unit){
    Column(
        modifier = Modifier
            .padding(
                start = if(index == 0) 15.dp else 0.dp,
                end = 15.dp,
                bottom = 10.dp
            )
    ){
        ContentPoster(
            posterPath = content.backdropPath!!,
            id = content.id,
            modifier = Modifier
                .size(100.dp)
        ){
            onClick(it)
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
                ){
                    append(content.title)
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFBCBCBC)
                    )
                ){
                    if( content.releaseDate != null && content.releaseDate.isNotEmpty()){
                        val date = LocalDate.parse(content.releaseDate)
                        val year = date.year.toString()
                        append(" ($year)")
                    }
                }
            }
        )
    }
}

