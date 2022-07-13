package com.drsync.core.utils

import com.drsync.core.data.local.entity.MovieEntity

object DataDummy {
    fun generateDummyMovie(): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        for (i in 0..10){
            val movie = MovieEntity(
                "$i",
                "Oveview $i",
                "https://image.tmdb.org/t/p/w500/wKiOkZTN9lUUUNZLmtnwubZYONg.jpg",
                "Title $i",
                "$i",
                false
            )
            movieList.add(movie)
        }
        return movieList
    }
}