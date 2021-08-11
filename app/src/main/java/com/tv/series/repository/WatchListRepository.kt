package com.tv.series.repository

import androidx.lifecycle.LiveData
import com.tv.series.database.WatchListDao
import com.tv.series.model.TVShow


class WatchListRepository(private val watchListDao: WatchListDao) {

    //add fav tv series to watch list
    suspend fun addToWatchList(tvShow: TVShow){
        watchListDao.addToWatchList(tvShow)
    }

    //delete tv series from watch list
    suspend fun removeFromWatchList(tvShow: TVShow){
        watchListDao.removeFromWatchList(tvShow)
    }

    //getAll watchlist
    fun getWatchList():LiveData<List<TVShow>>{
        return watchListDao.getWatchList()
    }

    //getWatchlist by id
    fun getWatchListById(tvShowId:String):LiveData<List<TVShow>>{
        return watchListDao.getWatchListById(tvShowId)
    }
}