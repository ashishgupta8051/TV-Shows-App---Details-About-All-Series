package com.tvshow.series.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tvshow.series.repository.TVShowSearchRepository
import com.tvshow.series.response.TVShowResponse

class TVShowSearchViewModel(private val tvShowSearchRepository: TVShowSearchRepository = TVShowSearchRepository()) : ViewModel() {

    fun getSearchResult(searchQuery:String,pageNumber:Int):LiveData<TVShowResponse>{
        return tvShowSearchRepository.getSearchResult(searchQuery,pageNumber)
    }
}