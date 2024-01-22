package com.okankkl.themovieapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.components.Poster
import com.okankkl.themovieapp.components.SearchTextField
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.model.Search
import com.okankkl.themovieapp.viewModel.SearchViewModel

@Composable
fun SearchPage(navController: NavController,onMessage :(String) -> Unit){

    val searchViewModel : SearchViewModel = hiltViewModel()
    val searchList = searchViewModel.searchList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBar(searchViewModel){ message ->
            onMessage(message)
        }
        SearchList(searchList = searchList.value, navController = navController)
    }

}

@Composable
fun SearchBar(searchViewModel: SearchViewModel,onMessage: (String) -> Unit){

    var search by remember { mutableStateOf("") }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 20.dp),

        ){
        val (searchField,favourites) = createRefs()
        SearchTextField(
            modifier = Modifier
                .height(40.dp)
                .constrainAs(searchField) {
                    top.linkTo(parent.top)
                    end.linkTo(favourites.start, margin = 10.dp)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints

                },
            hint = "What are you looking for?",
            value = search,
            onValueChange = {
                search = it
            }
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .constrainAs(favourites) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .background(
                    color = Color(0x24FFFFFF),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    if (search.isNotEmpty() || search.trim().length >= 2)
                    {
                        searchViewModel.search(query = search)
                    } else
                    {
                        onMessage("Search query must be more than two characters!")
                    }
                }
        ){
            Icon(
                painterResource(id = R.drawable.ic_search),
                tint = Color.White,
                contentDescription = "Search",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(5.dp)
                    .size(24.dp)

            )
        }

    }

}

@Composable
fun SearchList(searchList : List<Search>, navController: NavController){

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp,end = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        var filteredList = searchList.filter { it.posterPath != null }
        items(filteredList){search ->

            if(search.posterPath != null){
                Poster(
                    posterPath = search.posterPath!!,
                    id = search.id,
                    modifier = Modifier
                        .height(150.dp)
                ){ searchId ->
                    when(search.mediaType){
                        DisplayType.Movie.path -> navController.navigate("${Pages.MovieDetail.route}/${searchId}")
                        DisplayType.TvSeries.path ->  navController.navigate("${Pages.TvSeriesDetail.route}/${searchId}")
                    }
                }
            }
        }
    }

}