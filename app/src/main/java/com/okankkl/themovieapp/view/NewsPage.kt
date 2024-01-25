package com.okankkl.themovieapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
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
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.ui.theme.OceanPalet4
import com.okankkl.themovieapp.viewModel.NewMoviesViewModel
import java.time.LocalDate

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
                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        ){
            items(moviesPagingItems.itemCount){ index ->
                val movie = moviesPagingItems[index]
                if(movie!!.posterPath != null && movie.posterPath!!.isNotEmpty()){
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
fun NewMovieContent(movie : Movie,onClick : (Int)->Unit){

    val date = LocalDate.parse(movie.releaseDate)
    val day = date.dayOfMonth
    var month = date.month.name
    month = month.lowercase().replaceFirstChar { it.uppercase() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
            ) {
                Divider(
                    modifier = Modifier
                        .height(225.dp)
                        .width(1.dp)
                        .align(Alignment.TopCenter)
                        .background(Color(0xB3FFFFFF))
                )
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(12.dp)
                        .align(Alignment.Center)
                        .background(
                            color = OceanPalet4,
                            shape = CircleShape
                        )
                )
            }
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    ){
                        append(day.toString())
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = Color(0xB3FFFFFF)
                        )
                    ){
                        append(" $month")
                    }
                },
                modifier = Modifier
                    .padding(end = 10.dp)
            )
        }

        Poster(
            posterPath = movie.posterPath!!,
            id = movie.id,
            modifier = Modifier
                .weight(1f)
                .height(225.dp)
                .padding(bottom = 25.dp)
        )
        {
            onClick(it)
        }

    }
}

