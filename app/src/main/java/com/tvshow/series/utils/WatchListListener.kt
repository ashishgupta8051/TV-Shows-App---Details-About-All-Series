package com.tvshow.series.utils

import com.tvshow.series.model.TVShow

interface WatchListListener {
    fun onTvShowClicked(tvShow: TVShow)

    fun removeTvShow(tvShow:TVShow)
}