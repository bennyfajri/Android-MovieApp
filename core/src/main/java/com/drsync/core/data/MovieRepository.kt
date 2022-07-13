package com.drsync.core.data

import com.drsync.core.data.remote.RemoteDataSource
import com.drsync.core.data.remote.network.ApiResponse
import com.drsync.core.data.remote.response.MovieResponse
import com.drsync.core.domain.model.Movie
import com.drsync.core.domain.repository.IMovieRepository
import com.drsync.core.utils.AppExecutors
import com.drsync.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: com.drsync.core.data.local.LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        object : com.drsync.core.data.NetworkBoundResource<List<Movie>, List<MovieResponse>>(){
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovie()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()

//    override fun getSearchMovie(query: String): Flow<Resource<List<Movie>>> =
//        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>(){
//            override fun loadFromDB(): Flow<List<Movie>> {
//                return localDataSource.getAllMovie().map { DataMapper.mapEntitiesToDomain(it) }
//            }
//
//            override fun shouldFetch(data: List<Movie>?): Boolean =
//                data == null || data.isEmpty()
//
//            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
//                remoteDataSource.searchMovie(query)
//
//            override suspend fun saveCallResult(data: List<MovieResponse>) {
//                val movieList = DataMapper.mapResponsesToEntities(data)
//                localDataSource.insertMovie(movieList)
//            }
//        }.asFlow()

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }
}