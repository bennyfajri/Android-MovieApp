package com.drsync.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val overview: String,
    val posterPath: String,
    val originalTitle: String,
    val voteAverage: String,
    val isFavorite: Boolean
): Parcelable
