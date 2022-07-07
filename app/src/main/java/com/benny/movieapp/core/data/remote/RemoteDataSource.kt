package com.benny.movieapp.core.data.remote

import android.util.Log
import com.benny.movieapp.core.data.remote.network.ApiResponse
import com.benny.movieapp.core.data.remote.network.MovieApi
import com.benny.movieapp.core.data.remote.response.MovieResponse
import com.benny.movieapp.core.utils.Constant.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(
    private val apiService: MovieApi
) {

    fun getAllMovie(): Flow<ApiResponse<List<MovieResponse>>>{
        return flow {
            try{
                val response = apiService.getNowPlayingMovies()
                val dataArray = response.results
                if(dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                }else{
                    emit(ApiResponse.Empty)
                }
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.d(TAG, "getAllMovie: $e.")
            }
        }.flowOn(Dispatchers.IO)
    }

//    fun searchMovie(query: String): Flow<ApiResponse<List<MovieResponse>>>{
//        return flow {
//            try{
//                val response = apiService.searchMovie(query)
//                val dataArray = response.results
//                if(dataArray.isNotEmpty()){
//                    emit(ApiResponse.Success(response.results))
//                }else{
//                    emit(ApiResponse.Empty)
//                }
//            }catch (e: Exception){
//                emit(ApiResponse.Error(e.toString()))
//                Log.d(TAG, "searchMovie: $e")
//            }
//        }.flowOn(Dispatchers.IO)
//    }

}