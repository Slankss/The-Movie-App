package com.okankkl.themovieapp.data.remote.dto

import com.okankkl.themovieapp.domain.model.TvSeries

data class TvSeriesDetailDto(
    val adult: Boolean,
    val backdrop_path: String?,
    val created_by: List<CreatedBy>,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val last_air_date: String,
    val last_episode_to_air: LastEpisodeToAir,
    val name: String,
    val networks: List<Network>,
    //val next_episode_to_air: Any,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val seasons: List<Season>,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val type: String,
    val videos: Videos?,
    val vote_average: Double,
    val vote_count: Int
)

fun TvSeriesDetailDto.toTvSeries() : TvSeries {
    return TvSeries(
        id = id,
        backdropPath = backdrop_path,
        posterPath = poster_path,
        popularity = popularity,
        voteAverage = vote_average,
        title = name,
        releaseDate = first_air_date,
        category = "",
        mediaType = "tv",
        overview = overview,
        videos = videos,
        createdBy = created_by,
        lastAirDate = last_air_date,
        numberOfEpisodes = number_of_episodes,
        numberOfSeasons = number_of_seasons,
        genres = genres
    )
}