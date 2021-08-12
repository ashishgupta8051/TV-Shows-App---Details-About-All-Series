package com.tv.series.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tv.series.repository.TVShowSearchRepository
import com.tv.series.response.TVShowResponse

class TVShowSearchViewModel: ViewModel() {

    fun getSearchResult(searchQuery:String,pageNumber:Int): LiveData<TVShowResponse>{
        return TVShowSearchRepository().getSearchResult(searchQuery,pageNumber)
    }
}