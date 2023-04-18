package com.globant.imdb.view.home.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.globant.imdb.database.asWatchListMovie
import com.globant.imdb.databinding.FragmentDetailsBinding
import com.globant.imdb.model.searchFragment.SearchMovieViewModel
import com.globant.imdb.model.searchFragment.SearchMovieViewModelFactory
import com.globant.imdb.utils.NetworkResult
import com.globant.imdb.utils.ToastCreator
import com.globant.imdb.view.MyIMDbApp
import com.globant.imdb.view.adapter.MostPopularMovieAdapter
import javax.inject.Inject

class DetailsFragment : Fragment() {

    private var binding: FragmentDetailsBinding? = null

    @Inject
    lateinit var searchMovieViewModelFactory: SearchMovieViewModelFactory

    @Inject
    lateinit var recommendedMovieAdapter: MostPopularMovieAdapter
    private val searchMovieViewModel by lazy {
        ViewModelProvider(requireActivity(), searchMovieViewModelFactory)[SearchMovieViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as MyIMDbApp).appComponent.injectDetailsFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            movieData = searchMovieViewModel
            recommendedMoviesRv.adapter = recommendedMovieAdapter
            searchMovieViewModel.apply {
                clickedMovieId.observe(viewLifecycleOwner){
                    getRecommendedMoviesFromId(it)
                }
                recommendedMovies.observe(viewLifecycleOwner){
                    when(it){
                        is NetworkResult.MoviesSuccess -> {
                            recommendedMovieAdapter.submitList(it.movies)
                        }
                        is NetworkResult.MoviesError -> {
                            ToastCreator.showToastMessage(context, it.message)
                        }
                    }
                }
                watchListButton.setOnClickListener {
                    clickedMovie.observe(viewLifecycleOwner) {
                        if(it != null){
                            addMovieToWatchList(it.asWatchListMovie())
                            ToastCreator.showToastMessage(context, "Movie Added to WatchList successfully")
                        } else {
                            ToastCreator.showToastMessage(context, "Error adding movie to WatchList")
                        }
                    }
                }
            }
            backToSearchMovies.setOnClickListener {
                val action = DetailsFragmentDirections.actionDetailsFragmentToSearchFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}