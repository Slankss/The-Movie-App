package com.okankkl.themovieapp.module

import android.content.Context
import com.okankkl.themovieapp.api.TmdbApi
import com.okankkl.themovieapp.dao.Dao
import com.okankkl.themovieapp.model.StoreData
import com.okankkl.themovieapp.paging.data_source.DataSources
import com.okankkl.themovieapp.paging.data_source.DataSourcesImp
import com.okankkl.themovieapp.paging.use_case.GetMoviesUseCase
import com.okankkl.themovieapp.paging.use_case.GetTvSeriesUseCase
import com.okankkl.themovieapp.repository.Repository
import com.okankkl.themovieapp.repository.RepositoryImp
import com.okankkl.themovieapp.util.AppDatabase
import com.okankkl.themovieapp.util.Util.BASE_URL
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
    fun provideRetrofit() : TmdbApi
    {
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
    fun provideDao(@ApplicationContext appContext : Context) : Dao{
        return AppDatabase
            .getDatabase(appContext)
            .dao()
    }
    
    @Singleton
    @Provides
    fun provideMovieDataSource(
            api: TmdbApi
    ) : DataSources
    {
        return DataSourcesImp(api)
    }
    
    @Singleton
    @Provides
    fun provideRepository(
            api: TmdbApi,
            dao: Dao
    ) : Repository
    {
        return RepositoryImp(api,dao)
    }
    
    @Singleton
    @Provides
    fun provideMovieUseCase(
            repository: Repository
    ): GetMoviesUseCase
    {
        return GetMoviesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideTvSeriesUseCase(
        repository: Repository
    ): GetTvSeriesUseCase
    {
        return GetTvSeriesUseCase(repository)
    }

}