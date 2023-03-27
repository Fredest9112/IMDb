package com.globant.imdb.view.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.globant.imdb.databinding.FragmentSearchBinding
import com.globant.imdb.model.searchfragment.SearchMovieViewModel
import com.globant.imdb.model.searchfragment.SearchMovieViewModelFactory
import com.globant.imdb.view.MyIMDbApp
import com.globant.imdb.view.adapter.MovieAdapter
import javax.inject.Inject

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    @Inject
    lateinit var movieAdapter: MovieAdapter
    @Inject
    lateinit var searchMovieViewModelFactory: SearchMovieViewModelFactory
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as MyIMDbApp).appComponent.injectSearchFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            searchMovieViewModel.apply {
                topRatedMovies.observe(viewLifecycleOwner) {
                    movieAdapter.submitList(it)
                }
                searchResultRecycler.adapter = movieAdapter
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(query: String?): Boolean {
                        if(!query.isNullOrEmpty()){
                            getMoviesFromQuery(query)
                            moviesFromQuery.observe(viewLifecycleOwner) {
                                movieAdapter.submitList(it)
                            }
                            return true
                        }
                        return false
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}