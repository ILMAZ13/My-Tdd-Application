package com.flatstack.mytddapplication.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.flatstack.mytddapplication.api.ApiEmptyResponse
import com.flatstack.mytddapplication.api.ApiErrorResponse
import com.flatstack.mytddapplication.api.ApiSuccessResponse
import com.flatstack.mytddapplication.api.services.MovieService
import com.flatstack.mytddapplication.entities.MovieType
import com.flatstack.mytddapplication.entities.NetworkState
import com.flatstack.mytddapplication.entities.SearchResult

class PageKeyedMovieSearchDataSource(
    private val movieService: MovieService,
    private val searchQuery: String,
    private val type: MovieType?,
    private val year: Int?
) : PageKeyedDataSource<Int, SearchResult.Movie>() {

    private var retry: (() -> Any)? = null
    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            it.invoke()
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, SearchResult.Movie>) {
        initialLoad.postValue(NetworkState.LOADING)
        networkState.postValue(NetworkState.LOADING)
        val page = 1
        movieService.searchByTitle(searchQuery, type, year, page).observeForever {
            when (it) {
                is ApiErrorResponse -> {
                    retry = {
                        loadInitial(params, callback)
                    }
                    initialLoad.postValue(NetworkState.error(it.errorMessage))
                    networkState.postValue(NetworkState.error(it.errorMessage))
                }
                is ApiSuccessResponse -> {
                    retry = null
                    networkState.postValue(NetworkState.LOADED)
                    initialLoad.postValue(NetworkState.LOADED)
                    callback.onResult(it.body.search, 0, it.body.totalResults, null, page + 1)
                }
                is ApiEmptyResponse -> {
                    retry = null
                    networkState.postValue(NetworkState.LOADED)
                    initialLoad.postValue(NetworkState.LOADED)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SearchResult.Movie>) {
        networkState.postValue(NetworkState.LOADING)
        movieService.searchByTitle(searchQuery, type, year, params.key).observeForever {
            when (it) {
                is ApiErrorResponse -> {
                    retry = {
                        loadAfter(params, callback)
                    }
                    networkState.postValue(NetworkState.error(it.errorMessage))
                }
                is ApiSuccessResponse -> {
                    retry = null
                    networkState.postValue(NetworkState.LOADED)
                    val nextPage = if (params.requestedLoadSize > it.body.search.size) {
                        null
                    } else {
                        params.key + 1
                    }
                    callback.onResult(it.body.search, nextPage)
                }
                is ApiEmptyResponse -> {
                    retry = null
                    networkState.postValue(NetworkState.LOADED)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SearchResult.Movie>) {}
}
