package com.okankkl.themovieapp.di

import com.okankkl.themovieapp.data.datasource.content.ContentDataSource
import com.okankkl.themovieapp.data.datasource.content.ContentDataSourceImpl
import com.okankkl.themovieapp.data.datasource.favourites.FavouritesDataSource
import com.okankkl.themovieapp.data.datasource.favourites.FavouritesDataSourceImpl
import com.okankkl.themovieapp.data.datasource.movies.local.MoviesLocalDataSource
import com.okankkl.themovieapp.data.datasource.movies.local.MoviesLocalDataSourceImpl
import com.okankkl.themovieapp.data.datasource.tvseries.local.TvSeriesLocalDataSource
import com.okankkl.themovieapp.data.datasource.tvseries.local.TvSeriesLocalDataSourceImpl
import com.okankkl.themovieapp.data.datasource.movies.remote.MoviesRemoteDataSource
import com.okankkl.themovieapp.data.datasource.movies.remote.MoviesRemoteDataSourceImpl
import com.okankkl.themovieapp.data.datasource.person.PersonDataSource
import com.okankkl.themovieapp.data.datasource.person.PersonDataSourceImpl
import com.okankkl.themovieapp.data.datasource.tvseries.remote.TvSeriesRemoteDataSource
import com.okankkl.themovieapp.data.datasource.tvseries.remote.TvSeriesRemoteDataSourceImpl
import com.okankkl.themovieapp.data.local.database.LocalDb
import com.okankkl.themovieapp.data.remote.TmdbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataSourceModule {

    @Singleton
    @Provides
    fun provideMoviesRemoteDataSource(
        api: TmdbApi
    ): MoviesRemoteDataSource = MoviesRemoteDataSourceImpl(api)

    @Singleton
    @Provides
    fun provideMoviesLocalDataSource(
        localDb: LocalDb
    ): MoviesLocalDataSource = MoviesLocalDataSourceImpl(localDb)

    @Singleton
    @Provides
    fun provideTvSeriesLocalDataSource(
        localDb: LocalDb
    ): TvSeriesLocalDataSource = TvSeriesLocalDataSourceImpl(localDb)

    @Singleton
    @Provides
    fun provideTvSeriesRemoteDataSource(
        api: TmdbApi
    ): TvSeriesRemoteDataSource = TvSeriesRemoteDataSourceImpl(api)

    @Singleton
    @Provides
    fun provideFavouritesDataSource(
        localDb: LocalDb
    ): FavouritesDataSource = FavouritesDataSourceImpl(localDb)

    @Singleton
    @Provides
    fun provideContentDataSource(
        api: TmdbApi
    ) : ContentDataSource = ContentDataSourceImpl(api)

    @Singleton
    @Provides
    fun providePersonDataSource(
        api: TmdbApi
    ) : PersonDataSource = PersonDataSourceImpl(api)
}