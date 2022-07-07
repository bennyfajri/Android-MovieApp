package com.benny.movieapp.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: String,
    val overview: String,
    val posterPath: String,
    val originalTitle: String,
    val isFavorite: Boolean
): Parcelable
