package com.okankkl.themovieapp.data.mapper

import com.okankkl.themovieapp.utils.ContentType
import com.okankkl.themovieapp.data.model.entity.TvSeriesEntity
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.data.model.dto.TvSeries
import com.okankkl.themovieapp.domain.model.ContentDetail

fun TvSeries.toTvSeriesEntity() : TvSeriesEntity {
    return TvSeriesEntity(
        id = id,
        backdropPath = backdropPath,
        posterPath = posterPath,
        title = title,
        category = category
    )
}

fun TvSeriesEntity.toContent() : Content {
    return Content(
        id = id,
        backdropPath = backdropPath,
        posterPath = posterPath,
        title = title,
        contentType = ContentType.TvSeries.path,
        category = category
    )
}

fun TvSeries.toContent() : Content {
    return Content(
        id = id,
        backdropPath = backdropPath,
        posterPath = posterPath,
        title = title,
        contentType = ContentType.TvSeries.path,
        releaseDate = releaseDate,
        category = category
    )
}

fun TvSeries.toContentDetail() : ContentDetail {
    return ContentDetail(
        id = id,
        backdropPath = backdropPath,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        contentType = ContentType.TvSeries.path,
        category = category,
        overview = overview,
        voteAverage = voteAverage,
        genres = genres,
        videos = videos,
        numberOfEpisodes = numberOfEpisodes,
        numberOfSeasons = numberOfSeasons,
        createdBy = createdBy,
        seasons = seasons,
        lastAirDate = lastAirDate,
        credits = credits,
        similarContents = similar?.results?.filter { it.backdropPath != null }
            ?.map { it.toContent() },
        images = images
    )
}