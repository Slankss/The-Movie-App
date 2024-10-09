package com.okankkl.themovieapp.presentation.screens.content_detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import com.okankkl.themovieapp.SessionViewModel
import com.okankkl.themovieapp.data.model.dto.Images
import com.okankkl.themovieapp.presentation.components.Loading
import com.okankkl.themovieapp.presentation.components.Error
import com.okankkl.themovieapp.presentation.screens.content_detail.bottom_sheet.PersonDetailBottomSheet
import com.okankkl.themovieapp.presentation.screens.content_detail.bottom_sheet.ReviewsBottomSheet
import com.okankkl.themovieapp.presentation.screens.content_detail.dialog.VideoDialog
import com.okankkl.themovieapp.presentation.screens.content_detail.bottom_sheet.VideosBottomSheet
import com.okankkl.themovieapp.presentation.screens.content_detail.dialog.ImageDialog
import com.okankkl.themovieapp.utils.ContentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentDetailScreen(
    contentId: Int?,
    contentType: String?,
    navigateToContentDetail: (Int?, String?) -> Unit,
    sessionViewModel: SessionViewModel
) {
    val contentDetailViewModel: ContentDetailViewModel = hiltViewModel()
    val viewState = contentDetailViewModel.viewState.collectAsState().value
    val configuration = LocalConfiguration.current

    LaunchedEffect(key1 = true) {
        if (contentId != null && contentType != null) {
            contentDetailViewModel.getData(contentId, contentType)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        viewState.apply {

            contentDetail?.let { contentDetail ->
                if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                    HorizontalContentDetailScreen(
                        contentDetail = contentDetail,
                        openCommentBottomSheetState = {
                            contentDetailViewModel.changeCommentsBottomSheetState(true)
                        },
                        openVideosBottomSheetState = {
                            contentDetailViewModel.changeVideosBottomSheetState(true)
                        },
                        navigateToContentDetail = { contentId, contentType ->
                            navigateToContentDetail(contentId, contentType)
                        },
                        favouriteClick = {
                            if (isFavourite) {
                                contentDetailViewModel.deleteFavourite()
                            } else {
                                contentDetailViewModel.addFavourite()
                            }
                        },
                        isFavourite = isFavourite,
                        personClick = { id ->
                            contentDetailViewModel.getPersonDetail(id)
                        },
                        openImageBottomSheet = {
                            contentDetailViewModel.changeImageDialogState(true)
                        }
                    )
                } else {
                    VerticalContentDetailScreen(
                        contentDetail = contentDetail,
                        openCommentBottomSheetState = {
                            contentDetailViewModel.changeCommentsBottomSheetState(true)
                        },
                        openVideosBottomSheetState = {
                            contentDetailViewModel.changeVideosBottomSheetState(true)
                        },
                        navigateToContentDetail = { contentId, contentType ->
                            navigateToContentDetail(contentId, contentType)
                        },
                        favouriteClick = {
                            if (isFavourite) {
                                contentDetailViewModel.deleteFavourite()
                            } else {
                                contentDetailViewModel.addFavourite()
                            }
                        },
                        isFavourite = isFavourite,
                        personClick = { id ->
                            contentDetailViewModel.getPersonDetail(id)
                        },
                        openImageBottomSheet = {
                            contentDetailViewModel.changeImageDialogState(true)
                        }
                    )
                }
            }

            if (showVideosBottomSheet && contentDetail?.videos != null) {
                VideosBottomSheet(
                    videos = contentDetail.videos,
                    onClick = { videoKey ->
                        contentDetailViewModel.changeVideoDialogState(true)
                        contentDetailViewModel.setCurrentVideoKey(videoKey)
                    },
                    onDismissClick = {
                        contentDetailViewModel.changeVideosBottomSheetState(false)
                    }
                )
            }

            if (showReviewsBottomSheet) {
                ReviewsBottomSheet(
                    reviews = reviewsState.reviews,
                    loadMoreReview = {
                        if (contentDetail?.id != null && contentDetail.contentType != null){
                            if (contentDetail.contentType == ContentType.Movie.path){
                                contentDetailViewModel.getMovieReviews(contentDetail.id)
                            } else {
                                contentDetailViewModel.getTvSeriesReviews(contentDetail.id)
                            }
                        }
                    },
                    onDismissClick = {
                        contentDetailViewModel.changeCommentsBottomSheetState(false)
                    }
                )
            }

            if (showVideoDialog && currentVideoKey != null) {
                VideoDialog(
                    videoKey = currentVideoKey,
                    dismissClick = {
                        contentDetailViewModel.changeVideoDialogState(false)
                    }
                )
            }

            if (showPersonDetailBottomSheet && personDetail != null){
                PersonDetailBottomSheet(
                    person = personDetail,
                    onDismissClick = {
                        contentDetailViewModel.changePersonDetailBottomSheetState(false)
                    }
                )
            }

            if (showImageDialog && contentDetail?.images != null){
                ImageDialog(
                    onDismiss = {
                        contentDetailViewModel.changeImageDialogState(false)
                    },
                    images = contentDetail.images
                )
            }

            if (isLoading) {
                Loading(modifier = Modifier.align(Alignment.Center))
            }
            if (!errorMessage.isNullOrEmpty()) {
                Error(errorMsg = errorMessage)
            }
        }

    }
}