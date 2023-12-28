package com.okankkl.themovieapp.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.okankkl.themovieapp.R
import com.okankkl.themovieapp.ui.theme.LightBlue
import androidx.compose.runtime.*
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.okankkl.themovieapp.components.YouTubePlayer
import com.okankkl.themovieapp.enum_sealed.MovieDetailPages
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.enum_sealed.Resources
import com.okankkl.themovieapp.model.Videos
import com.okankkl.themovieapp.util.Util.IMAGE_BASE_URL
import com.okankkl.themovieapp.viewModel.MovieDetailViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@Composable
fun MovieDetail(navController: NavController,movieId : Int?)
{
    var movieViewModel : MovieDetailViewModel = hiltViewModel()
    var movie = movieViewModel.movie.collectAsState()

    SideEffect {
        if(movieId != null){
            movieViewModel.getMovie(movieId)
        }
    }

    when(movie.value){
        is Resources.Loading -> Loading()
        is Resources.Success -> {
            Success(
                movie = (movie.value as Resources.Success).data as Movie
            )
        }
        is Resources.Failed -> {
            Failed(errorMsg = (movie.value as Resources.Failed).errorMsg)
        }
    }
}

@Composable
fun Success(movie : Movie){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        TopHeader(movie = movie)
        Content(movie = movie)
    }

}

@Composable
fun TopHeader(movie: Movie ){

    Box(
        modifier = Modifier
            .fillMaxWidth()
        ,
    ){
        movie.videos?.apply {
            results.firstOrNull { it.type == "Trailer"}?.let { video ->
                YouTubePlayer(videoId = video.key, lifecycleOwner = LocalLifecycleOwner.current)
            }
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_fullscreen),
            contentDescription = "Full screen",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 25.dp, bottom = 65.dp)
        )

    }



}

@Composable
fun Content(movie: Movie){

    Column(
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 25.dp)
    ) {

        Text(
            text = movie.title,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 24.sp,
                lineHeight = 32.sp
            )
        )

        Row(
            modifier = Modifier
                .padding(top = 20.dp),
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
                .padding(vertical = 20.dp)
                .background(color = Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(50.dp)
        ){
            Column(){
                Text(
                    text = "Release date",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .padding(bottom= 10.dp)
                )
                Text(
                    text = convertDate(movie.releaseDate),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 12.sp,
                        color = Color(0xFFBCBCBC)
                    )
                )
            }
            Column(){
                Text(
                    text = "Genre",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .padding(bottom= 10.dp)
                )
            }
        }

        Divider(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .background(color = Color.White)
                .height(1.dp)
                .fillMaxWidth()
        )

        Text(
            text = "Overview",
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 16.sp
            ),
            modifier = Modifier
                .padding(bottom= 10.dp)
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

        Row(
            modifier = Modifier
                .padding(vertical = 20.dp),
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


@SuppressLint("SimpleDateFormat")
fun convertDate(dateString : String) : String{
    val date = LocalDate.parse(dateString)
    val month = date.month.name.lowercase(Locale.ROOT)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    return "$month ${date.dayOfMonth}, ${date.year}"

}