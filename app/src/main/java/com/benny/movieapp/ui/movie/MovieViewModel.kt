package com.benny.movieapp.ui.movie


import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.benny.movieapp.data.remote.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor (
    private val repository: MovieRepository,
    private val state: SavedStateHandle
) : ViewModel()  {

    companion object{
        private const val CURRENT_QUERY = "current_query"
        private const val EMPTY_QUERY = ""
    }
    private val currentQuery = state.getLiveData(CURRENT_QUERY, EMPTY_QUERY)
    val movies = currentQuery.switchMap { query ->
        if(query.isNotEmpty()){
            repository.getSearchMovies(query)
        }else{
            repository.getNowPlayingMovies().cachedIn(viewModelScope)
        }
    }

    fun searchMovies(query: String){
        currentQuery.value = query
    }
}