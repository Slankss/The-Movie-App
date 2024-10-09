package com.okankkl.themovieapp.presentation.screens.view_all

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.okankkl.themovieapp.SessionViewModel
import com.okankkl.themovieapp.presentation.components.Loading
import com.okankkl.themovieapp.presentation.components.ContentPoster
import com.okankkl.themovieapp.utils.FunctionUtils

@Composable
fun ViewAllScreen(
    contentType: String?,
    category: String?,
    navigateToContentDetail: (Int?, String?) -> Unit,
    sessionViewModel: SessionViewModel
) {
    val viewAllViewModel: ViewAllViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        if (contentType != null && category != null) {
            viewAllViewModel.load(contentType, category)
        }
    }

    viewAllViewModel.viewState.collectAsState().value.apply {
        Column(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxSize()
        ) {
            if (contentType != null && category != null) {
                Text(
                    text = FunctionUtils.getCategory(category).title + " " + FunctionUtils.getType(
                        contentType
                    ).title,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .padding(top = 25.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    contentList?.let { contentList ->
                        itemsIndexed(contentList) { position, content ->
                            ContentPoster(
                                posterPath = content.posterPath ?: content.backdropPath!!,
                                modifier = Modifier
                                    .height(150.dp),
                                onClick = {
                                    navigateToContentDetail(content.id, contentType)
                                },
                                onLongClick = {
                                    sessionViewModel.changePosterDialogState(true,content.posterPath)
                                }
                            )
                            if (position == contentList.lastIndex) {
                                viewAllViewModel.load(contentType, category)
                            }
                        }
                        item {
                            if (isLoading) {
                                Loading(Modifier)
                            }
                        }
                    }
                }
            }
        }
    }
}