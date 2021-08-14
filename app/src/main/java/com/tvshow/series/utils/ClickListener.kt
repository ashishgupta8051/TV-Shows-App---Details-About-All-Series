package com.tvshow.series.utils

import com.tvshow.series.model.TVShow

interface ClickListener {
    fun onClickedTvShow(tvShow: TVShow)
}