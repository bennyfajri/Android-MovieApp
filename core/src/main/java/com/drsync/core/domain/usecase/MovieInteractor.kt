package com.drsync.core.domain.usecase

import com.drsync.core.domain.model.Movie
import com.drsync.core.domain.repository.IMovieRepository

class MovieInteractor(
    private val movieRepository: IMovieRepository
) : MovieUseCase {
    override fun getAllMovie() = movieRepository.getAllMovie()

//    override fun getSearchMovie(query: String) = movieRepository.getSearchMovie(query)

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) = movieRepository.setFavoriteMovie(movie, state)
}