package com.okankkl.themovieapp.domain.use_cases.get_movies
import android.util.Log
import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.data.remote.repository.ApiRepositoryImp
import com.okankkl.themovieapp.domain.repository.ApiRepository
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.presentation.home_screen.ContentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException
class GetMoviesFromInternetUseCase @Inject constructor(
    private val api: ApiRepositoryImp
) {
    operator fun invoke() : Flow<Resources<ContentList>> = flow{
        try {
            emit(Resources.Loading())
            val contentList = ContentList()
            contentList.popular = api.getMovies(Categories.Popular,1).results.sortedByDescending { it.popularity }
            contentList.trending = api.getMovies(Categories.Trending,1).results.sortedByDescending { it.popularity }
            contentList.now_playing = api.getMovies(Categories.NowPlaying,1).results.sortedBy { it.popularity }
            contentList.top_rated = api.getMovies(Categories.TopRated,1).results.sortedByDescending { it.voteAverage }

            emit(Resources.Success(data = contentList))
        } catch (e : Exception){
            emit(Resources.Failed(message = e.localizedMessage ?: "Un exptected error occures"))
        }
    }
}