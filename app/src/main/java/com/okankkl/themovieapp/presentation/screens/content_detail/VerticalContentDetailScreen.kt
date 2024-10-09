package com.okankkl.themovieapp.presentation.screens.content_detail

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.model.ContentDetail
import com.okankkl.themovieapp.presentation.components.HorizontalDivider
import com.okankkl.themovieapp.presentation.screens.content_detail.components.ContentDetailSubHeader
import com.okankkl.themovieapp.presentation.screens.content_detail.components.ContentPosterLarge
import com.okankkl.themovieapp.presentation.screens.content_detail.components.CreditRow
import com.okankkl.themovieapp.presentation.screens.content_detail.components.GenreRowUI
import com.okankkl.themovieapp.presentation.screens.content_detail.components.SimilarContentList
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.okankkl.themovieapp.data.model.dto.Credits
import com.okankkl.themovieapp.presentation.components.ContentPoster
import com.okankkl.themovieapp.presentation.screens.content_detail.components.ContentCast
import com.okankkl.themovieapp.utils.FunctionUtils

@Composable
fun VerticalContentDetailScreen(
    contentDetail: ContentDetail,
    openCommentBottomSheetState: () -> Unit,
    openVideosBottomSheetState: () -> Unit,
    openImageBottomSheet: () -> Unit,
    navigateToContentDetail: (Int?, String?) -> Unit,
    favouriteClick: () -> Unit,
    isFavourite: Boolean,
    personClick: (Int) -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        contentDetail.apply {

            backdropPath?.let {
                item {
                    ContentPosterLarge(
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                        posterPath = it
                    )
                }
            }

            item {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                ) {
                    val (header, buttons) = createRefs()
                    title?.let {
                        Text(
                            modifier = Modifier
                                .constrainAs(header) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                    end.linkTo(buttons.start)
                                    width = Dimension.fillToConstraints
                                }
                                .padding(end = 10.dp),
                            text = it,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontSize = 18.sp,
                                color = Color.White
                            )
                        )
                    }
                    Row(
                        modifier = Modifier
                            .constrainAs(buttons) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                            },
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            modifier = Modifier.size(30.dp),
                            onClick = openImageBottomSheet,
                        ) {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(R.drawable.ic_image),
                                contentDescription = "Image Icon"
                            )
                        }
                        IconButton(
                            modifier = Modifier.size(30.dp),
                            onClick = openCommentBottomSheetState,
                        ) {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(R.drawable.ic_comment),
                                contentDescription = "Command Icon"
                            )
                        }
                        IconButton(
                            modifier = Modifier.size(30.dp),
                            onClick = openVideosBottomSheetState,
                        ) {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(R.drawable.ic_video),
                                contentDescription = "Trailers Icon"
                            )
                        }
                        IconButton(
                            modifier = Modifier.size(30.dp),
                            onClick = {
                                getTrailerUrl()?.let { trailerUrl ->
                                    val intent = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_TEXT, trailerUrl)
                                    }
                                    context.startActivity(intent)
                                }
                            },
                        ) {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(R.drawable.ic_share),
                                contentDescription = "Share Icon"
                            )
                        }
                        IconButton(
                            modifier = Modifier
                                .size(30.dp),
                            onClick = favouriteClick,
                        ) {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                painter = painterResource(
                                    if (isFavourite) R.drawable.ic_fav_selected else R.drawable.ic_fav_unselected
                                ),
                                contentDescription = "Favourite Icon"
                            )
                        }
                    }
                }
            }

            item {
                ContentDetailSubHeader(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxWidth(),
                    releaseDate = releaseDate,
                    runtime = runtime,
                    voteAverage = voteAverage,
                    numberOfSeason = numberOfSeasons,
                    numberOfEpisode = numberOfEpisodes,
                    lastAirDate = lastAirDate
                )
            }

            genres?.let {
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            start = 15.dp,
                            end = 15.dp,
                            bottom = 10.dp
                        )
                    )
                    GenreRowUI(
                        modifier = Modifier.padding(horizontal = 15.dp),
                        genres = it
                    )
                }
            }

            if (!overview.isNullOrEmpty()) {
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 15.dp)
                    ) {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                        Text(
                            modifier = Modifier.padding(bottom = 5.dp),
                            text = "Overview",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 16.sp,
                                color = Color(0xFFFFFFFF)
                            )
                        )
                        Text(
                            text = overview,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                                color = Color(0xFFBCBCBC),
                                textAlign = TextAlign.Justify
                            )
                        )
                    }
                }
            }

            if (credits != null) {
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            start = 15.dp,
                            end = 15.dp,
                            bottom = 10.dp
                        )
                    )
                    ContentCast(
                        credits = credits,
                        onClick = { id ->
                            personClick(id)
                        }
                    )
                }
            }

            if (!createdBy.isNullOrEmpty()) {
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            start = 15.dp,
                            end = 15.dp,
                            bottom = 10.dp
                        )
                    )

                    CreditRow(
                        modifier = Modifier.padding(horizontal = 15.dp),
                        createdBy = createdBy,
                        personClick = { id ->
                            personClick(id)
                        }
                    )
                }
            }

            if (!similarContents.isNullOrEmpty()) {
                item {
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            start = 15.dp,
                            end = 15.dp,
                            bottom = 10.dp
                        )
                    )

                    SimilarContentList(
                        similarContents = similarContents,
                        onClick = { contentId, contentType ->
                            navigateToContentDetail(contentId, contentType)
                        }
                    )
                }
            }

        }
    }
}