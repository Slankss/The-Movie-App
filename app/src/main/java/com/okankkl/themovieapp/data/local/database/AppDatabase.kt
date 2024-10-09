package com.okankkl.themovieapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.okankkl.themovieapp.data.model.entity.MovieEntity
import com.okankkl.themovieapp.data.model.entity.TvSeriesEntity
import com.okankkl.themovieapp.data.model.entity.Favourite

@Database(
    entities = [MovieEntity::class, TvSeriesEntity::class, Favourite::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun dao() : LocalDb
    companion object{
        private var INSTANCE : AppDatabase? = null
        fun getDatabase(context : Context) : AppDatabase {
            val tmpInstance = INSTANCE
            if(tmpInstance != null){
                return tmpInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "local_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

