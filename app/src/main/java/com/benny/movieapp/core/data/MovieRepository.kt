package com.benny.movieapp.core.data

import com.benny.movieapp.core.data.local.LocalDataSource
import com.benny.movieapp.core.data.remote.RemoteDataSource
import com.benny.movieapp.core.data.remote.network.ApiResponse
import com.benny.movieapp.core.data.remote.response.MovieResponse
import com.benny.movieapp.core.domain.model.Movie
import com.benny.movieapp.core.domain.repository.IMovieRepository
import com.benny.movieapp.core.utils.AppExecutors
import com.benny.movieapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>(){
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