package com.okankkl.themovieapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.okankkl.themovieapp.enum_sealed.DataType
import com.okankkl.themovieapp.viewModel.ViewAllViewModel
import com.okankkl.themovieapp.components.Loading
import com.okankkl.themovieapp.components.Failed
import com.okankkl.themovieapp.model.Movie
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.okankkl.themovieapp.components.Poster


@Composable
fun ViewAll(navController: NavController,dataType : String?,category : String?){

    val viewAllViewModel : ViewAllViewModel = hiltViewModel()
    val contentList = viewAllViewModel.contentList.collectAsState()
    var pagingItems = viewAllViewModel.displayState.collectAsLazyPagingItems()


    LaunchedEffect(key1 = true){
        if(dataType != null && category != null){
            //viewAllViewModel.getContent(dataType,category,1)
            viewAllViewModel.LoadDisplay()
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxSize()
    ){
        when(dataType){
            DataType.Movie().title ->
            {
                MoviePages(
                    moviesPagingItems = pagingItems as LazyPagingItems<Movie>
                )
            }
            DataType.TvSeries().title ->
            {
                /*
                MoviePages(
                    moviesPagingItems = pagingItems as LazyPagingItems<TvSeries>
                )
                 */
            }
            else -> Failed(errorMsg = "Error")
        }
    }
}

@Composable
fun MoviePages(moviesPagingItems: LazyPagingItems<Movie>){

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
                ){
                
                }
            }
           
        }
        moviesPagingItems.apply {
            when{
                loadState.refresh is LoadState.Loading -> {
                    item{ Loading() }
                }
                loadState.refresh is LoadState.Error -> {
                    val error = moviesPagingItems.loadState.refresh as LoadState.Error
                    item{
                        Failed(errorMsg =  error.error.localizedMessage!!)
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item{ Loading() }
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

