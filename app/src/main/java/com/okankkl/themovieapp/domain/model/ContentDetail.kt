package com.okankkl.themovieapp.domain.model

import com.okankkl.themovieapp.data.model.dto.CreatedBy
import com.okankkl.themovieapp.data.model.dto.Credits
import com.okankkl.themovieapp.data.model.dto.Genre
import com.okankkl.themovieapp.data.model.dto.Images
import com.okankkl.themovieapp.data.model.dto.Season
import com.okankkl.themovieapp.data.model.dto.Videos
import com.okankkl.themovieapp.utils.Constants.YOUTUBE_VIDEO_URL

class ContentDetail(
    id: Int? = null,
    backdropPath: String? = null,
    posterPath: String? = null,
    title: String? = null,
    contentType: String? = null,
    category: String? = null,
    releaseDate: String? = null,
    val genres: List<Genre>? =  null,
    val lastAirDate: String? = null,
    val videos: Videos? = null,
    val overview: String? = null,
    val voteAverage: Double? = null,
    val runtime: Int? = null,
    val createdBy: List<CreatedBy>? = null,
    val numberOfEpisodes: Int? = null,
    val numberOfSeasons: Int? = null,
    val seasons: List<Season>? = null,
    val credits: Credits? = null,
    val images: Images? = null,
    val similarContents: List<Content>? = null
) : Content(id,backdropPath,posterPath,title,category,contentType,releaseDate) {

    fun getTrailerUrl() : String? {
        videos?.results?.first()?.key?.let { trailerKey ->
            return "$YOUTUBE_VIDEO_URL$trailerKey"
        } ?: return null
    }
}