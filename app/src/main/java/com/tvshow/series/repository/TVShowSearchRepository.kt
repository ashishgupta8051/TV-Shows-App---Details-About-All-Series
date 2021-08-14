package com.tvshow.series.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tvshow.series.apiclient.ApiClient
import com.tvshow.series.response.TVShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVShowSearchRepository {

    private val searchList:MutableLiveData<TVShowResponse> = MutableLiveData()

    fun getSearchResult(searchQuery:String,pageNUmber:Int): LiveData<TVShowResponse>{
        val call = ApiClient.getApiClient().getApi().getSearchResult(searchQuery,pageNUmber)
        call.enqueue(object : Callback<TVShowResponse>{
            override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
                searchList.postValue(response.body())
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {
                Log.e(TAG,"some thing is wrong")
            }
        })
        return  searchList
    }
}