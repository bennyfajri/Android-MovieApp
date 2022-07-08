package com.drsync.core.data.local

import com.drsync.core.data.local.entity.MovieEntity
import com.drsync.core.data.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {
    fun getAllMovie(): Flow<List<MovieEntity>> = movieDao.getAllMovie()

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovie()

    suspend fun insertMovie(tourismList: List<MovieEntity>) = movieDao.insertMovie(tourismList)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateFavoriteMovie(movie)
    }
}