package com.okankkl.themovieapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.okankkl.themovieapp.model.TvSeries
import com.okankkl.themovieapp.viewModel.ViewAllViewModel

@Composable
fun ViewAll(navController: NavController, displayType : String?, category : String?){

    val viewAllViewModel : ViewAllViewModel = hiltViewModel()
    LaunchedEffect(key1 = true){
        if(displayType != null && category != null){
            viewAllViewModel.load(displayType,category)
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxSize()
    ){
        if(displayType != null && category != null){
            Text(
                text = viewAllViewModel.getCategory(category).title + " " + viewAllViewModel.getType(displayType).title,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 20.sp
                ),
                modifier = Modifier
                    .padding(top = 20.dp)
            )
        }

        when(displayType){
            DisplayType.Movie.path -> MoviePages(viewAllViewModel,displayType,navController)
            DisplayType.TvSeries.path -> TvSeriesPages(viewAllViewModel,navController)
        }

    }

}

@Composable
fun MoviePages(viewAllViewModel: ViewAllViewModel,displayType: String,navController: NavController){
    
    val moviesPagingItems : LazyPagingItems<Movie> = viewAllViewModel.movieState.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .padding(top = 25.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        items(moviesPagingItems.itemCount){ index ->
            val movie = moviesPagingItems[index]
            if(movie!!.posterPath != null && movie.posterPath!!.isNotEmpty()){
                Poster(
                    posterPath = movie.posterPath!!,
                    id = movie.id,
                    modifier = Modifier
                        .height(150.dp)
                ){ movieId ->
                    navController.navigate("${Pages.DisplayDetail.route}/${movie.id}&$displayType")
                }
            }
            else{
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .background(Color(0x0F5A5A5A))
                        .clickable {
                            navController.navigate("${Pages.DisplayDetail.route}/${movie.id}&$displayType")
                        },
                )
            }
        }
        moviesPagingItems.apply {
            when{
                loadState.refresh is LoadState.Loading -> {
                    item{ Loading(Modifier) }
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

@Composable
fun TvSeriesPages(viewAllViewModel: ViewAllViewModel, navController: NavController){

    val tvSeriesPagingItems : LazyPagingItems<TvSeries> = viewAllViewModel.tvSeriesState.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .padding(top = 25.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){

        items(tvSeriesPagingItems.itemCount){ index ->
            val tvSeries = tvSeriesPagingItems[index]
            if(tvSeries!!.posterPath != null && tvSeries.posterPath!!.isNotEmpty()){
                Poster(
                    posterPath = tvSeries.posterPath!!,
                    id = tvSeries.id,
                    modifier = Modifier
                        .height(150.dp)
                ){ tvSeriesId ->
                    navController.navigate("${ Pages.DisplayDetail.route }/$tvSeriesId&${DisplayType.TvSeries.path}")
                }
            }
            else{
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .background(Color(0x0F5A5A5A))
                        .clickable {
                            navController.navigate("${Pages.DisplayDetail.route}/${tvSeries.id}&${tvSeries.mediaType}")
                        },
                )
            }

        }
        tvSeriesPagingItems.apply {
            when{
                loadState.refresh is LoadState.Loading -> {
                    item{ Loading(Modifier) }
                }
                loadState.refresh is LoadState.Error -> {
                    val error = tvSeriesPagingItems.loadState.refresh as LoadState.Error
                    item{
                        Failed(errorMsg =  error.error.localizedMessage!!)
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item{ Loading(Modifier) }
                }
                loadState.append is LoadState.Error -> {
                    val error = tvSeriesPagingItems.loadState.append as LoadState.Error
                    item{
                        Failed(errorMsg =  error.error.localizedMessage!!)
                    }
                }
            }
        }

    }

}

