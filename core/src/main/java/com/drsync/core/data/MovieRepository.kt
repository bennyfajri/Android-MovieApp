package com.drsync.core.data

import com.drsync.core.data.local.LocalDataSource
import com.drsync.core.data.remote.RemoteDataSource
import com.drsync.core.data.remote.network.ApiResponse
import com.drsync.core.domain.model.Movie
import com.drsync.core.domain.repository.IMovieRepository
import com.drsync.core.utils.DataMapper
import kotlinx.coroutines.flow.*

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMovieRepository {

    override fun getAllMovie(): Flow<Resource<List<Movie>>> = flow {
        val localData = localDataSource.getAllMovie().map { DataMapper.mapEntitiesToDomain(it) }
        val dbSource = localData.first()
        emit(Resource.Loading())
        if(dbSource.isEmpty()){
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getAllMovie().first()){
                is ApiResponse.Success -> {
                    val movieList = DataMapper.mapResponsesToEntities(apiResponse.data)
                    localDataSource.insertMovie(movieList)
                    emitAll(localData.map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(localData.map { Resource.Success(it) })
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }
        }else{
            emitAll(localData.map { Resource.Success(it) })
        }
    }

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override suspend fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        localDataSource.setFavoriteMovie(movieEntity, state)
    }
}