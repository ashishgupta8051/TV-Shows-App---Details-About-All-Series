package com.tv.series.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tv.series.network.ApiClient
import com.tv.series.response.TVShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVShowSearchRepository {
    val searchList:MutableLiveData<TVShowResponse> = MutableLiveData()

    fun getSearchResult(searchQuery:String,pageNumber: Int):LiveData<TVShowResponse>{
        val call = ApiClient.getApiClient().getApi().getSearchResult(searchQuery, pageNumber)
        call.enqueue(object : Callback<TVShowResponse>{
            override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
                searchList.postValue(response.body())
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {
                Log.e("TAG","some thing is wrong")
            }
        })
        return searchList
    }
}