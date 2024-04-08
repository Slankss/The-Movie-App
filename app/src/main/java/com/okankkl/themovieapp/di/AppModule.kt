package com.okankkl.themovieapp.di

import android.content.Context
import com.okankkl.themovieapp.data.remote.TmdbApi
import com.okankkl.themovieapp.data.local.database.ContentDao
import com.okankkl.themovieapp.data.local.preferences.StoreData
import com.okankkl.themovieapp.data.paging.data_source.ContentDataSource
import com.okankkl.themovieapp.data.paging.data_source.ContentDataSourceImp
import com.okankkl.themovieapp.data.paging.use_case.GetMoviesUseCase
import com.okankkl.themovieapp.data.paging.use_case.GetTvSeriesUseCase
import com.okankkl.themovieapp.domain.repository.ApiRepository
import com.okankkl.themovieapp.data.remote.repository.ApiRepositoryImp
import com.okankkl.themovieapp.data.local.database.AppDatabase
import com.okankkl.themovieapp.common.Constants.BASE_URL
import com.okankkl.themovieapp.data.local.database.repository.RoomRepositoryImp
import com.okankkl.themovieapp.domain.repository.RoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule
{
    @Singleton
    @Provides
    fun provideRetrofit() : TmdbApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApi::class.java)
    }
    @Singleton
    @Provides
    fun provideStoreData(@ApplicationContext appContext : Context) : StoreData {
        return StoreData(appContext)
    }

    @Singleton
    @Provides
    fun provideDao(@ApplicationContext appContext : Context) : ContentDao {
        return AppDatabase
            .getDatabase(appContext)
            .dao()
    }
    
    @Singleton
    @Provides
    fun provideMovieDataSource(
            api: TmdbApi
    ) : ContentDataSource
    {
        return ContentDataSourceImp(api)
    }
    
    @Singleton
    @Provides
    fun provideRepository(
        api: TmdbApi
    ) : ApiRepository
    {
        return ApiRepositoryImp(api)
    }
    @Singleton
    @Provides
    fun provideRoomRepository(
        contentDao: ContentDao
    ) : RoomRepository {
        return RoomRepositoryImp(contentDao)
    }

    @Singleton
    @Provides
    fun provideTvSeriesUseCase(
        apiRepository: ApiRepository
    ): GetTvSeriesUseCase
    {
        return GetTvSeriesUseCase(apiRepository)
    }

    @Singleton
    @Provides
    fun provideMovieUseCase(
        apiRepository: ApiRepository
    ): GetMoviesUseCase
    {
        return GetMoviesUseCase(apiRepository)
    }

}