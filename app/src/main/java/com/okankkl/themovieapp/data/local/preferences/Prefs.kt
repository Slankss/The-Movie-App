package com.okankkl.themovieapp.data.local.preferences

import kotlinx.coroutines.flow.Flow

interface Prefs {

    suspend fun getStringValue(key: String): Flow<String?>

    suspend fun setStringValue(key: String, value: String)
}