package com.drsync.core.data.remote

import android.util.Log
import com.drsync.core.data.remote.network.ApiResponse
import com.drsync.core.data.remote.network.MovieApi
import com.drsync.core.data.remote.response.MovieResponse
import com.drsync.core.utils.Constant.TAG
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

}