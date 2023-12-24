package com.okankkl.themovieapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.components.SearchTextField
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.Pages
import com.okankkl.themovieapp.model.Resources
import com.okankkl.themovieapp.util.Util.IMAGE_BASE_URL
import com.okankkl.themovieapp.viewModel.MovieListViewModel

@Composable
fun MovieList(navController: NavController)
{
    var movieListViewModel : MovieListViewModel = hiltViewModel()
    var movieList = movieListViewModel.movieList.collectAsState()

    SideEffect {
        movieListViewModel.getMoviesFromInternet()
    }

    var movieList2 by remember {
        mutableStateOf(
            listOf<Int>(
                R.drawable.movie,
                R.drawable.movie,
                R.drawable.movie,
                R.drawable.movie2,
                R.drawable.movie,
                R.drawable.movie2,
                R.drawable.movie,
                R.drawable.movie2
        ))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Search()
        when(movieList.value){
            is Resources.Loading -> Resources.Loading
            is Resources.Success -> {
                PosterList(
                    movieList = (movieList.value as Resources.Success).movieList,
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
fun Search(){

    var search by remember { mutableStateOf("")}

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 20.dp),

        ){
        val (searchField,favourites) = createRefs()
        SearchTextField(
            modifier = Modifier
                .constrainAs(searchField){
                    top.linkTo(parent.top)
                    end.linkTo(favourites.start, margin = 10.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints

                },
            hint = "Hangi filmi arıyorsun?",
            value = search,
            onValueChange = {
                search = it
            }
        )
        Box(
            modifier = Modifier
                .constrainAs(favourites) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .background(
                    color = Color(0x24FFFFFF),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable {

                }
        ){
            Icon(
                painterResource(id = R.drawable.ic_favourite),
                tint = Color.White,
                contentDescription = "Favourites",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(10.dp)
            )
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
        items(movieList){it ->
            Poster(
                movie = it
            ){ movieId ->
                navController.navigate("${Pages.MovieDetail.name}/${movieId}")
            }
        }
    }

}

@Composable
fun Poster(movie : Movie,onClick : (Int) -> Unit){

    Box(
        modifier = Modifier
            .height(250.dp)
    ){
        AsyncImage(
            model = IMAGE_BASE_URL+movie.posterPath,
            contentDescription = null,
            modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    onClick(movie.id)
                },
            contentScale = ContentScale.FillBounds
        )
    }

}

@Composable
fun Loading(){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
fun Failed(errorMsg : String){
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