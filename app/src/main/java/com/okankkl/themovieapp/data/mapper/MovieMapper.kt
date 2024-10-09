package com.okankkl.themovieapp.data.mapper

import com.okankkl.themovieapp.utils.ContentType
import com.okankkl.themovieapp.data.model.entity.MovieEntity
import com.okankkl.themovieapp.data.model.dto.Movie
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.model.ContentDetail

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        backdropPath = backdropPath,
        posterPath = posterPath,
        title = title,
        category = category
    )
}

fun Movie.toContent(): Content {
    return Content(
        id = id,
        backdropPath = backdropPath,
        posterPath = posterPath,
        title = title,
        contentType = ContentType.Movie.path,
        releaseDate = releaseDate,
        category = category
    )
}

fun MovieEntity.toContent(): Content {
    return Content(
        id = id,
        backdropPath = backdropPath,
        posterPath = posterPath,
        title = title,
        contentType = ContentType.Movie.path,
        category = category
    )
}

fun Movie.toContentDetail(): ContentDetail {
    return ContentDetail(
        id = id,
        backdropPath = backdropPath,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        contentType = ContentType.Movie.path,
        category = category,
        overview = overview,
        voteAverage = voteAverage,
        runtime = runtime,
        genres = genres,
        videos = videos,
        credits = credits,
        images = images,
        similarContents = similar?.results?.filter { it.backdropPath != null }
            ?.map { it.toContent() }
    )
}