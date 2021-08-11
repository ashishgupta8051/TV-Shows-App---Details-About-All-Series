package com.tv.series.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tvShow")
data class TVShow(
    @PrimaryKey
    val id: Int,
    val country: String,
    val image_thumbnail_path: String,
    val name: String,
    val network: String,
    val permalink: String,
    val start_date: String,
    val status: String
) : Serializable