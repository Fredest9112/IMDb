package com.globant.imdb.view.home.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.globant.imdb.database.Movie
import com.globant.imdb.databinding.FragmentSearchBinding
import com.globant.imdb.model.searchFragment.SearchMovieViewModel
import com.globant.imdb.model.searchFragment.SearchMovieViewModelFactory
import com.globant.imdb.utils.NetworkResult
import com.globant.imdb.utils.ToastCreator
import com.globant.imdb.view.MyIMDbApp
import com.globant.imdb.view.adapter.SearchMovieAdapter
import javax.inject.Inject

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null

    @Inject
    lateinit var searchMovieAdapter: SearchMovieAdapter

    @Inject
    lateinit var searchMovieViewModelFactory: SearchMovieViewModelFactory
    private val searchMovieViewModel by lazy {
        ViewModelProvider(requireActivity(), searchMovieViewModelFactory)[SearchMovieViewModel::class.java]
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
                    searchMovieAdapter.submitList(it)
                }
                searchResultRecycler.adapter = searchMovieAdapter
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(query: String?): Boolean {
                        if (!query.isNullOrEmpty()) {
                            getMoviesFromQuery(query)
                            moviesFromQuery.observe(viewLifecycleOwner) {
                                when (it) {
                                    is NetworkResult.MoviesSuccess -> {
                                        searchMovieAdapter.submitList(it.movies)
                                    }
                                    is NetworkResult.MoviesError -> {
                                        searchMovieAdapter.submitList(it.movies)
                                        ToastCreator.showToastMessage(context, it.message)
                                    }
                                }
                            }
                            return true
                        }
                        return false
                    }
                })
                searchMovieAdapter.setOnItemClickListener(object: SearchMovieAdapter.OnItemClickListener{
                    override fun onItemClick(movie: Movie?) {
                        if(movie != null){
                            getClickedMovie(movie)
                            val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment()
                            findNavController().navigate(action)
                        }
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