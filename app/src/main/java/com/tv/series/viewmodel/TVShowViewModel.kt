package com.tv.series.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tv.series.repository.TVShowRepository
import com.tv.series.response.TVShowResponse

class TVShowViewModel : ViewModel(){
    //get tv Show
    fun getTvShow(page:Int) : LiveData<TVShowResponse>{
        return TVShowRepository().getPopularTvShows(page)
    }
}