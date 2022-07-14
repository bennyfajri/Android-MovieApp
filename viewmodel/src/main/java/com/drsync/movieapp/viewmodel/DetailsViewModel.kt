package com.drsync.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drsync.core.domain.model.Movie
import com.drsync.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.launch

class DetailsViewModel (
    private val movieUseCase: MovieUseCase
): ViewModel(){
    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) = viewModelScope.launch {
        movieUseCase.setFavoriteMovie(movie, newStatus)
    }
}