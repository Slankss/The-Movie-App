package com.okankkl.themovieapp.presentation.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.okankkl.themovieapp.SessionViewModel
import com.okankkl.themovieapp.presentation.components.ContentPoster
import com.okankkl.themovieapp.presentation.components.Error
import com.okankkl.themovieapp.presentation.components.Loading
import com.okankkl.themovieapp.presentation.screens.search.components.SearchBarUI

@Composable
fun SearchPage(
    navigateToContentDetail: (Int?, String?) -> Unit,
    onMessage: (String) -> Unit,
    sessionViewModel: SessionViewModel
) {
    val searchViewModel: SearchViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBarUI { query ->
            searchViewModel.search(query)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            searchViewModel.viewState.collectAsState().value.apply {
                if (!contentList.isNullOrEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        contentList.filter { it.posterPath != null }.let { contentList ->
                            itemsIndexed(contentList) { position, content ->
                                ContentPoster(
                                    posterPath = content.posterPath!!,
                                    modifier = Modifier
                                        .height(150.dp),
                                    onClick = {
                                        navigateToContentDetail(content.id,content.contentType)
                                    },
                                    onLongClick = {
                                        sessionViewModel.changePosterDialogState(true,content.posterPath)
                                    }
                                )

                                if (position == contentList.lastIndex) {
                                    searchViewModel.loadMore()
                                }
                            }
                        }
                    }
                }
                if (isLoading) {
                    Loading(modifier = Modifier.align(Alignment.Center))
                }

                if (!message.isNullOrEmpty()) {
                    Error(errorMsg = message)
                }
            }
        }
    }
}