package com.tvshow.series.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tvshow.series.repository.TVShowRepository
import com.tvshow.series.response.TVShowResponse

class TVShowViewModel : ViewModel(){
    //get tv Show
    fun getTvShow(page:Int) : LiveData<TVShowResponse>{
        return TVShowRepository().getPopularTvShows(page)
    }
}