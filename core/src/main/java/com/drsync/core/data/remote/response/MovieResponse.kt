package com.drsync.core.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class MovieResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("overview")
    val overview: String?,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("original_title")
    val originalTitle: String,

    @field:SerializedName("vote_average")
    val voteAverage: String
) : Parcelable
