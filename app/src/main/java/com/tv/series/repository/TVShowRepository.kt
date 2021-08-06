package com.tv.series.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tv.series.network.ApiClient
import com.tv.series.response.TVShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TVShowRepository() {
    fun getPopularTvShows(page:Int,context: Context): LiveData<List<TVShowResponse>>{
        val tvShowList:MutableLiveData<List<TVShowResponse>> = MutableLiveData()
        val call: Call<List<TVShowResponse>> = ApiClient.getApi.getTvShows(page)

        call.enqueue(object: Callback<List<TVShowResponse>>{
            override fun onResponse(call: Call<List<TVShowResponse>>, response: Response<List<TVShowResponse>>) {
                if (response.isSuccessful){
                    tvShowList.postValue(response.body())
                }else{
                    Toast.makeText(context,response.errorBody().toString(),Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<TVShowResponse>>, t: Throwable) {
                tvShowList.postValue(null)
            }
        })
        return tvShowList
    }
}