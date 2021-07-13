package com.benny.movieapp.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.benny.movieapp.data.remote.MovieRepository

class MovieViewModel @ViewModelInject constructor (private val repository: MovieRepository) : ViewModel() {
}