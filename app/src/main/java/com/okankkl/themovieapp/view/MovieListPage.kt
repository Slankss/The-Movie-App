package com.okankkl.themovieapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.util.Util
import com.okankkl.themovieapp.viewModel.MovieListViewModel

@Composable
fun MovieList(navController: NavController){

    val movieListViewModel : MovieListViewModel = hiltViewModel()
    val movieList = movieListViewModel.movieList.collectAsState()

    SideEffect {
        movieListViewModel.getMoviesFromInternet()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        when(movieList.value){
            is Resources.Loading -> Loading()
            is Resources.Success -> {
                PosterList(
                    movieList = (movieList.value as Resources.Success).data as List<Movie>,
                    navController)
            }
            is Resources.Failed -> {
                Failed(
                    errorMsg = (movieList.value as Resources.Failed).errorMsg
                )
            }
        }


    }

}

@Composable
fun PosterList(movieList : List<Movie>, navController: NavController){

    LazyVerticalGrid(
        modifier = Modifier
            .padding(horizontal = 5.dp),
        columns = GridCells.Fixed(2)
    ){
        items(movieList){movie ->
            if(movie.posterPath != null && movie.posterPath!!.isNotEmpty()){
                Poster(
                    movie = movie
                ){ movieId ->
                    navController.navigate("${Pages.MovieDetail.route}/${movieId}")
                }
            }
        }
    }

}

@Composable
fun Poster(movie : Movie, onClick : (Int) -> Unit){

    Box(
        modifier = Modifier
            .height(250.dp)
    ){
        AsyncImage(
            model = Util.IMAGE_BASE_URL +movie.posterPath,
            contentDescription = null,
            modifier = Modifier
                .padding(15.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    onClick(movie.id)
                },
            contentScale = ContentScale.FillBounds
        )
    }

}

@Composable
private fun Loading(){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center),
            color = Color.White
        )
    }
}

@Composable
private fun Failed(errorMsg : String){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Text(
            text = errorMsg,
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}