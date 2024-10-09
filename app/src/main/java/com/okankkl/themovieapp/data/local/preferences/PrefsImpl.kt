package com.okankkl.themovieapp.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PrefsImpl(private val context : Context) : Prefs
{
    companion object {
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("storeData")
    }

    override suspend fun getStringValue(key: String): Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[stringPreferencesKey(key)]
        }

    override suspend fun setStringValue(key: String, value: String) {
        context.dataStore.edit { preferences ->
                preferences[stringPreferencesKey(key)] = value
            }
    }
}