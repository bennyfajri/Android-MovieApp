package com.benny.movieapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.benny.movieapp.core.domain.usecase.MovieUseCase

class FavoriteViewModel (
    movieUseCase: MovieUseCase
): ViewModel() {
    val favoriteMovie = movieUseCase.getFavoriteMovie().asLiveData()
}