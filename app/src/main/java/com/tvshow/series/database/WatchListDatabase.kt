package com.tvshow.series.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tvshow.series.model.TVShow

@Database(entities = [TVShow::class], version = 1, exportSchema = false)
abstract class WatchListDatabase : RoomDatabase(){

    abstract fun getDao(): WatchListDao

    companion object{
        private var INSTANCE : WatchListDatabase? = null

        fun getInstance(context: Context) : WatchListDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WatchListDatabase::class.java,
                    "watchlistDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}