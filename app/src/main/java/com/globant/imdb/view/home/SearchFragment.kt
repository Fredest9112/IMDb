package com.globant.imdb.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.globant.imdb.databinding.FragmentSearchBinding
import com.globant.imdb.model.SearchMovieViewModel
import com.globant.imdb.model.SearchMovieViewModelFactory
import com.globant.imdb.repo.MoviesRepo
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import com.globant.imdb.view.adapter.MovieAdapter
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    private lateinit var movieAdapter: MovieAdapter
    private val searchMovieViewModelFactory = SearchMovieViewModelFactory(MoviesRepo())
    private val searchMovieViewModel by lazy {
        ViewModelProvider(this, searchMovieViewModelFactory)[SearchMovieViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentSearchBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter()

        binding?.apply {
            searchResultRecycler.adapter = movieAdapter
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if(!query.isNullOrEmpty()){
                        lifecycleScope.launch {
                            Log.i("query","$query")
                            Log.i("Response","${MoviesRepo().getMoviesByQuery(query)}")
                        }
                        return true
                    }
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    return false
                }
            })
        }

        searchMovieViewModel.topRatedMovies.observe(viewLifecycleOwner) {
            movieAdapter.submitList(it)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}