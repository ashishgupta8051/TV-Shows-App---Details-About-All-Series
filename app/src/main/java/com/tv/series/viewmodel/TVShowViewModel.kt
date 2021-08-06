package com.tv.series.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tv.series.repository.TVShowRepository
import com.tv.series.response.TVShowResponse

class TVShowViewModel(private val tvShowRepository: TVShowRepository) : ViewModel() {

    fun getTvShow(page:Int,context: Context) : LiveData<List<TVShowResponse>>{
        return tvShowRepository.getPopularTvShows(page,context)
    }
}