package com.flatstack.mytddapplication.entities

import com.google.gson.annotations.SerializedName

enum class MovieType {
    @SerializedName("movie")
    MOVIE,
    @SerializedName("series")
    SERIES,
    @SerializedName("episode")
    EPISODE
}
