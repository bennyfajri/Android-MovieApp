package com.drsync.movieapp.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.drsync.core.ui.MovieAdapter
import com.drsync.movieapp.R
import com.drsync.movieapp.databinding.FragmentMovieBinding
import com.drsync.movieapp.details.DetailActivity
import com.drsync.movieapp.details.DetailActivity.Companion.EXTRA_DATA
import com.drsync.movieapp.viewmodel.MovieViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModel()

    private var _binding : FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val movieAdapter = MovieAdapter()
            movieAdapter.onItemClick = { selectedData ->
                Intent(activity, DetailActivity::class.java).also {
                    it.putExtra(EXTRA_DATA, selectedData)
                    startActivity(it)
                }
            }

            movieViewModel.movies.observe(viewLifecycleOwner) { movie ->
                if (movie  != null) {
                    when (movie) {
                        is com.drsync.core.data.Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is com.drsync.core.data.Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            movieAdapter.setData(movie.data)
                        }
                        is com.drsync.core.data.Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text =
                                movie.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }

            with(binding.rvMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}