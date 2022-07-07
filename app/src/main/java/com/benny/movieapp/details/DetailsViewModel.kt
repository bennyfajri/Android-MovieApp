package com.benny.movieapp.details

import androidx.lifecycle.ViewModel
import com.benny.movieapp.core.domain.model.Movie
import com.benny.movieapp.core.domain.usecase.MovieUseCase

class DetailsViewModel (
    private val movieUseCase: MovieUseCase
): ViewModel(){
    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) = movieUseCase.setFavoriteMovie(movie, newStatus)
}