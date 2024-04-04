package com.okankkl.themovieapp.presentation.favourite_screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.presentation.components.LoadingUi
import com.okankkl.themovieapp.presentation.components.ContentPoster
import com.okankkl.themovieapp.presentation.components.TopMenuItem
import com.okankkl.themovieapp.presentation.DisplayType
import com.okankkl.themovieapp.presentation.Pages
import com.okankkl.themovieapp.extensions.convertDate
import com.okankkl.themovieapp.domain.model.Favourite
import com.okankkl.themovieapp.presentation.components.ErrorUi
import com.okankkl.themovieapp.presentation.favourite_screen.components.FavouriteItem
import com.okankkl.themovieapp.ui.theme.OceanPalet4
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Favourites(navController: NavController,favouritesViewModel: FavouritesViewModel = hiltViewModel()){
    
    val state = favouritesViewModel.favourites.collectAsState().value

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { DisplayType.values().size })
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }

    LaunchedEffect(key1 = true){
        pagerState.scrollToPage(0)
        favouritesViewModel.getFavourites(DisplayType.Movie)
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {

       Row(
          modifier = Modifier
              .padding(start = 20.dp,top = 20.dp, bottom = 20.dp),
           horizontalArrangement = Arrangement.spacedBy(20.dp),
           verticalAlignment = Alignment.CenterVertically
       ){
           DisplayType.values().forEachIndexed { index, page ->
               val isSelected = selectedTabIndex.value == index
               TopMenuItem(displayType = page, isSelected = isSelected) {
                   scope.launch {
                       pagerState.animateScrollToPage(
                           page.ordinal,
                           animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
                       )
                       favouritesViewModel.getFavourites(page)
                   }
               }
           }
       }
        Box(modifier = Modifier.fillMaxSize()){
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState,
                userScrollEnabled = false
            ) { pageIndex ->
                when(state.data){
                    null -> LoadingUi(modifier = Modifier.fillMaxSize())
                    else -> when(state.data!!.isEmpty()){
                        true -> ErrorUi(errorMsg = state.message)
                        false ->FavouriteList(favouritesViewModel = favouritesViewModel,favouriteList = state.data!!, navController = navController,
                            DisplayType.values()[selectedTabIndex.value])
                    }
                }
            }
            if(state.is_loading){
                LoadingUi(modifier = Modifier.align(Alignment.Center))
            }
            if(state.message.isNotBlank()){
                ErrorUi(errorMsg = state.message)
            }
        }
        
        
    }
}
@Composable
fun FavouriteList(favouritesViewModel: FavouritesViewModel, favouriteList : List<Favourite>, navController: NavController, displayType: DisplayType){
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        items(favouriteList){ favourite ->
            FavouriteItem(
                favourite = favourite,
                onClick = {
                    val route = "${Pages.DisplayDetail.route}/${favourite.contentId}&${favourite.type}"
                    navController.navigate(route)
                },
                onDeleteClick = {
                    favouritesViewModel.deleleteFavourite(favourite,displayType)
                }
            )
        }
    }
}





