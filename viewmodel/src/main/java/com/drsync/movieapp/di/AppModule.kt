package com.drsync.movieapp.di

import com.drsync.movieapp.viewmodel.DetailsViewModel
import com.drsync.movieapp.viewmodel.MovieViewModel
import com.drsync.core.domain.usecase.MovieInteractor
import com.drsync.core.domain.usecase.MovieUseCase
import com.drsync.movieapp.viewmodel.FavoriteViewModel
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