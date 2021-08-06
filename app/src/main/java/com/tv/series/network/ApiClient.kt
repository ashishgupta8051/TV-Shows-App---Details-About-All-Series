package com.tv.series.network

import com.tv.series.utils.Credentials
import com.tv.series.utils.DataApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Credentials().BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getApi:DataApi by lazy {
        retrofit!!.create(DataApi::class.java)
    }
}