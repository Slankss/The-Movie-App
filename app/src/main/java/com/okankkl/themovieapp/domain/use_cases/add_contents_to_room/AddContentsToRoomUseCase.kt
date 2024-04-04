package com.okankkl.themovieapp.domain.use_cases.add_contents_to_room

import android.util.Log
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.repository.RoomRepository
import com.okankkl.themovieapp.presentation.Categories
import com.okankkl.themovieapp.presentation.DisplayType
import com.okankkl.themovieapp.presentation.home_screen.ContentList
import javax.inject.Inject

class AddContentsToRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend fun addContents(contentList : ContentList) {
        try {
            val filteredList = getAllMovieList(contentList)
            roomRepository.addContents(filteredList)
        } catch (e : Exception){

        }
    }
    private fun getAllMovieList(contentList: ContentList) : List<Content> {
        val allMovieList = mergeAllList(contentList)
        val filteredList = mutableListOf<Content>()
        allMovieList.forEach { display ->
            filteredList.find { it.id == display.id }?.let {
                it.category += ("," + display.category)
            } ?: filteredList.add(display)
        }
        return filteredList
    }
    private fun mergeAllList(contentList : ContentList) : MutableList<Content>{
        contentList.apply {
            val allMovieList = mutableListOf<Content>()

            allMovieList.addAll(popular.map { content ->
                content.category = Categories.Popular.name
                return@map content
            })
            allMovieList.addAll(trending.map { content ->
                content.category = Categories.Trending.name
                return@map content
            })
            allMovieList.addAll(now_playing.map { content ->
                content.category = Categories.NowPlaying.name
                return@map content
            })
            allMovieList.addAll(top_rated.map { content ->
                content.category = Categories.TopRated.name
                return@map content
            })
            return allMovieList
        }
    }
}