package com.drsync.movieapp.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.benny.movieapp.details.DetailActivity
import com.benny.movieapp.details.DetailActivity.Companion.EXTRA_DATA
import com.drsync.core.ui.MovieAdapter
import com.drsync.movieapp.favorite.databinding.FragmentFavoriteBinding
import com.drsync.movieapp.viewmodel.FavoriteViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null){
            val movieAdapter = MovieAdapter()
            movieAdapter.onItemClick = { selectedData ->
                Intent(activity, DetailActivity::class.java).also {
                    it.putExtra(EXTRA_DATA, selectedData)
                    startActivity(it)
                }
            }

            favoriteViewModel.favoriteMovie.observe(viewLifecycleOwner){ movieData ->
                movieAdapter.setData(movieData)
                binding.viewEmpty.root.visibility =
                    if(movieData.isNotEmpty()) View.GONE else View.VISIBLE
            }

            binding.rvMovie.apply {
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