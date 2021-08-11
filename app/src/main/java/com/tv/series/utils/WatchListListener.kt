package com.tv.series.utils

import com.tv.series.model.TVShow

interface WatchListListener {
    fun onTvShowClicked(tvShow: TVShow)

    fun removeTvShow(tvShow:TVShow)
}