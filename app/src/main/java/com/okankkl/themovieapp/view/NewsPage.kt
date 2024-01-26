package com.okankkl.themovieapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.okankkl.themovieapp.components.Failed
import com.okankkl.themovieapp.components.Loading
import com.okankkl.themovieapp.components.Poster
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.ui.theme.OceanPalet4
import com.okankkl.themovieapp.viewModel.NewMoviesViewModel

@Composable
fun News(navController: NavController){

    val newMoviesViewModel : NewMoviesViewModel = hiltViewModel()
    val moviesPagingItems : LazyPagingItems<Movie> = newMoviesViewModel.newsMovies.collectAsLazyPagingItems()

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
                .padding(top = 20.dp, start = 0.dp, end = 0.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ){
            items(moviesPagingItems.itemCount){ index ->
                val movie = moviesPagingItems[index]
                if(movie!!.posterPath != null && movie.posterPath!!.isNotEmpty()){
                    NewMovieContent(movie)
                }
            }
            moviesPagingItems.apply {
                when
                {
                    loadState.refresh is LoadState.Loading ->
                    {
                        item { Loading(Modifier)}
                    }
                    loadState.refresh is LoadState.Error -> {
                        val error = moviesPagingItems.loadState.refresh as LoadState.Error
                        item{
                            Failed(errorMsg =  error.error.localizedMessage!!)
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item{ Loading(Modifier) }
                    }
                    loadState.append is LoadState.Error -> {
                        val error = moviesPagingItems.loadState.append as LoadState.Error
                        item{
                            Failed(errorMsg =  error.error.localizedMessage!!)
                        }
                    }
                }
            }

        }

    }

}

@Composable
fun NewMovieContent(movie : Movie){

    Row(

    ){
        /*
        Column(
            modifier = Modifier
        ) {
            Text(
                text = "14",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 32.sp,
                    color = Color.White,

                    ),
                modifier = Modifier
                    .padding(end = 5.dp)

            )
            Text(
                text = "JAN"
            )
        }


         */

        Poster(
            posterPath = movie.backdropPath!!,
            id = movie.id,
            modifier = Modifier
                .fillMaxWidth()
                .height(225.dp)

        )
        {

        }

    }

}