package com.tvshow.series.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tvshow.series.repository.TVShowDetailsRepository
import com.tvshow.series.response.TVShowDetailsResponse

class TVShowDetailsViewModel(private val tvShowDetailsRepository: TVShowDetailsRepository = TVShowDetailsRepository()):
    ViewModel() {

    fun getTvShowDetails(showName:String):LiveData<TVShowDetailsResponse>{
        return tvShowDetailsRepository.getTvShowDetails(showName)
    }

}