package com.drsync.core.utils

import com.drsync.core.data.local.entity.MovieEntity
import com.drsync.core.data.remote.response.MovieResponse
import com.drsync.core.domain.model.Movie

object DataMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity>{
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                overview = it.overview ?: "",
                posterPath = it.posterPath,
                originalTitle = it.originalTitle,
                voteAverage = it.voteAverage,
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                overview = it.overview,
                posterPath = it.posterPath,
                originalTitle = it.originalTitle,
                voteAverage = it.voteAverage,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        id = input.id,
        overview = input.overview,
        posterPath = input.posterPath,
        originalTitle = input.originalTitle,
        voteAverage = input.voteAverage,
        isFavorite = input.isFavorite
    )
}