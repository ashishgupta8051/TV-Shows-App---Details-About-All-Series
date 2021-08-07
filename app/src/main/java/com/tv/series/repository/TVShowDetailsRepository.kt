package com.tv.series.repository

import androidx.lifecycle.MutableLiveData
import com.tv.series.network.ApiClient
import com.tv.series.response.TVShowDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVShowDetailsRepository {
    val tvShowDetails: MutableLiveData<TVShowDetailsResponse> = MutableLiveData()

    fun getTvShowDetails(showName: String): MutableLiveData<TVShowDetailsResponse> {
        val call: Call<TVShowDetailsResponse> = ApiClient.getApi.getTvShowsDetails(showName)
        call.enqueue(object: Callback<TVShowDetailsResponse> {
            override fun onResponse(call: Call<TVShowDetailsResponse>, response: Response<TVShowDetailsResponse>) {
                tvShowDetails.postValue(response.body())
            }

            override fun onFailure(call: Call<TVShowDetailsResponse>, t: Throwable) {
                tvShowDetails.postValue(null)
            }
        })
        return tvShowDetails
    }
}