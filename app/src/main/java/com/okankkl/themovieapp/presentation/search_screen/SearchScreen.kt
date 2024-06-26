package com.okankkl.themovieapp.presentation.search_screen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.okankkl.themovieapp.presentation.components.ErrorUi
import com.okankkl.themovieapp.presentation.components.LoadingUi
import com.okankkl.themovieapp.presentation.components.ContentPoster
import com.okankkl.themovieapp.presentation.Pages
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.presentation.search_screen.components.SearchBar

@Composable
fun SearchPage(
    navController: NavController, searchViewModel: SearchViewModel = hiltViewModel(),
    onMessage: (String) -> Unit
) {

    val state = searchViewModel.state.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBar { query ->
            searchViewModel.search(query)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SearchList(
                searchList = state.data,
                navController = navController
            )
            if (state.is_loading) {
                LoadingUi(modifier = Modifier.align(Alignment.Center))
            }
            if (state.message.isNotBlank()) {
                ErrorUi(errorMsg = state.message)
            }
        }
    }
}

@Composable
fun SearchList(searchList: List<Content>, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        val filteredList = searchList.filter { it.posterPath != null }
        items(filteredList) { search ->
            if (search.posterPath != null) {
                ContentPoster(
                    posterPath = search.posterPath!!,
                    id = search.id,
                    modifier = Modifier
                        .height(150.dp)
                ) { searchId ->
                    navController.navigate("${Pages.DisplayDetail.route}/${searchId}&${search.mediaType}")
                }
            }
        }
    }
}