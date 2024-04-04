package com.okankkl.themovieapp.domain.use_cases.get_tv_series
import android.util.Log
import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.domain.repository.ApiRepository
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.presentation.home_screen.ContentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import retrofit2.HttpException
import java.io.IOException
class GetTvSeriesFromInternetUseCase @Inject constructor(
    private val api: ApiRepository
) {
    fun getTvSeries() : Flow<Resources<ContentList>> = flow{
        try {
            emit(Resources.Loading())

            val tvSeriesList = ContentList()
            tvSeriesList.popular = api.getTvSeries(Categories.Popular,1).results.sortedByDescending { it.popularity }
            tvSeriesList.trending = api.getTvSeries(Categories.Trending,1).results.sortedByDescending { it.popularity }
            tvSeriesList.now_playing = api.getTvSeries(Categories.OnTheAir,1).results.sortedBy { it.popularity }
            tvSeriesList.top_rated = api.getTvSeries(Categories.TopRated,1).results.sortedByDescending { it.voteAverage }

            Log.w("noluyoyo",tvSeriesList.popular.toString())
            emit(Resources.Success(data = tvSeriesList))

        } catch (e : HttpException){
            emit(Resources.Failed(message = e.localizedMessage ?: "Un exptected error occures"))
        } catch (e : IOException){
            emit(Resources.Failed(message = e.localizedMessage ?: "Please check your internet connection"))
        }
    }
}