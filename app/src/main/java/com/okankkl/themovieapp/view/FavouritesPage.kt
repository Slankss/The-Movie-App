package com.okankkl.themovieapp.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.okankkl.themovieapp.components.Loading
import com.okankkl.themovieapp.components.Poster
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.model.Favourite
import com.okankkl.themovieapp.ui.theme.OceanPalet4
import com.okankkl.themovieapp.viewModel.FavouritesViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Favourites(navController: NavController){

    var favouriteViewModel : FavouritesViewModel = hiltViewModel()
    var favourites = favouriteViewModel.favourites.collectAsState()


    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { DisplayType.values().size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    LaunchedEffect(key1 = true){
        favouriteViewModel.getFavourites(DisplayType.Movie)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier
                .padding(bottom = 20.dp)
        ){
            DisplayType.values().forEachIndexed { index, page ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = OceanPalet4,
                    unselectedContentColor = Color.White,
                    onClick = {
                        scope.launch {
                            favouriteViewModel.getFavourites(page)
                            pagerState.animateScrollToPage(
                                page.ordinal,
                                animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
                            )
                        }
                    },
                    text = {
                        Text(
                            text = page.title,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 16.sp
                            )
                        )
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) { pageIndex ->
            when(favourites.value){
                null -> {
                    Loading(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                else -> {
                    if(favourites.value!!.isEmpty()){
                        EmptyPage()
                    } else {
                        FavouriteList(favouriteList = favourites.value!!, navController = navController)
                    }
                }
            }
        }

    }

}

@Composable
fun FavouriteList(favouriteList : List<Favourite>,navController: NavController){

    LazyVerticalGrid(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){

        items(favouriteList){ favourite ->
           FavouriteItem(favourite = favourite){
               val route = when(favourite.type){
                   DisplayType.Movie.path -> Pages.MovieDetail.route
                   else -> Pages.TvSeriesDetail.route
               } + "/" + favourite.contentId
               navController.navigate(route)
           }
        }
    }

}

@Composable
fun FavouriteItem(favourite : Favourite,onClick : (Int) -> Unit){
    Column(
        modifier = Modifier
            .padding(bottom = 10.dp)
    ) {
        Poster(
            posterPath = favourite.posterPath,
            id = favourite.contentId,
            modifier = Modifier.height(150.dp)
        ){ movieId ->
            onClick(movieId)
        }

        Text(
            modifier = Modifier
                .padding(top = 5.dp),
            text = favourite.title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 13.sp,
                color = Color(0xB3FFFFFF)
            )
        )
    }
}

@Composable
fun EmptyPage(){

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        Text(
            text = "Favorilerinizde hiç film bulunmamaktadır.",
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp,
                color = Color(0xB3FFFFFF)
            )
        )
    }

}



