package com.okankkl.themovieapp.util

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.okankkl.themovieapp.dao.Dao
import com.okankkl.themovieapp.model.Display
import com.okankkl.themovieapp.model.Favourite
import com.okankkl.themovieapp.model.Movie
import com.okankkl.themovieapp.model.MovieEntity
import com.okankkl.themovieapp.model.TvSeries

@Database(
    entities = [Display::class,Favourite::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun dao() : Dao
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

