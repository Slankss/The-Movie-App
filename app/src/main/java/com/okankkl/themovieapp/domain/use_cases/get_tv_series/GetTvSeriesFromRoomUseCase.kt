package com.okankkl.themovieapp.domain.use_cases.get_tv_series
import com.okankkl.themovieapp.common.Resources
import com.okankkl.themovieapp.domain.repository.RoomRepository
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.presentation.DisplayType
import com.okankkl.themovieapp.presentation.home_screen.ContentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
class GetTvSeriesFromRoomUseCase @Inject constructor(
    private val roomApi : RoomRepository
) {
    fun getTvSeries() : Flow<Resources<ContentList>> = flow {
        try {
            emit(Resources.Loading())
            val contentList = ContentList()
            roomApi.getContents(DisplayType.TvSeries.path).let { tvSeries ->
                contentList.popular = tvSeries
                    .filter { it.category.contains(Categories.Popular.name) }
                    .sortedByDescending { it.popularity }

                contentList.trending = tvSeries
                    .filter { it.category.contains(Categories.Trending.name) }
                    .sortedByDescending { it.popularity }

                contentList.now_playing = tvSeries
                    .filter { it.category.contains(Categories.NowPlaying.name) }
                    .sortedBy { it.popularity }

                contentList.top_rated = tvSeries
                    .filter {it.category.contains(Categories.TopRated.name) }
                    .sortedByDescending { it.voteAverage }
            }
            emit(Resources.Success(data = contentList))
        } catch (e : Exception){
            emit(Resources.Failed(message = e.localizedMessage ?: "Unexpected error occured"))
        }
    }

}