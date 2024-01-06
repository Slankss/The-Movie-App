package com.okankkl.themovieapp.view

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.ui.theme.LightBlue
import com.okankkl.themovieapp.util.Util
import com.okankkl.themovieapp.viewModel.ListViewModel
import androidx.compose.runtime.*
import com.okankkl.themovieapp.enum_sealed.Categories
import com.okankkl.themovieapp.enum_sealed.DataType
import com.okankkl.themovieapp.components.*
import com.okankkl.themovieapp.model.TvSeries

@Composable
fun MovieList(navController: NavController,listViewModel: ListViewModel){

    val popularMovies = listViewModel.popularMovies.collectAsState()
    val trendMovies = listViewModel.trendMovies.collectAsState()
    val topRatedMovies = listViewModel.topRatedMovies.collectAsState()
    val nowPlayingMovies = listViewModel.nowPlayingMovies.collectAsState()

    fun currentList(moviesType: Categories) : Resources?{
        return when(moviesType){
            Categories.Popular -> popularMovies.value
            Categories.Trending -> trendMovies.value
            Categories.TopRated -> topRatedMovies.value
            Categories.NowPlaying -> nowPlayingMovies.value
            else -> null
        }
    }

    SideEffect {
        listViewModel.getMovies()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Categories.values().forEach { type ->
            val currentState = currentList(type)
            if(currentState != null){

                when(currentState){
                    is Resources.Loading -> Loading()
                    is Resources.Success -> {
                        if(type == Categories.Trending){
                            Log.w("arabam",currentState.data.toString())
                            TrendMovies(
                                movies = currentState.data as List<Movie> ,
                                navController = navController
                            )
                        }
                        else{
                            MovieList(
                                movieList = currentState.data as List<Movie>,
                                moviesType = type,
                                navController)
                        }
                    }
                    is Resources.Failed -> {
                        Failed(
                            errorMsg = currentState.errorMsg
                        )
                    }
                }
            }
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrendMovies(movies : List<Movie>,navController: NavController){

    var time by remember { mutableStateOf(0) }

    var pageState = rememberPagerState(
        pageCount = {
            movies.size
        },
        initialPage = 0,

    )

    LaunchedEffect(key1 = time, block = {
        /*
        while(true){
            if(time == 5){
                pageState.animateScrollToPage(pageState.currentPage +1)
                time = 0
            }
            delay(1000L)
            time++
        }

         */
    })
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
    ) {
        HorizontalPager(
            state = pageState,
            modifier = Modifier.fillMaxWidth()
        )
        { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .shadow(
                        elevation = 4.dp,
                        shape = RoundedCornerShape(12.dp)
                    ),
            ){
                AsyncImage(
                    model = Util.IMAGE_BASE_URL +movies[page].backdropPath,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            navController.navigate("${Pages.MovieDetail.route}/${movies[page].id}")
                        },
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = movies[page].title,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .background(
                            color = Color(0x4D012022)
                        )
                        .fillMaxWidth()
                        .padding(5.dp),
                    style = MaterialTheme.typography.headlineLarge.copy(

                    ),
                    )
            }
        }
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 10.dp, top = 20.dp),
            horizontalArrangement = Arrangement.Center
        ){
            repeat(pageState.pageCount) { iteration ->
                val color = if(pageState.currentPage == iteration) LightBlue else Color(0x24FFFFFF)
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(CircleShape)
                        .background(
                            color = color
                        )
                        .size(8.dp)
                )
            }
        }
    }
}

@Composable
fun MovieList(movieList : List<Movie>,moviesType : Categories, navController: NavController){

    Column(
        modifier = Modifier
            .padding(bottom = 10.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ){
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 15.dp),
                text = moviesType.title,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 16.sp
                )
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp)
                    .clickable {
                        navController.navigate("${Pages.ViewAll.route}/${DataType.Movie().title}&${moviesType.title}")
                    },
                text = "view all",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 14.sp,
                    color = Color(0xB3FFFFFF)
                )
            )
        }
        LazyRow(
            modifier = Modifier
        ){
            itemsIndexed(movieList){index ,movie ->
                if(movie.posterPath != null && movie.posterPath!!.isNotEmpty()){
                    Poster(
                        posterPath = movie.posterPath!!,
                        id = movie.id,
                        modifier = Modifier
                            .height(150.dp)
                            .padding(
                                start = if(index == 0) 15.dp else 0.dp,
                                end = 15.dp
                            )

                    ){ movieId ->
                        navController.navigate("${Pages.MovieDetail.route}/${movieId}")
                    }
                }
            }
        }
    }
}
