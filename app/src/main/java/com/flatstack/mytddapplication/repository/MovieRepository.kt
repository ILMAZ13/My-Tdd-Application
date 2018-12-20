package com.flatstack.mytddapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.flatstack.mytddapplication.AppExecutors
import com.flatstack.mytddapplication.api.services.MovieService
import com.flatstack.mytddapplication.api.util.RateLimiter
import com.flatstack.mytddapplication.db.daos.MovieDao
import com.flatstack.mytddapplication.entities.Listing
import com.flatstack.mytddapplication.entities.Movie
import com.flatstack.mytddapplication.entities.MovieType
import com.flatstack.mytddapplication.entities.Resource
import com.flatstack.mytddapplication.entities.SearchResult
import java.util.concurrent.TimeUnit

class MovieRepository(
    private val appExecutors: AppExecutors,
    private val movieDao: MovieDao,
    private val movieService: MovieService
) {
    private val rateLimiter = RateLimiter<String>(1, TimeUnit.HOURS)

    fun findMovieById(id: String): LiveData<Resource<Movie>> =
        object : NetworkBoundResource<Movie, Movie>(appExecutors) {
            override fun loadFromDb() = movieDao.findById(id)

            override fun saveCallResult(item: Movie) = movieDao.insert(item)

            override fun shouldFetch(data: Movie?): Boolean {
                return data == null || rateLimiter.shouldFetch(id)
            }

            override fun createCall() = movieService.findById(id)

            override fun onFetchFailed() = rateLimiter.reset(id)
        }.asLiveData()

    fun searchMovie(searchQuery: String, type: MovieType?, year: Int?): Listing<SearchResult.Movie> {
        val sourceFactory = MovieSearchDataSourceFactory(movieService, searchQuery, type, year)
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(false)
            .build()
        val livePagedList = LivePagedListBuilder<Int, SearchResult.Movie>(sourceFactory, config)
            .setFetchExecutor(appExecutors.mainThread)
            .build()

        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.initialLoad
            }
        )
    }

    companion object {
        const val PAGE_SIZE: Int = 10
    }
}
