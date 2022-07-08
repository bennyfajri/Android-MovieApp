package com.drsync.core.data.remote.network

import com.drsync.core.BuildConfig
import com.drsync.core.data.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = BuildConfig.MOVIEDB_API_KEY
    ): ListMovieResponse

//    @GET("search/movie")
//    suspend fun searchMovie(
//        @Query("query") query: String,
//        @Query("api_key") apiKey: String = BuildConfig.MOVIEDB_API_KEY
//    ): ListMovieResponse
}