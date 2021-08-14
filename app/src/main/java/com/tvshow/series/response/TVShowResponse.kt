package com.tvshow.series.response


import com.tvshow.series.model.TVShow

data class TVShowResponse(val page:Int,
                          val pages:Int,
                          var  tv_shows:List<TVShow>)