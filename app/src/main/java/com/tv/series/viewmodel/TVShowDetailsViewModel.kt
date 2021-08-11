package com.tv.series.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tv.series.database.WatchListDatabase
import com.tv.series.model.TVShow
import com.tv.series.repository.TVShowDetailsRepository
import com.tv.series.repository.WatchListRepository
import com.tv.series.response.TVShowDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TVShowDetailsViewModel(private val tvShowDetailsRepository: TVShowDetailsRepository = TVShowDetailsRepository()):
    ViewModel() {

    fun getTvShowDetails(showName:String):LiveData<TVShowDetailsResponse>{
        return tvShowDetailsRepository.getTvShowDetails(showName)
    }

}