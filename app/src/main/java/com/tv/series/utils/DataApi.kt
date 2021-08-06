package com.tv.series.utils

import com.tv.series.response.TVShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApi {

    @GET("most-popular")
    fun getTvShows(
        @Query("page") page:Int,
    ): Call<List<TVShowResponse>>


}