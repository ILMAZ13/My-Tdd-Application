package com.flatstack.mytddapplication.api.services

import androidx.lifecycle.LiveData
import com.flatstack.mytddapplication.api.ApiResponse
import com.flatstack.mytddapplication.entities.Movie
import com.flatstack.mytddapplication.entities.MovieType
import com.flatstack.mytddapplication.entities.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/")
    fun searchByTitle(
        @Query("s") title: String,
        @Query("type") type: MovieType?,
        @Query("y") year: Int?,
        @Query("page") page: Int = 1
    ): Call<SearchResult>

    fun findById(@Query("i") id: String): LiveData<ApiResponse<Movie>>
}
