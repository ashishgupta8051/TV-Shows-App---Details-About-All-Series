package com.tvshow.series.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.tvshow.series.model.TVShow

@Dao
interface WatchListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToWatchList(tvShow: TVShow)

    @Delete
    suspend fun removeFromWatchList(tvShow: TVShow)

    @Query("select * from tvShow")
    fun getWatchList(): LiveData<List<TVShow>>

    @Query("select * from tvShow where id = :tvShowId")
    fun getWatchListById(tvShowId:String) : LiveData<List<TVShow>>


}