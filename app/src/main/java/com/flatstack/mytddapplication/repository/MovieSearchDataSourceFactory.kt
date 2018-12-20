package com.flatstack.mytddapplication.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.flatstack.mytddapplication.api.services.MovieService
import com.flatstack.mytddapplication.entities.MovieType
import com.flatstack.mytddapplication.entities.SearchResult

class MovieSearchDataSourceFactory(
    private val movieService: MovieService,
    private val searchQuery: String,
    private val type: MovieType?,
    private val year: Int?
) : DataSource.Factory<Int, SearchResult.Movie>() {

    val sourceLiveData = MutableLiveData<PageKeyedMovieSearchDataSource>()

    override fun create(): DataSource<Int, SearchResult.Movie> {
        val source = PageKeyedMovieSearchDataSource(movieService, searchQuery, type, year)
        sourceLiveData.postValue(source)
        return source
    }
}
