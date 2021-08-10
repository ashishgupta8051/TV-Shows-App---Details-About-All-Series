package com.tv.series.database

import androidx.room.*
import androidx.room.Dao
import com.tv.series.model.TVShow
import io.reactivex.Flowable

@Dao
interface WatchListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToWatchList(tvShow: TVShow)

    @Delete
    fun removeFromWatchList(tvShow: TVShow)

    @Query("select * from tvShow")
    fun getWatchList() : Flowable<List<TVShow>>

}