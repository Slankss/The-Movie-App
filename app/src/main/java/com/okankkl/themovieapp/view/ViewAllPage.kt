package com.okankkl.themovieapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.okankkl.themovieapp.enum_sealed.DataType
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.viewModel.ViewAllViewModel
import com.okankkl.themovieapp.components.*
import com.okankkl.themovieapp.extensions.capitalizeWords
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.TvSeries
import com.okankkl.themovieapp.enum_sealed.Pages
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun ViewAll(navController: NavController,dataType : String?,category : String?){

    val viewAllViewModel : ViewAllViewModel = hiltViewModel()
    val contentList = viewAllViewModel.contentList.collectAsState()


    LaunchedEffect(key1 = true){
        if(dataType != null && category != null){
            viewAllViewModel.getContent(dataType,category,1)
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .fillMaxSize()
    ){
        when(contentList.value){
            is Resources.Loading -> Loading()
            is Resources.Success -> {
                Success(
                    data = (contentList.value as Resources.Success).data as List<Any>,
                    dataType = dataType!!,
                    category = category!!,
                    navController = navController,
                    viewAllViewModel = viewAllViewModel
                )
            }
            is Resources.Failed -> {
                Failed(
                    errorMsg = (contentList.value as Resources.Failed).errorMsg
                )
            }
        }
    }

}

@Composable
fun Success(data : List<Any>,dataType: String,category: String,navController: NavController,
            viewAllViewModel: ViewAllViewModel){

    var capitalizeDataType = dataType.capitalizeWords()
    val header = "$category $capitalizeDataType"
    Text(
        text = header,
        style = MaterialTheme.typography.headlineLarge.copy(
            fontSize = 20.sp
        ),
        modifier = Modifier
            .padding(top = 20.dp)
    )

    when(dataType){
        DataType.Movie().name -> {
            AllMovieList(
                movieList = data as List<Movie>,
                navController = navController,
                viewAllViewModel = viewAllViewModel
            )
        }
        DataType.TvSeries().name -> {
            AllTvSeriesList(
                tvSeriesList = data as List<TvSeries>,
                navController = navController,
                viewAllViewModel = viewAllViewModel
            )
        }
    }

}

@Composable
fun AllMovieList(movieList : List<Movie>,navController: NavController,viewAllViewModel: ViewAllViewModel){

    val filteredList = movieList.filter { it.posterPath != null && it.posterPath!!.isNotEmpty()}
    val page by remember { mutableStateOf(1) }
    val lastIndex = filteredList.lastIndex
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .padding(top = 25.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        itemsIndexed(movieList){ index,movie ->
            Poster(
                posterPath = movie.posterPath!!,
                id = movie.id,
                modifier = Modifier
                    .height(150.dp)
            ){ id ->
                navController.navigate(Pages.MovieDetail.route+"/"+id)
            }
        }
    }

}

@Composable
fun AllTvSeriesList(tvSeriesList : List<TvSeries>,navController: NavController,
                    viewAllViewModel: ViewAllViewModel){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .padding(top = 25.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        items(tvSeriesList){ tvSeries ->
            if(tvSeries.posterPath != null && tvSeries.posterPath!!.isNotEmpty()){
                Poster(
                    posterPath = tvSeries.posterPath!!,
                    id = tvSeries.id,
                    modifier = Modifier
                        .height(150.dp)
                ){ id ->
                    navController.navigate(Pages.TvSeriesDetail.route+"/"+id)
                }
            }

        }
    }
}

