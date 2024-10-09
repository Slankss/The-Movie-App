package com.okankkl.themovieapp.presentation.screens.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.okankkl.themovieapp.presentation.components.Error
import com.okankkl.themovieapp.presentation.components.Loading
import com.okankkl.themovieapp.utils.ContentType
import com.okankkl.themovieapp.presentation.screens.news.components.NewMovieContent

@Composable
fun NewsMoviesScreen(
    navigateToContentDetail: (Int?, String?) -> Unit
){

    val newMoviesViewModel : NewMoviesViewModel = hiltViewModel()
    val viewState  = newMoviesViewModel.viewState.collectAsState().value

    LaunchedEffect(key1 = true){
        newMoviesViewModel.loadMovies()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            viewState.movies?.let { movies ->
                itemsIndexed(movies) { position, movie ->
                    NewMovieContent(
                        movieDetail = movie,
                        onClick = {
                            navigateToContentDetail(movie.id, ContentType.Movie.path)
                        }
                    )

                    if (position == movies.lastIndex) {
                        newMoviesViewModel.loadMovies()
                    }
                }
                item {
                    if(viewState.isLoading == true){
                        Loading(modifier = Modifier)
                    }
                    if(!viewState.message.isNullOrEmpty()){
                        Error(errorMsg = viewState.message!!)
                    }
                }
            }
        }
    }
}