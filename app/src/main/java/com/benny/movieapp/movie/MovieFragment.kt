package com.benny.movieapp.movie

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.benny.movieapp.R
import com.benny.movieapp.databinding.FragmentMovieBinding
import com.benny.movieapp.details.DetailActivity
import com.benny.movieapp.details.DetailActivity.Companion.EXTRA_DATA
import com.drsync.core.ui.MovieAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModel()

    private var _binding : FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object  : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
//                movieViewModel.searchMovies(query.toString())
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}