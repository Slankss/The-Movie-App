package com.okankkl.themovieapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.okankkl.themovieapp.domain.model.Content
import com.okankkl.themovieapp.domain.model.Favourite

@Database(
    entities = [Content::class, Favourite::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun dao() : ContentDao
    companion object{
        private var INSTANCE : AppDatabase? = null
        fun getDatabase(context : Context) : AppDatabase {
            var tmpInstance = INSTANCE
            if(tmpInstance != null){
                return tmpInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "cache_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

