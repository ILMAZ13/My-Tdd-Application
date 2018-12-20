package com.flatstack.mytddapplication.entities

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("Search")
    val search: List<Movie>,
    @SerializedName("totalResults")
    val totalResults: Int
) : RespBase() {
    data class Movie(
        @SerializedName("imdbID")
        val imdbID: String,
        @SerializedName("Title")
        val title: String,
        @SerializedName("Year")
        val year: String,
        @SerializedName("Type")
        val type: MovieType?,
        @SerializedName("Poster")
        val poster: String,
        val page: Int = 1
    )
}
