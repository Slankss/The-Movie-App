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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.components.Credit
import com.okankkl.themovieapp.components.GenreBox
import com.okankkl.themovieapp.components.Loading
import com.okankkl.themovieapp.components.Poster
import com.okankkl.themovieapp.components.YouTubePlayer
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.extensions.convertDate
import com.okankkl.themovieapp.model.Display
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.TvSeries
import com.okankkl.themovieapp.model.Videos
import com.okankkl.themovieapp.ui.theme.OceanPalet4
import com.okankkl.themovieapp.ui.theme.StatusBarColor
import com.okankkl.themovieapp.util.Util.IMAGE_BASE_URL
import com.okankkl.themovieapp.viewModel.DisplayDetailViewModel
import java.time.LocalDate

@Composable
fun DisplayDetail(navController: NavController, movieId : Int?, displayType : String?)
{
    val displayViewModel : DisplayDetailViewModel = hiltViewModel()
    val display = displayViewModel.display.collectAsState()
    val similarDisplays = displayViewModel.similarDisplays.collectAsState()

    LaunchedEffect(key1 = true){
        if(movieId != null && displayType != null){
            displayViewModel.getDisplay(movieId,displayType)
            displayViewModel.getFavourite(movieId,displayType)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
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

            if(display.value is Resources.Success){
                val data = (display.value as Resources.Success).data as Display
                Trailer(
                    videos = data.videos,
                    backdropPath = data.backdropPath
                )
                if(data is Movie){
                    MovieContent(movie = data,displayType!!,displayViewModel)
                }
                else if(data is TvSeries){
                    TvSeriesContent(tvSeries = data,displayType!!,displayViewModel = displayViewModel)
                }
            }
            if(similarDisplays.value.isNotEmpty()){
                SimilarDisplays(
                    similarDisplay = similarDisplays.value,
                    navController = navController,
                    displayType = displayType!!
                )
            }
        }
        if(display.value is Resources.Loading) Loading(modifier = Modifier.align(Alignment.Center))
    }
}


@Composable
fun Trailer(videos: Videos?,backdropPath : String?){
    Box(
        modifier = Modifier
            .fillMaxWidth()
        ,
    ){
        videos?.apply {
            val trailer = results.firstOrNull{ it.type == "Trailer"}
                ?: results.firstOrNull()

            trailer?.let { video ->
                YouTubePlayer(videoId = video.key, lifecycleOwner = LocalLifecycleOwner.current)
            }  ?: Box(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                AsyncImage(
                    model = IMAGE_BASE_URL+backdropPath,
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
fun MovieContent(movie: Movie, displayType : String, displayViewModel: DisplayDetailViewModel){

    val favouriteState = displayViewModel.favouriteState.collectAsState()
    val titleStyle = MaterialTheme.typography.labelLarge.copy(
        fontSize = 24.sp,
        lineHeight = 32.sp
    )
    val subTitleStyle = MaterialTheme.typography.bodyLarge.copy(
        fontSize = 16.sp,
        color = Color(0xFFFFFFFF)
    )

    Column(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = movie.en_title,
            style = titleStyle,
            modifier = Modifier
                .padding(bottom = 10.dp)
        )

        FlowRow(
            maxItemsInEachRow = 3,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = null,
                    tint = Color(0xFFBBBBBB)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp),
                    text = "${movie.runtime} minutes",
                    color = Color(0xFFBCBCBC)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.filled_star),
                    contentDescription = null,
                    tint = Color(0xFFBBBBBB),
                )
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp),
                    text = "${String.format("%.1f",movie.voteAverage)} (TMDB)",
                    color = Color(0xFFBCBCBC)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = Color(0xFFBBBBBB),
                    modifier = Modifier
                )
                Text(
                    text = convertDate(movie.releaseDate),
                    color = Color(0xFFBCBCBC),
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }
        }

        Divider(
            modifier = Modifier
                .background(color = Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Genres",
            style = subTitleStyle
        )

        FlowRow(
            maxItemsInEachRow = 3,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            movie.genres.forEach { genre ->
                GenreBox(
                    modifier = Modifier
                        .padding(end = 15.dp),
                    genreName = genre.name
                )
            }
        }

        Divider(
            modifier = Modifier
                .background(color = Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Overview",
            style = subTitleStyle,
            modifier = Modifier
        )
        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                color = Color(0xFFBCBCBC),
                textAlign = TextAlign.Justify
            )
        )

        AddFavourite(
            modifier = Modifier
                .align(Alignment.End),
            favouriteState = favouriteState.value == null
        ) {
            if (favouriteState.value == null)
                displayViewModel.addFavourite(movie,displayType)
            else
                displayViewModel.deleteFavourite(movie,displayType)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TvSeriesContent(tvSeries: TvSeries,displayType: String,displayViewModel : DisplayDetailViewModel){

    val favouriteState = displayViewModel.favouriteState.collectAsState()
    val titleStyle = MaterialTheme.typography.labelLarge.copy(
        fontSize = 24.sp,
        lineHeight = 32.sp
    )
    val subTitleStyle = MaterialTheme.typography.bodyLarge.copy(
        fontSize = 16.sp,
        color = Color(0xFFFFFFFF)
    )

    Column(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = tvSeries.en_title,
            style = titleStyle,
            modifier = Modifier
                .padding(bottom = 10.dp)
        )

        FlowRow(
            maxItemsInEachRow = 3,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Row(
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
            }
            Row(
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
                    text = "${tvSeries.numberOfEpisodes} episodes",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 13.sp,
                        color = Color(0xFFBCBCBC)
                    ),
                    color = Color(0xFFBCBCBC)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = R.drawable.filled_star),
                    contentDescription = null,
                    tint = Color(0xFFBBBBBB)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp),
                    text = "${String.format("%.1f",tvSeries.voteAverage)} (TMDB)",
                    color = Color(0xFFBCBCBC),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 13.sp,
                        color = Color(0xFFBCBCBC)
                    )
                )
            }
            Row(
               verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "date",
                    tint = Color(0xFFBBBBBB),
                    modifier = Modifier
                        .padding(end = 5.dp)
                )
                if(tvSeries.releaseDate.isNotEmpty()){
                    Text(
                        text = convertDate(tvSeries.releaseDate),
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
        }


        Divider(
            modifier = Modifier
                .background(color = Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Genre",
            style = subTitleStyle,
            modifier = Modifier
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

        Divider(
            modifier = Modifier
                .background(color = Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )

        if(tvSeries.createdBy.isNotEmpty()){
            Credit(createdtBy = tvSeries.createdBy)
            Divider(
                modifier = Modifier
                    .background(color = Color.White)
                    .height(1.dp)
                    .fillMaxWidth()
            )
        }

        if(tvSeries.overview.isNotEmpty()){
            Text(
                text = "Overview",
                style = subTitleStyle,
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

        AddFavourite(
            modifier = Modifier
                .align(Alignment.End),
            favouriteState = favouriteState.value == null
        ) {
            if (favouriteState.value == null)
                displayViewModel.addFavourite(tvSeries,displayType)
            else
                displayViewModel.deleteFavourite(tvSeries,displayType)
        }
    }
}

@Composable
fun AddFavourite(modifier : Modifier,favouriteState : Boolean,onClick: () -> Unit){
    Box(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
              onClick()
            },
    ){
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = if(favouriteState) "Favorilere Ekle" else "Favorilerden Çıkar",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 14.sp,
                    color = Color(0x99FFFFFF)
                ),
                modifier = Modifier
            )
            Icon(
                painter = if(favouriteState) painterResource(id = R.drawable.ic_fav_unselected) else painterResource(
                    id = R.drawable.ic_fav_selected
                ),
                contentDescription = null,
                tint = if(favouriteState) Color.White else OceanPalet4,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(16.dp)

            )

        }
    }
}

@Composable
fun SimilarDisplays(similarDisplay : List<Display>, navController: NavController,displayType: String){

    Text(
        text = "Similar movies",
        style = MaterialTheme.typography.labelLarge.copy(
            fontSize = 16.sp
        ),
        modifier = Modifier
            .padding(bottom = 15.dp, start = 15.dp)
    )

    LazyRow(
        modifier = Modifier
    ){
        val filterSimilarList = similarDisplay
            .filter { it.backdropPath != null && it.backdropPath!!.isNotEmpty()}

        itemsIndexed(filterSimilarList){index,display ->

            SimilarDisplay(
                display = display,
                index = index
            ){ id ->
                navController.navigate("${Pages.DisplayDetail.route}/${id}&$displayType")
            }

        }
    }
}

@Composable
fun SimilarDisplay(display: Display, index : Int, onClick : (Int) -> Unit){

    Column(
        modifier = Modifier
            .padding(
                start = if(index == 0) 15.dp else 0.dp,
                end = 15.dp,
                bottom = 10.dp
            )
    ){
        Poster(
            posterPath = display.backdropPath!!,
            id = display.id,
            modifier = Modifier
                .size(100.dp)

        ){
             onClick(it)
        }

        Text(
            modifier = Modifier
                .width(100.dp)
                .padding(
                    top = 10.dp
                ),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )

                ){
                    append(display.en_title)
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFBCBCBC)
                    )
                ){

                    if( display.releaseDate != null && display.releaseDate.isNotEmpty()){
                        val date = LocalDate.parse(display.releaseDate)
                        val year = date.year.toString()
                        append(" ($year)")
                    }

                }
            }
        )


    }

}





