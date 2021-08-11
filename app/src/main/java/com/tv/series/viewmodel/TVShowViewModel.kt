package com.tv.series.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tv.series.repository.TVShowRepository
import com.tv.series.response.TVShowResponse

class TVShowViewModel(private val tvShowRepository: TVShowRepository = TVShowRepository()) : ViewModel(){

    //get tv Show
    fun getTvShow(page:Int) : LiveData<TVShowResponse>{
        return tvShowRepository.getPopularTvShows(page)
    }

}