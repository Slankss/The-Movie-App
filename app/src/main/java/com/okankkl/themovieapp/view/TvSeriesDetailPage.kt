package com.okankkl.themovieapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.okankkl.themovieapp.viewModel.TvSeriesDetailViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.components.YouTubePlayer
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.TvSeries
import com.okankkl.themovieapp.util.Util
import com.okankkl.themovieapp.components.*
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.model.CreatedBy
import java.time.LocalDate
import com.okankkl.themovieapp.extensions.*
import com.okankkl.themovieapp.ui.theme.OceanPalet4
import com.okankkl.themovieapp.ui.theme.StatusBarColor

@Composable
fun TvSeriesDetail(navController : NavController,tvSeriesId : Int?){

    val tvSeriesViewModel : TvSeriesDetailViewModel = hiltViewModel()
    val tvSeries = tvSeriesViewModel.tvSeries.collectAsState()
    val similarTvSeries = tvSeriesViewModel.similarTvSeries.collectAsState()

    SideEffect {
        if(tvSeriesId != null){
            tvSeriesViewModel.getTvSeries(tvSeriesId)
            tvSeriesViewModel.getFavourite(tvSeriesId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = StatusBarColor
                )
        ){
            Icon(
                painterResource(id = R.drawable.ic_back),
                contentDescription = "Return home page",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 20.dp, bottom = 10.dp)
                    .size(36.dp)
                    .blur(1.dp)
                    .clickable {
                        navController.popBackStack(Pages.Home.route, inclusive = false)
                    },
                tint = Color.LightGray
            )
        }

        when(tvSeries.value){
            is Resources.Loading -> Loading(Modifier)
            is Resources.Success -> {
                TvSeriesTrailer(
                    tvSeries = (tvSeries.value as Resources.Success).data as TvSeries
                )
                TvSeriesContent(
                    tvSeries = (tvSeries.value as Resources.Success).data as TvSeries,
                    tvSeriesViewModel = tvSeriesViewModel
                    )
            }
            is Resources.Failed -> {
                Failed(errorMsg = (tvSeries.value as Resources.Failed).errorMsg)
            }
        }
        when(similarTvSeries.value){
            is Resources.Loading -> Loading(Modifier)
            is Resources.Success -> {
                SimilarTvSeriesList(
                    similarTvSeries = (similarTvSeries.value as Resources.Success).data as List<TvSeries>,
                    navController = navController)
            }
            is Resources.Failed -> {
                Failed(errorMsg = (similarTvSeries.value as Resources.Failed).errorMsg)
            }
        }
    }

}@Composable
fun TvSeriesTrailer(tvSeries : TvSeries){
    Box(
        modifier = Modifier
            .fillMaxWidth()
        ,
    ){
        tvSeries.videos?.apply {
            results.firstOrNull { it.type == "Trailer"}?.let { video ->
                YouTubePlayer(videoId = video.key, lifecycleOwner = LocalLifecycleOwner.current)
            }
                ?: Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    AsyncImage(
                        model = Util.IMAGE_BASE_URL +tvSeries.backdropPath,
                        contentDescription = "Movie Poster",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(200.dp)
                    )
                }
        }

    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TvSeriesContent(tvSeries: TvSeries,tvSeriesViewModel : TvSeriesDetailViewModel){

    val favouriteState = tvSeriesViewModel.favouriteState.collectAsState()

    Column(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 25.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = tvSeries.name,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 24.sp,
                lineHeight = 32.sp,
            )
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                painter = painterResource(id = R.drawable.ic_tv_series_season),
                contentDescription = null,
                tint = Color(0xFFBBBBBB)
            )
            Text(
                modifier = Modifier
                    .padding(start = 5.dp),
                text = "${tvSeries.numberOfSeasons} season",
                color = Color(0xFFBCBCBC),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 13.sp,
                    color = Color(0xFFBCBCBC)
                )
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_tv_series_season),
                contentDescription = null,
                tint = Color(0xFFBBBBBB),
                modifier = Modifier
                    .padding(start = 20.dp)
            )
            Text(
                modifier = Modifier
                    .padding(start = 5.dp),
                text = "${tvSeries.numberOfEpisodes} episodes",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 13.sp,
                    color = Color(0xFFBCBCBC)
                ),
                color = Color(0xFFBCBCBC)
            )

            Icon(
                painter = painterResource(id = R.drawable.filled_star),
                contentDescription = null,
                tint = Color(0xFFBBBBBB),
                modifier = Modifier
                    .padding(start = 20.dp)
            )
            Text(
                modifier = Modifier
                    .padding(start = 5.dp),
                text = "${String.format("%.1f",tvSeries.voteAverage)} (IMDB)",
                color = Color(0xFFBCBCBC),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 13.sp,
                    color = Color(0xFFBCBCBC)
                )
            )
        }

        Row(
            modifier = Modifier
        ){
            Icon(
                painterResource(id = R.drawable.ic_calendar),
                contentDescription = "date",
                tint = Color(0xFFBBBBBB),
                modifier = Modifier
                    .padding(end = 5.dp)
            )
            if(tvSeries.firstAirDate.isNotEmpty()){
                Text(
                    text = convertDate(tvSeries.firstAirDate),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 13.sp,
                        color = Color(0xFFBCBCBC)
                    )
                )
            }
            if(tvSeries.lastAirDate.isNotEmpty()){
                Text(
                    text = " - "+convertDate(tvSeries.lastAirDate),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 13.sp,
                        color = Color(0xFFBCBCBC)
                    )
                )
            }
        }

        Divider(
            modifier = Modifier
                .background(color = Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier
        ) {
            Text(
                text = "Genre",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .padding(bottom= 10.dp)
            )
            FlowRow(
                maxItemsInEachRow = 3,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
            ) {
                tvSeries.genres.forEach { genre ->
                    GenreBox(
                        modifier = Modifier
                            .padding(end = 15.dp),
                        genreName = genre.name
                    )
                }
            }
        }

        Divider(
            modifier = Modifier
                .background(color = Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )

        if(tvSeries.createdBy.isNotEmpty()){
           Credit(createdtBy = tvSeries.createdBy)
        }


        if(tvSeries.overview.isNotEmpty()){
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "Overview",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .padding(bottom= 10.dp)
                )

                Text(
                    text = tvSeries.overview,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Light,
                        color = Color(0xFFBCBCBC),
                        textAlign = TextAlign.Justify
                    )
                )
            }

        }

        Box(
            modifier = Modifier
                .align(Alignment.End)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    if(favouriteState.value == null){
                        tvSeriesViewModel.addFavourite(tvSeries)
                    }
                    else{
                        tvSeriesViewModel.deleteFavourite(tvSeries)
                    }
                },
        ){
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = if(favouriteState.value == null) "Favorilere Ekle" else "Favorilerden Çıkar",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 14.sp,
                        color = Color(0x99FFFFFF)
                    ),
                    modifier = Modifier
                )
                Icon(
                    painter = if(favouriteState.value == null) painterResource(id = R.drawable.ic_fav_unselected) else painterResource(
                        id = R.drawable.ic_fav_selected
                    ),
                    contentDescription = null,
                    tint = if(favouriteState.value == null) Color.White else OceanPalet4,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(16.dp)

                )

            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Credit(createdtBy : List<CreatedBy>){

    Text(
        text = "Created By",
        style = MaterialTheme.typography.labelLarge.copy(
            fontSize = 16.sp
        ),
        modifier = Modifier
    )

    FlowRow(
        maxItemsInEachRow = 2,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
    ) {
        createdtBy.forEach { createdBy ->
            Row(
                modifier = Modifier
                    .padding(bottom = 10.dp, end = 25.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                if( createdBy.profilePath == null || createdBy.profilePath!!.isEmpty()){
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF363636))
                    )
                }
                else{
                    AsyncImage(
                        model = Util.IMAGE_BASE_URL +createdBy.profilePath,
                        contentDescription = "Movie Poster",
                        placeholder = painterResource(id = R.drawable.ic_launcher_background),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }

                Text(
                    text = createdBy.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.5.sp,
                        color = Color(0xFFBCBCBC)
                    ),
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }
        }
    }

    Divider(
        modifier = Modifier
            .background(color = Color.White)
            .height(1.dp)
            .fillMaxWidth()
    )

}

@Composable
fun SimilarTvSeriesList(similarTvSeries : List<TvSeries>,navController: NavController){

    Text(
        text = "Similar TV Series",
        style = MaterialTheme.typography.labelLarge.copy(
            fontSize = 16.sp
        ),
        modifier = Modifier
            .padding(start = 25.dp , end = 25.dp, bottom = 10.dp)
    )

    LazyRow(
        modifier = Modifier
            .padding()
    ){
        val filterSimilarList = similarTvSeries
            .filter { it.backdropPath != null && it.backdropPath!!.isNotEmpty()}

        itemsIndexed(filterSimilarList){   index,tvSeries ->
            if(tvSeries.backdropPath != null && tvSeries.backdropPath!!.isNotEmpty()){
                SimilarTvSeries(
                    tvSeries = tvSeries,
                    index = index
                ){ id ->
                    navController.navigate("${Pages.TvSeriesDetail.route}/${id}")
                }

            }
        }
    }
}

@Composable
fun SimilarTvSeries(tvSeries: TvSeries,index : Int,onClick : (Int) -> Unit){

    Column(
        modifier = Modifier
            .padding(
                start = if(index == 0) 20.dp else 0.dp,
                end = 20.dp,
                bottom = 10.dp
            )

    ){
        Poster(
            posterPath = tvSeries.backdropPath!!,
            id = tvSeries.id,
            modifier = Modifier
                .size(100.dp)
        ){
            onClick(it)
        }
        Text(
            modifier = Modifier
                .width(100.dp)
                .padding(top = 10.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )

                ){
                    append(tvSeries.name)
                }
                if(tvSeries.firstAirDate.isNotEmpty()){
                    withStyle(
                        style = SpanStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFBCBCBC)
                        )
                    ){
                        val date = LocalDate.parse(tvSeries.firstAirDate)
                        val year = date.year.toString()
                        append(" ($year)")
                    }
                }
            }
        )
    }
}