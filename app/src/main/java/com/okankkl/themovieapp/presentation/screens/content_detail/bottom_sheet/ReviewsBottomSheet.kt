package com.okankkl.themovieapp.presentation.screens.content_detail.bottom_sheet

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.data.model.dto.Review
import com.okankkl.themovieapp.presentation.components.Error
import com.okankkl.themovieapp.ui.theme.BottomSheetBgColor
import com.okankkl.themovieapp.utils.DateUtils
import com.okankkl.themovieapp.utils.FunctionUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsBottomSheet(
    modifier: Modifier = Modifier,
    reviews: List<Review>?,
    loadMoreReview: () -> Unit,
    onDismissClick: () -> Unit,
){
    val bottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = onDismissClick,
        containerColor = BottomSheetBgColor
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ){
            if (reviews.isNullOrEmpty()) {
                Error(
                    modifier = modifier.padding(top = 100.dp),
                    errorMsg = "No Reviews Found"
                )
            } else {
                LazyColumn(
                    modifier = modifier.fillMaxSize()
                ) {
                    itemsIndexed(reviews){index,review ->
                        ReviewsItem(review)

                        if (index == reviews.lastIndex){
                            loadMoreReview()
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun ReviewsItem(
    review: Review,
    modifier: Modifier = Modifier
){
    val minimumLineCount = 4
    var showMoreState by remember { mutableStateOf(false) }
    var showMoreVisibility by remember { mutableStateOf(true) }
    val maxLines = if (showMoreState) 100 else minimumLineCount

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
           Row(
               modifier = modifier.padding(bottom = 10.dp),
               verticalAlignment = Alignment.CenterVertically
           ){
               AsyncImage(
                   modifier = modifier
                       .size(36.dp)
                       .clip(CircleShape),
                   model = FunctionUtils.getImageUrl(review.authorDetails?.avatarPath),
                   contentDescription = "Author Image",
                   placeholder = painterResource(R.drawable.user_round_icon),
                   error = painterResource(R.drawable.user_round_icon),
                   contentScale = ContentScale.FillBounds
               )
               Text(
                   modifier = modifier.padding(start = 10.dp),
                   text = review.author ?: "",
                   style = MaterialTheme.typography.headlineLarge.copy(
                       fontSize = 17.sp,
                       color = Color.White,
                       fontWeight = FontWeight.Bold
                   )
               )
           }
            Text(
                text = DateUtils.formatDate(review.createdAt),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 13.sp,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic
                )
            )
        }

        Text(
            modifier = modifier
                .animateContentSize(),
            text = review.content ?: "",
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 15.sp,
                color = Color.White,
                fontWeight = FontWeight.Light
            ),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            onTextLayout = { textLayoutResult ->
                showMoreVisibility = textLayoutResult.didOverflowHeight ||
                        textLayoutResult.lineCount > minimumLineCount

            }
        )

        if (showMoreVisibility){
            Text(
                text = if (showMoreState) "Show Less" else "Show More" ,
                modifier = modifier
                    .padding(top = 5.dp)
                    .clickable {
                        showMoreState = !showMoreState
                    },
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}