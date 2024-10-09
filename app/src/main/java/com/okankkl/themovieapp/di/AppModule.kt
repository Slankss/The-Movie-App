package com.okankkl.themovieapp.di

import android.content.Context
import com.okankkl.themovieapp.data.remote.TmdbApi
import com.okankkl.themovieapp.data.local.database.LocalDb
import com.okankkl.themovieapp.data.local.preferences.PrefsImpl
import com.okankkl.themovieapp.data.local.database.AppDatabase
import com.okankkl.themovieapp.utils.Constants.BASE_URL
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
    fun provideStoreData(@ApplicationContext appContext : Context) : PrefsImpl {
        return PrefsImpl(appContext)
    }

    @Singleton
    @Provides
    fun provideDao(@ApplicationContext appContext : Context) : LocalDb {
        return AppDatabase
            .getDatabase(appContext)
            .dao()
    }
}