package com.benny.movieapp.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.benny.movieapp.core.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 2, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}