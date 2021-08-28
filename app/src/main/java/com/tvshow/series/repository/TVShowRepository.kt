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


class TVShowRepository {
    val tvShowList:MutableLiveData<TVShowResponse> = MutableLiveData()

    //get popular tv show
    fun getPopularTvShows(page:Int): MutableLiveData<TVShowResponse>{
        val call: Call<TVShowResponse> = ApiClient.getApiClient().getApi().getTvShows(page)
        call.enqueue(object: Callback<TVShowResponse>{
            override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
                tvShowList.postValue(response.body())
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {
                Log.e(TAG,t.message.toString())
            }
        })
        return tvShowList
    }

}