package com.okankkl.themovieapp.view


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.okankkl.themovieapp.R
import androidx.compose.runtime.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.okankkl.themovieapp.components.GenreBox
import com.okankkl.themovieapp.components.YouTubePlayer
import com.okankkl.themovieapp.enum_sealed.Pages
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.util.Util.IMAGE_BASE_URL
import com.okankkl.themovieapp.viewModel.DisplayDetailViewModel
import java.time.LocalDate
import com.okankkl.themovieapp.extensions.*
import com.okankkl.themovieapp.components.*
import com.okankkl.themovieapp.enum_sealed.DataType
import com.okankkl.themovieapp.ui.theme.StatusBarColor

@Composable
fun MovieDetail(navController: NavController,movieId : Int?)
{
    val displayDetailViewModel : DisplayDetailViewModel = hiltViewModel()
    val display = displayDetailViewModel.display.collectAsState()
    val similarMovies = displayDetailViewModel.similarDisplayList.collectAsState()

    SideEffect {
        if(movieId != null){
            displayDetailViewModel.getDisplay(DataType.Movie(),movieId)
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

        when(display.value){
            is Resources.Loading -> Loading()
            is Resources.Success -> {
                MovieTrailer(
                    movie = (display.value as Resources.Success).data as Movie
                )
                MovieContent(movie = (display.value as Resources.Success).data as Movie)
            }
            is Resources.Failed -> {
                Failed(errorMsg = (display.value as Resources.Failed).errorMsg)
            }
        }
        when(similarMovies.value){
            is Resources.Loading -> Loading()
            is Resources.Success -> {
                SimilarMovies(
                    similarMovies = (similarMovies.value as Resources.Success).data as List<Movie>,
                    navController = navController)
            }
            is Resources.Failed -> {
                Failed(errorMsg = (display.value as Resources.Failed).errorMsg)
            }
        }
    }
}


@Composable
fun MovieTrailer(movie: Movie ){
    Box(
        modifier = Modifier
            .fillMaxWidth()
        ,
    ){
        movie.videos?.apply {
            results.firstOrNull { it.type == "Trailer"}?.let { video ->
                YouTubePlayer(videoId = video.key, lifecycleOwner = LocalLifecycleOwner.current)
            }
                ?: Box(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                AsyncImage(
                    model = IMAGE_BASE_URL+movie.backdropPath,
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
fun MovieContent(movie: Movie){

    Column(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 25.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = movie.title,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 24.sp,
                lineHeight = 32.sp
            )
        )

        Row(
            modifier = Modifier,
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
                text = "${String.format("%.1f",movie.voteAverage)} (IMDB)",
                color = Color(0xFFBCBCBC)
            )
        }

        Divider(
            modifier = Modifier
                .background(color = Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier
        ){
            Column {
                ContentHeader(header = "Release date")
                Text(
                    text = convertDate(movie.releaseDate),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 12.sp,
                        color = Color(0xFFBCBCBC)
                    )
                )
            }
            Column {
                ContentHeader(header = "Genre")
                FlowRow(
                    maxItemsInEachRow = 2,
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
            }
        }

        Divider(
            modifier = Modifier
                .background(color = Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )

        Column {
            ContentHeader(header = "Overview")
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    color = Color(0xFFBCBCBC),
                    textAlign = TextAlign.Justify
                )
            )
        }
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ){
            Row(
                modifier = Modifier
                    .clickable {

                    },
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_favourite),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(16.dp)
                )
                Text(
                    text = "Favoriye Ekle",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 14.sp,
                        color = Color(0x99FFFFFF)
                    ),
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }
            Row(
                modifier = Modifier
                    .clickable {

                    },
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(18.dp)
                )
                Text(
                    text = "Paylaş",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 14.sp,
                        color = Color(0x99FFFFFF)
                    ),
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }
        }

    }
}

@Composable
fun ContentHeader(header : String){
    Text(
        text = header,
        style = MaterialTheme.typography.labelLarge.copy(
            fontSize = 16.sp
        ),
        modifier = Modifier
            .padding(bottom= 10.dp)
    )
}

@Composable
fun SimilarMovies(similarMovies : List<Movie>,navController: NavController){

    Text(
        text = "Similar movies",
        style = MaterialTheme.typography.labelLarge.copy(
            fontSize = 16.sp
        ),
        modifier = Modifier
            .padding(bottom = 15.dp, start = 25.dp,end = 25.dp)
    )

    LazyRow(
        modifier = Modifier
    ){
        val filterSimilarList = similarMovies
            .filter { it.backdropPath != null && it.backdropPath!!.isNotEmpty()}

        itemsIndexed(filterSimilarList){index,movie ->
            SimilarMovie(
                movie = movie,
                index = index
            ){ id ->
                   navController.navigate("${Pages.MovieDetail.route}/${id}")
            }

        }
    }
}

@Composable
fun SimilarMovie(movie: Movie,index : Int,onClick : (Int) -> Unit){

    Column(
        modifier = Modifier
            .padding(
                start = if(index == 0) 20.dp else 0.dp,
                end = 20.dp,
                bottom = 10.dp
            )
    ){
        Poster(
            posterPath = movie.backdropPath!!,
            id = movie.id,
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
                    append(movie.title)
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFBCBCBC)
                    )
                ){
                    val date = LocalDate.parse(movie.releaseDate)
                    val year = date.year.toString()
                    append(" ($year)")
                }
            }
        )


    }

}



