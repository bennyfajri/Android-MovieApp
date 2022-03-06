package com.benny.movieapp.data.local

import javax.inject.Inject

class FavoriteMovieRepository @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao
){
    suspend fun addToFavorite(favoriteMovie: FavoriteMovie) =
        favoriteMovieDao.addToFavorite(favoriteMovie)
    suspend fun checkMovie(id: String) = favoriteMovieDao.checkMovie(id)
    suspend fun removeFromFavorite(id: String){
        favoriteMovieDao.removeFromFavorite(id)
    }
}