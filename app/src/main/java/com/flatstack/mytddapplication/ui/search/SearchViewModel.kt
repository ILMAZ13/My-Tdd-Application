package com.flatstack.mytddapplication.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.flatstack.mytddapplication.repository.MovieRepository

class SearchViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val searchQuery = MutableLiveData<String>()
    private val searchResult = Transformations.map(searchQuery) { movieRepository.searchMovie(it, null, null) }

    val movies = Transformations.switchMap(searchResult) { it.pagedList }
    val networkState = Transformations.switchMap(searchResult) { it.networkState }
    val refreshState = Transformations.switchMap(searchResult) { it.refreshState }

    fun searchMovie(query: String) {
        if (searchQuery.value != query) {
            searchQuery.value = query
        }
    }

    fun retry() {
        searchResult?.value?.retry?.invoke()
    }
}
