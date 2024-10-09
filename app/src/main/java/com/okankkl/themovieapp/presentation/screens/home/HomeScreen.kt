package com.okankkl.themovieapp.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.okankkl.themovieapp.SessionViewModel
import com.okankkl.themovieapp.presentation.components.Loading
import com.okankkl.themovieapp.utils.ContentType
import com.okankkl.themovieapp.presentation.components.Error
import com.okankkl.themovieapp.presentation.screens.home.components.HorizontalContentListUI
import com.okankkl.themovieapp.presentation.screens.home.components.TopMenu
import com.okankkl.themovieapp.presentation.screens.home.components.TrendContentList
import com.okankkl.themovieapp.presentation.screens.home.dialog.PosterDialog

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun HomeScreen(
    navigateToContentDetail: (Int?, String?) -> Unit,
    navigateToViewAll: (String?, String?) -> Unit,
    navigateToSearch: () -> Unit,
    sessionViewModel: SessionViewModel
) {
    val homeViewModel: HomeViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        homeViewModel.setSelectedPage(homeViewModel.viewState.value.selectedPage)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        homeViewModel.viewState.collectAsState().value.apply {
            selectedPage?.let { selectedPage ->

                Column {
                    TopMenu(
                        modifier = Modifier.padding(top = 30.dp, start = 25.dp, end = 25.dp),
                        selectedPage = selectedPage,
                        navigateSearch = navigateToSearch,
                        setSelectedPage = { page ->
                            homeViewModel.setSelectedPage(page)
                        }
                    )

                    val contentList = if (selectedPage == ContentType.Movie) movies else tvSeries

                    contentList?.let {
                        ContentScreen(
                            contentList = it,
                            selectedPage = selectedPage,
                            navigateToContentDetail = navigateToContentDetail,
                            navigateToViewAll = navigateToViewAll,
                            showPosterDialog = { showPosterDialog, posterPath ->
                                sessionViewModel.changePosterDialogState(showPosterDialog,posterPath)
                            }
                        )
                    }
                }

            }

            if (isLoading) {
                Loading(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if (errorMessage?.isNotBlank() == true) {
                Error(
                    modifier = Modifier.align(Alignment.Center),
                    errorMsg = errorMessage
                )
            }
        }

    }
}

@Composable
fun ContentScreen(
    contentList: ContentList,
    selectedPage: ContentType,
    navigateToContentDetail: (Int?, String?) -> Unit,
    navigateToViewAll: (String?, String?) -> Unit,
    showPosterDialog:(Boolean,String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        contentList.apply {
            if (trending != null){
                TrendContentList(
                    contents = trending!!,
                    contentType = selectedPage.path,
                    topPadding = 25.dp,
                    onClick = { contentId, contentType ->
                        navigateToContentDetail(contentId, contentType)
                    }
                )
            }

            if(popular != null){
                HorizontalContentListUI(
                    contents = popular!!,
                    navigateToContentDetail = { contentId, contentType ->
                        navigateToContentDetail(contentId, contentType)
                    },
                    navigateToViewAll = { contentType, category ->
                        navigateToViewAll(contentType, category)
                    },
                    showPosterDialog = showPosterDialog
                )
            }

            if(nowPlaying != null){
                HorizontalContentListUI(
                    contents = nowPlaying!!,
                    navigateToContentDetail = { contentId, contentType ->
                        navigateToContentDetail(contentId, contentType)
                    },
                    navigateToViewAll = { contentType, category ->
                        navigateToViewAll(contentType, category)
                    },
                    showPosterDialog = showPosterDialog
                )
            }

            if(topRated != null){
                HorizontalContentListUI(
                    contents = topRated!!,
                    navigateToContentDetail = { contentId, contentType ->
                        navigateToContentDetail(contentId, contentType)
                    },
                    navigateToViewAll = { contentType, category ->
                        navigateToViewAll(contentType, category)
                    },
                    showPosterDialog = showPosterDialog
                )
            }
        }
    }
}