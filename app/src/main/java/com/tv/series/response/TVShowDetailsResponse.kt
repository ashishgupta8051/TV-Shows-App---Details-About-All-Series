package com.tv.series.response

import com.google.gson.annotations.SerializedName
import com.tv.series.model.TVShowDetails

data class TVShowDetailsResponse(
    @SerializedName("tvShow")
    val tvShowDetails: TVShowDetails)