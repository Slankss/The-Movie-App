package com.okankkl.themovieapp.presentation.content_detail_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.okankkl.themovieapp.presentation.components.LoadingUi
import com.okankkl.themovieapp.presentation.DisplayType
import com.okankkl.themovieapp.domain.model.Movie
import com.okankkl.themovieapp.domain.model.TvSeries
import com.okankkl.themovieapp.presentation.components.ErrorUi
import com.okankkl.themovieapp.presentation.content_detail_screen.components.MovieContent
import com.okankkl.themovieapp.presentation.content_detail_screen.components.SimilarContents
import com.okankkl.themovieapp.presentation.content_detail_screen.components.Trailer
import com.okankkl.themovieapp.presentation.content_detail_screen.components.TvSeriesContent
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.ui.theme.OceanPalet4

@Composable
fun DisplayDetail(navController: NavController, contentId : Int?, displayType : String?,
                  contentDetailViewModel: ContentDetailViewModel = hiltViewModel())
{
    val state = contentDetailViewModel.contentState.collectAsState().value
    val similarContents = contentDetailViewModel.similarContents.collectAsState().value
    val favouriteState = contentDetailViewModel.favouriteState.collectAsState().value

    LaunchedEffect(key1 = true){
        if(contentId != null && displayType != null){
            if(displayType == DisplayType.Movie.path){
                contentDetailViewModel.getMovieDetail(contentId)
                contentDetailViewModel.getSimilarMovies(contentId)
            } else {
                contentDetailViewModel.getTvSeriesDetail(contentId)
                contentDetailViewModel.getSimilarTvSeries(contentId)
            }
            contentDetailViewModel.getFavourite(contentId,displayType)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        val data = state.data

        if(data != null){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ){
                Trailer(
                    videos = data.videos,
                    backdropPath = data.backdropPath
                )
                ContentHeader(title = data.title, favouriteState = favouriteState){
                    if (!favouriteState) {
                        contentDetailViewModel.addFavourite(
                            content = data,
                            displayType = when (data) {
                                is Movie -> DisplayType.Movie.path
                                else -> DisplayType.TvSeries.path
                            }
                        )
                    } else {
                        contentDetailViewModel.deleteFavourite(
                            content = data,
                            displayType = when (data) {
                                is Movie -> DisplayType.Movie.path
                                else -> DisplayType.TvSeries.path
                            }
                        )
                    }
                }
                when(data){
                    is Movie ->  MovieContent(movie = data)
                    is TvSeries -> TvSeriesContent(tvSeries = data )
                }

                if(similarContents.isNotEmpty()){
                    SimilarContents(
                        similarContent = similarContents,
                        navController = navController,
                        displayType = displayType!!
                    )
                }
            }
        }

        if(state.is_loading)
            LoadingUi(modifier = Modifier.align(Alignment.Center))
        if(state.message.isNotBlank())
            ErrorUi(errorMsg = state.message)
    }
}

@Composable
fun ContentHeader(title : String,favouriteState : Boolean,onClick : () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 15.dp, end = 15.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 24.sp,
                lineHeight = 32.sp
            ),
            modifier = Modifier.weight(4f)
        )
        Icon(
            painter = if(!favouriteState) painterResource(id = R.drawable.ic_fav_unselected) else painterResource(
                id = R.drawable.ic_fav_selected
            ),
            contentDescription = null,
            tint = if(!favouriteState) Color.White else OceanPalet4,
            modifier = Modifier
                .weight(1f)
                .size(20.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onClick()
                }
        )
    }
}







