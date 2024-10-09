package com.okankkl.themovieapp.di

import com.okankkl.themovieapp.data.datasource.favourites.FavouritesDataSource
import com.okankkl.themovieapp.data.datasource.movies.local.MoviesLocalDataSource
import com.okankkl.themovieapp.data.datasource.movies.remote.MoviesRemoteDataSource
import com.okankkl.themovieapp.data.datasource.content.ContentDataSource
import com.okankkl.themovieapp.data.datasource.person.PersonDataSource
import com.okankkl.themovieapp.data.datasource.person.PersonDataSourceImpl
import com.okankkl.themovieapp.data.datasource.tvseries.local.TvSeriesLocalDataSource
import com.okankkl.themovieapp.data.datasource.tvseries.remote.TvSeriesRemoteDataSource
import com.okankkl.themovieapp.data.repository.FavouritesRepositoryImpl
import com.okankkl.themovieapp.data.repository.MoviesRepositoryImpl
import com.okankkl.themovieapp.data.repository.MultiContentRepositoryImpl
import com.okankkl.themovieapp.data.repository.PersonRepositoryImpl
import com.okankkl.themovieapp.data.repository.TvSeriesRepositoryImpl
import com.okankkl.themovieapp.domain.repository.FavouritesRepository
import com.okankkl.themovieapp.domain.repository.MoviesRepository
import com.okankkl.themovieapp.domain.repository.MultiContentRepository
import com.okankkl.themovieapp.domain.repository.PersonRepository
import com.okankkl.themovieapp.domain.repository.TvSeriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMoviesRepository(
        localDataSource: MoviesLocalDataSource,
        remoteDataSource: MoviesRemoteDataSource
    ): MoviesRepository = MoviesRepositoryImpl(
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource
    )

    @Singleton
    @Provides
    fun provideTvSeriesRepository(
        localDataSource: TvSeriesLocalDataSource,
        remoteDataSource: TvSeriesRemoteDataSource
    ): TvSeriesRepository = TvSeriesRepositoryImpl(
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource
    )

    @Singleton
    @Provides
    fun provideFavouritesRepository(
        localDataSource: FavouritesDataSource
    ): FavouritesRepository = FavouritesRepositoryImpl(
        dataSource = localDataSource
    )

    @Singleton
    @Provides
    fun provideMultiContentRepository(
        dataSource: ContentDataSource
    ): MultiContentRepository = MultiContentRepositoryImpl(
        dataSource = dataSource
    )

    @Singleton
    @Provides
    fun providePersonRepository(
        dataSource: PersonDataSource
    ): PersonRepository = PersonRepositoryImpl(
        dataSource = dataSource
    )
}