package com.flatstack.mytddapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: Int,
    @SerializedName("Rated")
    val rated: String,
    @SerializedName("Released")
    val released: Date,
    @SerializedName("Runtime")
    val runtime: String,
    @SerializedName("Genre")
    val genre: String,
    @SerializedName("Director")
    val director: String,
    @SerializedName("Writer")
    val writer: String,
    @SerializedName("Actors")
    val actors: String,
    @SerializedName("Plot")
    val plot: String,
    @SerializedName("Language")
    val language: String,
    @SerializedName("Country")
    val country: String,
    @SerializedName("Awards")
    val awards: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Ratings")
    val ratings: List<Rating>,
    @SerializedName("Metascore")
    val metascore: String,
    @SerializedName("imdbRating")
    val imdbRating: String,
    @SerializedName("imdbVotes")
    val imdbVotes: String,
    @SerializedName("Type")
    val type: MovieType,
    @SerializedName("DVD")
    val dvd: String,
    @SerializedName("BoxOffice")
    val boxOffice: String,
    @SerializedName("Production")
    val production: String,
    @SerializedName("Website")
    val website: String
) {
    data class Rating(
        @SerializedName("Source")
        val source: String,
        @SerializedName("Value")
        val value: String
    )
}
