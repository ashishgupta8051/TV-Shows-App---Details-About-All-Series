package com.tv.series.utils

import com.tv.series.response.TVShowDetailsResponse
import com.tv.series.response.TVShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApi {

    @GET("most-popular")
    fun getTvShows(
        @Query("page") page:Int,
    ): Call<TVShowResponse>

    @GET("show-details")
    fun getTvShowsDetails(
        @Query("q") showName:String,
    ): Call<TVShowDetailsResponse>

    @GET("search")
    fun getSearchResult(
        @Query("q") searchQuery: String,
        @Query("page")pageNumber:Int
    ):Call<TVShowResponse>


}