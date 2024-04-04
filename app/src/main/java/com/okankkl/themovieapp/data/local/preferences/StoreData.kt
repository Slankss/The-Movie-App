package com.okankkl.themovieapp.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreData(private val context : Context)
{
    companion object {
        private val Context.dataStore : DataStore<Preferences>
                by preferencesDataStore("storeData")
        val MOVIE_UPDATE_TIME = stringPreferencesKey("movie_last_update_time")
        val TV_SERIES_UPDATE_TIME = stringPreferencesKey("tv_series_last_update_time")

    }

    val getMovieUpdateTime : Flow<String?> = context.dataStore.data
        .map{ preferences ->
            preferences[MOVIE_UPDATE_TIME]
        }

    val getTvSeriesUpdateTime : Flow<String?> = context.dataStore.data
        .map{ preferences ->
            preferences[TV_SERIES_UPDATE_TIME]
        }

    suspend fun saveMovieUpdateTime(updateTime : String){
        context.dataStore.edit{ preferences ->
            preferences[MOVIE_UPDATE_TIME] = updateTime
        }
    }
    suspend fun saveTvSeriesUpdateTime(updateTime : String){
        context.dataStore.edit{ preferences ->
            preferences[TV_SERIES_UPDATE_TIME] = updateTime
        }
    }


}