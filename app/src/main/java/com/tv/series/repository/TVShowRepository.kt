package com.tv.series.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tv.series.network.ApiClient
import com.tv.series.response.TVShowResponse
import com.tv.series.utils.DataApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TVShowRepository {
    val tvShowList:MutableLiveData<TVShowResponse> = MutableLiveData()

    fun getPopularTvShows(page:Int): MutableLiveData<TVShowResponse>{
        val call: Call<TVShowResponse> = ApiClient.getApi.getTvShows(page)
        call.enqueue(object: Callback<TVShowResponse>{
            override fun onResponse(call: Call<TVShowResponse>, response: Response<TVShowResponse>) {
                tvShowList.postValue(response.body())
            }

            override fun onFailure(call: Call<TVShowResponse>, t: Throwable) {
                tvShowList.postValue(null)
            }
        })
        return tvShowList
    }
}