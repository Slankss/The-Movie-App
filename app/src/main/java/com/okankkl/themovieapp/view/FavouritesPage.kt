package com.okankkl.themovieapp.view

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.ExperimentalMaterialApi
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
import com.okankkl.themovieapp.components.Loading
import com.okankkl.themovieapp.components.Poster
import com.okankkl.themovieapp.components.TopMenuItem
import com.okankkl.themovieapp.enum_sealed.DisplayType
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.extensions.convertDate
import com.okankkl.themovieapp.model.Favourite
import com.okankkl.themovieapp.ui.theme.OceanPalet4
import com.okankkl.themovieapp.viewModel.FavouritesViewModel
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

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
                       favouriteViewModel.getFavourites(page)
                       pagerState.animateScrollToPage(
                           page.ordinal,
                           animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
                       )
                   }
               }
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
                        EmptyPage(
                            name = DisplayType.values()[selectedTabIndex.value].title
                        )
                    } else {
                        FavouriteList(favouritesViewModel = favouriteViewModel,favouriteList = favourites.value!!, navController = navController,DisplayType.values()[selectedTabIndex.value])
                    }
                }
            }
        }

    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavouriteList(favouritesViewModel: FavouritesViewModel,favouriteList : List<Favourite>,navController: NavController,displayType: DisplayType){

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
                    val route = "${Pages.DisplayDetail.route}/${favourite.id}&${favourite.type}"
                    navController.navigate(route)
                },
                onDeleteClick = {
                    favouritesViewModel.deleleteFavourite(favourite,displayType)
                }
            )
        }
    }

}

@Composable
fun FavouriteItem(favourite : Favourite,onClick : (Int) -> Unit,onDeleteClick : () -> Unit){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF0D1321),
                        Color(0xFF303B4A)
                    )
                ),
                shape = RoundedCornerShape(14.dp)
            )
            .clip(RoundedCornerShape(14.dp))
            .border(
                width = 1.dp,
                color = Color(0x0DFFFFFF),
                shape = RoundedCornerShape(14.dp)
            )
    ){
        val delete = SwipeAction(
            onSwipe = {
                onDeleteClick()
            },
            icon = {
                Icon(
                    modifier = Modifier.padding(start = 25.dp),
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = null,
                    tint = Color(0xFFFFFFFF)
                )
            },
            background = Color(0xfff64848),

        )
        SwipeableActionsBox(
            endActions = listOf(delete),
            swipeThreshold = 150.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Poster(
                    posterPath = favourite.posterPath,
                    id = favourite.contentId,
                    modifier = Modifier
                        .height(150.dp)
                        .weight(1f)
                        .shadow(
                            elevation = 4.dp,
                            ambientColor = OceanPalet4,
                            shape = RoundedCornerShape(14.dp)
                        )
                ){ movieId ->
                    onClick(movieId)
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxHeight()
                        .weight(2f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 5.dp),
                        text = favourite.title,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 16.sp,
                            color = Color(0xB3FFFFFF)
                        )
                    )
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0x0DFAF0CA),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clip(RoundedCornerShape(14.dp))
                    ){
                        Row(
                            modifier = Modifier
                                .padding(7.dp)
                        ){
                            Icon(
                                painter = painterResource(id = R.drawable.filled_star),
                                contentDescription = "star",
                                tint = OceanPalet4
                            )
                            Text(
                                modifier = Modifier
                                    .padding(start = 5.dp),
                                text = String.format("%.1f",favourite.imdb),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 12.sp,
                                    color = Color(0xB3FFFFFF)
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = "date",
                            tint = Color(0xB3FFFFFF)
                        )
                        val date = convertDate(favourite.date)
                        Text(
                            modifier = Modifier
                                .padding(start = 5.dp),
                            text = date,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 12.sp,
                                color = Color(0xB3FFFFFF)
                            )
                        )

                    }
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontSize = 12.sp,
                                    color = Color(0x4DFFFFFF)
                                )
                            ){
                                append(
                                    text = if(favourite.type == DisplayType.Movie.path) "Time: " else
                                        ""
                                )
                            }
                            withStyle(
                                SpanStyle(
                                    fontSize = 12.sp,
                                    color = Color(0xB3FFFFFF)
                                )
                            ){
                                append(favourite.time)
                            }
                        }
                    )
                }
            }
        }

    }


}

@Composable
fun EmptyPage(name : String){

    val lowerCase = name.lowercase()

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ){
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "There are no $lowerCase in your favorites.",
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp,
                color = Color(0xB3FFFFFF)
            )
        )
    }

}



