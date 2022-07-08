package com.benny.movieapp

import android.app.Application
import com.drsync.movieapp.di.useCaseModule
import com.drsync.movieapp.di.viewModelModule
import com.drsync.core.di.databaseModule
import com.drsync.core.di.networkModule
import com.drsync.core.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MovieApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    com.drsync.movieapp.di.useCaseModule,
                    com.drsync.movieapp.di.viewModelModule
                )
            )
        }
    }
}