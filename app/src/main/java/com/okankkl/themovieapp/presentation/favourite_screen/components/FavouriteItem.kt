package com.okankkl.themovieapp.presentation.favourite_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.domain.model.Favourite
import com.okankkl.themovieapp.extensions.convertDate
import com.okankkl.themovieapp.presentation.DisplayType
import com.okankkl.themovieapp.presentation.components.ContentPoster
import com.okankkl.themovieapp.ui.theme.OceanPalet4
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun FavouriteItem(favourite : Favourite, onClick : () -> Unit, onDeleteClick : () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF0D1321),
                        Color(0xFF303B4A)
                    )
                ),
                shape = RoundedCornerShape(14.dp)
            )
            .clip(RoundedCornerShape(14.dp))
            .border(
                width = 1.dp,
                color = Color(0x0DFFFFFF),
                shape = RoundedCornerShape(14.dp)
            )
            .clickable {
                onClick()
            }
    ){
        val delete = SwipeAction(
            onSwipe = {
                onDeleteClick()
            },
            icon = {
                Icon(
                    modifier = Modifier.padding(start = 25.dp),
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    tint = Color(0xFFFFFFFF)
                )
            },
            background = Color(0xfff64848),

            )
        SwipeableActionsBox(
            endActions = listOf(delete),
            swipeThreshold = 150.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ContentPoster(
                    posterPath = favourite.posterPath,
                    id = favourite.contentId,
                    modifier = Modifier
                        .height(150.dp)
                        .weight(1f)
                        .shadow(
                            elevation = 4.dp,
                            ambientColor = OceanPalet4,
                            shape = RoundedCornerShape(14.dp)
                        )
                ){}

                Column(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxHeight()
                        .weight(2f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp),
                        text = favourite.title,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 16.sp,
                            color = Color(0xB3FFFFFF)
                        )
                    )
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0x0DFAF0CA),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clip(RoundedCornerShape(14.dp))
                    ){
                        Row(
                            modifier = Modifier
                                .padding(7.dp)
                        ){
                            Icon(
                                painter = painterResource(id = R.drawable.filled_star),
                                contentDescription = "star",
                                tint = OceanPalet4
                            )
                            Text(
                                modifier = Modifier
                                    .padding(start = 5.dp),
                                text = String.format("%.1f",favourite.imdb),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 12.sp,
                                    color = Color(0xB3FFFFFF)
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = "date",
                            tint = Color(0xB3FFFFFF)
                        )
                        val date = convertDate(favourite.date)
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp),
                            text = date,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 12.sp,
                                color = Color(0xB3FFFFFF)
                            )
                        )

                    }
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontSize = 12.sp,
                                    color = Color(0x4DFFFFFF)
                                )
                            ){
                                append(
                                    text = if(favourite.type == DisplayType.Movie.path) "Time: " else
                                        ""
                                )
                            }
                            withStyle(
                                SpanStyle(
                                    fontSize = 12.sp,
                                    color = Color(0xB3FFFFFF)
                                )
                            ){
                                append(favourite.time)
                            }
                        }
                    )
                }
            }
        }

    }


}