package com.benny.movieapp.ui.favorite

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.benny.movieapp.data.local.FavoriteMovieRepository

class FavoriteViewModel @ViewModelInject constructor(
    private val repository: FavoriteMovieRepository
): ViewModel() {
    val movies = repository.getFavoriteMovies()
}