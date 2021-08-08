package com.tv.series.response

import com.google.gson.annotations.SerializedName
import com.tv.series.model.TVShow

data class TVShowResponse(@SerializedName("page")
                          val page:Int,
                          @SerializedName("pages")
                          val totalPage:Int,
                          @SerializedName("tv_shows")
                          val tvShows:List<TVShow> = emptyList())