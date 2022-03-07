package com.benny.movieapp.ui.movie

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.benny.movieapp.R
import com.benny.movieapp.data.remote.Movie
import com.benny.movieapp.databinding.ItemMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class MovieAdapter(
    private val listener: OnItemClickListener
) : PagingDataAdapter<Movie, MovieAdapter.MoviewViewHolder>(COMPARATOR) {

    inner class MoviewViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onClick(item)
                    }
                }
            }
        }

        fun bind(movie: Movie) {
            with(binding) {
                Glide.with(itemView)
                    .load("${movie.baseUrl}${movie.poster_path}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivMoviePoster)

                tvMovieTitle.text = movie.original_title
            }
        }
    }

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

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

        }
    }

    interface OnItemClickListener {
        fun onClick(movie: Movie)
    }


}