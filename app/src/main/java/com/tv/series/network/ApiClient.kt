package com.tv.series.network

import com.tv.series.utils.Credentials
import com.tv.series.utils.DataApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private var apiClient: ApiClient? = null
    private var retrofit: Retrofit? = null

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Credentials().BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Synchronized
    fun getApiClient() : ApiClient {
        if (apiClient == null) {
            apiClient = ApiClient
        }
        return apiClient as ApiClient
    }

    fun getApi(): DataApi {
        return retrofit!!.create(DataApi::class.java)
    }
}