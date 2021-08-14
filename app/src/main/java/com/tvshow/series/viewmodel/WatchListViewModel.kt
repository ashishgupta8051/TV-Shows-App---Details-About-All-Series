package com.tvshow.series.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.tvshow.series.database.WatchListDatabase
import com.tvshow.series.model.TVShow
import com.tvshow.series.repository.WatchListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WatchListViewModel(application: Application) : AndroidViewModel(application) {

    private val watchListRepository : WatchListRepository

    init {
        val dao = WatchListDatabase.getInstance(application).getDao()
        watchListRepository = WatchListRepository(dao)
    }

    //add fav tv series to watch list
    fun addToWatchList(tvShow: TVShow)  = viewModelScope.launch(Dispatchers.IO){
        watchListRepository.addToWatchList(tvShow)
    }

    //delete tv series from watch list
    fun removeFromWatchList(tvShow: TVShow) = viewModelScope.launch(Dispatchers.IO){
        watchListRepository.removeFromWatchList(tvShow)
    }

    //get all watchlist
    fun getWatchList() : LiveData<List<TVShow>>{
        return watchListRepository.getWatchList()
    }

    //getWatchListBy id
    fun getWatchListById(tvShowId:String):LiveData<List<TVShow>>{
        return watchListRepository.getWatchListById(tvShowId)
    }
}