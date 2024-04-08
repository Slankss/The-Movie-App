package com.okankkl.themovieapp.presentation.news_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.okankkl.themovieapp.data.remote.dto.MovieDto
import com.okankkl.themovieapp.presentation.components.ErrorUi
import com.okankkl.themovieapp.presentation.components.LoadingUi
import com.okankkl.themovieapp.presentation.components.ContentPoster
import com.okankkl.themovieapp.presentation.DisplayType
import com.okankkl.themovieapp.presentation.Pages
import com.okankkl.themovieapp.domain.model.Movie
import com.okankkl.themovieapp.presentation.news_screen.components.NewMovieContent
import com.okankkl.themovieapp.ui.theme.OceanPalet4
import java.time.LocalDate

@Composable
fun News(navController: NavController){

    val newMoviesViewModel : NewMoviesViewModel = hiltViewModel()
    val moviesPagingItems : LazyPagingItems<MovieDto> = newMoviesViewModel.newsMovies.collectAsLazyPagingItems()

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
        ){
            items(moviesPagingItems.itemCount){ index ->
                val movie = moviesPagingItems[index]
                if(movie!!.poster_path != null && movie.poster_path!!.isNotEmpty()){
                    NewMovieContent(movie){ movieId ->
                        navController.navigate("${Pages.DisplayDetail.route}/$movieId&${DisplayType.Movie.path}")
                    }
                }
            }
            moviesPagingItems.apply {
                when
                {
                    loadState.refresh is LoadState.Loading ->
                    {
                        item { LoadingUi(Modifier) }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val error = moviesPagingItems.loadState.refresh as LoadState.Error
                        item{
                            ErrorUi(errorMsg =  error.error.localizedMessage!!)
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item{ LoadingUi(Modifier.align(Alignment.CenterHorizontally)) }
                    }
                    loadState.append is LoadState.Error -> {
                        val error = moviesPagingItems.loadState.append as LoadState.Error
                        item{
                            ErrorUi(errorMsg =  error.error.localizedMessage!!)
                        }
                    }
                }
            }
        }
    }
}


