package com.tv.series.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tv.series.repository.TVShowRepository
import com.tv.series.response.TVShowResponse

class TVShowViewModel(private var tvShowRepository: TVShowRepository = TVShowRepository()) : ViewModel() {

    fun getTvShow(page:Int) : LiveData<TVShowResponse>{
        return tvShowRepository.getPopularTvShows(page)
    }
}