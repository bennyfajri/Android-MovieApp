package com.drsync.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.drsync.core.R
import com.drsync.core.databinding.ItemMovieBinding
import com.drsync.core.domain.model.Movie
import com.drsync.core.utils.Constant.IMAGE_URL

class MovieAdapter(
    private val onItemClick: (Movie) -> Unit
) : ListAdapter<Movie, MovieAdapter.MoviewViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviewViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviewViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class MoviewViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {
                Glide.with(itemView)
                    .load("$IMAGE_URL${movie.posterPath}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivMoviePoster)

                tvMovieTitle.text = movie.originalTitle
                tvRating.text = movie.voteAverage
                binding.root.setOnClickListener {
                    onItemClick(movie)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> =
            object : DiffUtil.ItemCallback<Movie>() {
                override fun areItemsTheSame(oldMovie: Movie, newMovie: Movie): Boolean {
                    return oldMovie.id == newMovie.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldMovie: Movie, newMovie: Movie): Boolean {
                    return newMovie == oldMovie
                }
            }
    }

}