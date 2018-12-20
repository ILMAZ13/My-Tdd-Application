package com.flatstack.mytddapplication.db

import androidx.room.TypeConverter
import com.flatstack.mytddapplication.entities.Movie
import com.flatstack.mytddapplication.entities.MovieType
import com.google.gson.Gson
import java.util.Date

object DbTypeConverters {
    @TypeConverter
    @JvmStatic
    fun fromMovieTypeToString(movieType: MovieType): String = movieType.name

    @TypeConverter
    @JvmStatic
    fun fromStringToMovieType(string: String): MovieType = MovieType.valueOf(string)

    @TypeConverter
    @JvmStatic
    fun fromDateToLong(date: Date): Long = date.time

    @TypeConverter
    @JvmStatic
    fun fromLongToDate(long: Long): Date = Date(long)

    @TypeConverter
    @JvmStatic
    fun fromRatingListToString(ratingList: List<Movie.Rating>): String =
        Gson().toJson(ratingList, Array<Movie.Rating>::class.java)

    @TypeConverter
    @JvmStatic
    fun fromStringToRatingList(string: String): List<Movie.Rating> =
        Gson().fromJson(string, Array<Movie.Rating>::class.java).toList()
}
