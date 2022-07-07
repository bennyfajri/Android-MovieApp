package com.benny.movieapp.di

import com.benny.movieapp.core.domain.usecase.MovieInteractor
import com.benny.movieapp.core.domain.usecase.MovieUseCase
import com.benny.movieapp.details.DetailsViewModel
import com.benny.movieapp.favorite.FavoriteViewModel
import com.benny.movieapp.movie.MovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}