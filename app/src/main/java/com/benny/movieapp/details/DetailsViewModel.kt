package com.benny.movieapp.details

import androidx.lifecycle.ViewModel
import com.drsync.core.domain.model.Movie
import com.drsync.core.domain.usecase.MovieUseCase

class DetailsViewModel (
    private val movieUseCase: MovieUseCase
): ViewModel(){
    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) = movieUseCase.setFavoriteMovie(movie, newStatus)
}