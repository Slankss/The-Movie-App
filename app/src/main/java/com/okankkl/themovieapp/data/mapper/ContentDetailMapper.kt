package com.okankkl.themovieapp.data.mapper

import com.okankkl.themovieapp.data.model.entity.Favourite
import com.okankkl.themovieapp.domain.model.ContentDetail
import com.okankkl.themovieapp.utils.ContentType

fun ContentDetail.toFavourite() : Favourite {
    return Favourite(
        contentType =  contentType,
        title = title,
        backdropPath = backdropPath,
        posterPath = posterPath,
        contentId = id,
        date = releaseDate,
        imdb = voteAverage,
        time = if(contentType == ContentType.Movie.path) "$runtime minutes" else "$numberOfSeasons seasons"
    )
}