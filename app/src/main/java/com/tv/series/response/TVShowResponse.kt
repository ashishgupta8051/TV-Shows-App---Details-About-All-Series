package com.tv.series.response


import com.tv.series.model.TVShow

data class TVShowResponse(val page:Int,
                          val pages:Int,
                          var  tv_shows:List<TVShow>)