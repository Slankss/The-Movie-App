package com.okankkl.themovieapp.module

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import com.okankkl.themovieapp.api.api
import com.okankkl.themovieapp.model.StoreData
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
    fun provideRetrofit() : api{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api::class.java)
    }

    @Singleton
    @Provides
    fun provideStoreData(@ApplicationContext appContext : Context) : StoreData {
        return StoreData(appContext)
    }

}