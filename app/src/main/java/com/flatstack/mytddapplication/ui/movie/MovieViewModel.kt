package com.flatstack.mytddapplication.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.flatstack.mytddapplication.repository.MovieRepository

class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val movieId = MutableLiveData<String>()

    val movie = Transformations.switchMap(movieId) {
        movieRepository.findMovieById(it)
    }

    fun getMovie(id: String) {
        if (movieId.value != id) {
            movieId.value = id
        }
    }
}
