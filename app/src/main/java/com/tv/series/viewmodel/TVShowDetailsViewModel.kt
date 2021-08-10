package com.tv.series.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tv.series.repository.TVShowDetailsRepository
import com.tv.series.response.TVShowDetailsResponse

class TVShowDetailsViewModel(private val tvShowDetailsRepository: TVShowDetailsRepository =
    TVShowDetailsRepository()): ViewModel() {

        fun getTvShowDetails(showName:String):LiveData<TVShowDetailsResponse>{
            return tvShowDetailsRepository.getTvShowDetails(showName)
        }
}