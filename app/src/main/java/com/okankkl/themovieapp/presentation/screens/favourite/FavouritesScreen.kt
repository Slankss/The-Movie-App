package com.okankkl.themovieapp.presentation.screens.favourite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.okankkl.themovieapp.SessionViewModel
import com.okankkl.themovieapp.presentation.components.Error
import com.okankkl.themovieapp.presentation.components.Loading
import com.okankkl.themovieapp.utils.ContentType
import com.okankkl.themovieapp.presentation.screens.favourite.components.FavouritesList
import com.okankkl.themovieapp.presentation.screens.home.components.TopMenuItem
import kotlinx.coroutines.launch

@Composable
fun FavouritesScreen(
    navigateToContentDetail: (Int?, String?) -> Unit,
    sessionViewModel: SessionViewModel
) {

    val scope = rememberCoroutineScope()
    val viewModel: FavouritesViewModel = hiltViewModel()
    val pagerState = rememberPagerState(pageCount = { ContentType.values().size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    LaunchedEffect(key1 = true) {
        viewModel.getFavourites()
        viewModel.viewState.value.let { viewState ->
            if (viewState.selectedPage == ContentType.TvSeries) {
                viewModel.setPage(ContentType.TvSeries)
                pagerState.scrollToPage(1)
            } else {
                viewModel.setPage(ContentType.Movie)
                pagerState.scrollToPage(0)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        viewModel.viewState.collectAsState().value.apply {
            Row(
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                ContentType.values().forEachIndexed { index, contentType ->

                    val isSelected = selectedTabIndex.value == index
                    TopMenuItem(
                        contentType = contentType,
                        isSelected = isSelected,
                        onClick = {
                            scope.launch {
                                viewModel.setPage(contentType)
                                pagerState.scrollToPage(contentType.ordinal)
                            }
                        }
                    )
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState,
                    userScrollEnabled = false,
                    verticalAlignment = Alignment.Top
                ) {
                    FavouritesList(
                        favouritesViewModel = viewModel,
                        favouriteList = when (selectedPage) {
                            ContentType.Movie -> favouriteMovies ?: emptyList()
                            ContentType.TvSeries -> favouriteTvSeries ?: emptyList()
                            else -> emptyList()
                        },
                        navigateContentDetailState = { contentId, contentType ->
                            navigateToContentDetail(contentId, contentType)
                        },
                        deleteFavourite = { favourite ->
                            viewModel.deleteFavourite(favourite)
                        }
                    )
                }

                if(isLoading){
                    Loading(modifier = Modifier.align(Alignment.Center))
                }
                if(!message.isNullOrEmpty()){
                    Error(errorMsg = message)
                }


            }
        }
    }
}