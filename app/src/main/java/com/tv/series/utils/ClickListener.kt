package com.tv.series.utils

import com.tv.series.model.TVShow

interface ClickListener {
    fun onClickedTvShow(tvShow: TVShow)
}